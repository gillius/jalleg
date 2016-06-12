package org.gillius.jalleg.framework;

import com.sun.jna.ptr.FloatByReference;
import org.gillius.jalleg.binding.*;
import org.gillius.jalleg.framework.math.Point;
import org.gillius.jalleg.framework.stats.GameState;
import org.gillius.jalleg.framework.stats.GameStats;
import org.gillius.jalleg.framework.stats.GameStatsRecorder;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public abstract class Game implements Runnable {

	protected ALLEGRO_TIMER mainTimer;
	protected ALLEGRO_EVENT_QUEUE eventQueue;
	protected ALLEGRO_DISPLAY mainDisplay;

	protected double gameTime;
	protected ALLEGRO_KEYBOARD_STATE keyState;
	protected ALLEGRO_MOUSE_STATE mouseState;
	protected GameStats lastStats;

	private EnumSet<AllegroAddon> initializedAddons = EnumSet.noneOf(AllegroAddon.class);

	private double targetFrameRate = 60.0;
	private String newWindowTitle = getClass().getSimpleName();
	private GameStatsRecorder statsRecorder = null;

	private ALLEGRO_COLOR clearColor = null;

	protected ALLEGRO_JOYSTICK_STATE joy1;
	private ALLEGRO_JOYSTICK joyHandle;

	public double getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setTargetFrameRate(double targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}

	public String getNewWindowTitle() {
		return newWindowTitle;
	}

	public void setNewWindowTitle(String newWindowTitle) {
		this.newWindowTitle = newWindowTitle;
	}

	public boolean isCollectingStats() {
		return statsRecorder != null;
	}

	public void initStatsCollection(long duration, TimeUnit unit) {
		statsRecorder = new GameStatsRecorder(unit.toNanos(duration));
	}

	public void stopStatsCollection() {
		statsRecorder = null;
		lastStats = null;
	}

	public ALLEGRO_COLOR getClearColor() {
		return clearColor;
	}

	public void setClearColor(ALLEGRO_COLOR clearColor) {
		this.clearColor = clearColor;
	}

	@Override
	public void run() {
		al_install_system(ALLEGRO_VERSION_INT, null);

		eventQueue = al_create_event_queue();
		mainTimer = al_create_timer(1.0 / targetFrameRate);
		al_set_new_window_title(newWindowTitle);
		mainDisplay = createDisplay();
		if (mainDisplay == null)
			throw new AllegroException("Unable to create display");
		clearColor = al_map_rgb((byte)0, (byte)0, (byte)0);

		al_register_event_source(eventQueue, al_get_timer_event_source(mainTimer));
		al_register_event_source(eventQueue, al_get_display_event_source(mainDisplay));

		try {
			onAllegroStarted();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			//Have a better handing for game startup issues
			throw new RuntimeException(e);
		}

		ALLEGRO_EVENT event = new ALLEGRO_EVENT();
		if (initializedAddons.contains(AllegroAddon.Keyboard))
			keyState = new ALLEGRO_KEYBOARD_STATE();

		if (initializedAddons.contains(AllegroAddon.Mouse))
			mouseState = new ALLEGRO_MOUSE_STATE();

		if (initializedAddons.contains(AllegroAddon.Joystick) &&
				al_get_num_joysticks() > 0) {
			joyHandle = al_get_joystick(0);
			joy1 = new ALLEGRO_JOYSTICK_STATE();
		}

		al_start_timer(mainTimer);

		boolean run = true;

		while(run) {
			event.setType(Integer.TYPE);
			if (isCollectingStats()) statsRecorder.startLoop();
			al_wait_for_event(eventQueue, event);

			if (event.type == ALLEGRO_EVENT_TIMER) {
				transition(GameState.Update);
				ALLEGRO_TIMER_EVENT timerEvent = event.asType(ALLEGRO_TIMER_EVENT.class);
				gameTime = timerEvent.timestamp;
				if (keyState != null)
					al_get_keyboard_state(keyState);
				if (joyHandle != null)
					al_get_joystick_state(joyHandle, joy1);
				if (mouseState != null)
					al_get_mouse_state(mouseState);

				update();

			} else {
				transition(GameState.Event);
				if (event.type == ALLEGRO_EVENT_DISPLAY_CLOSE) {
					run = false;
				}
			}

			if (al_is_event_queue_empty(eventQueue)) {
				transition(GameState.Render);
				if (clearColor != null)
					al_clear_to_color(clearColor);
				render();
				transition(GameState.Flip);
				al_flip_display();
			}

			if (isCollectingStats() && statsRecorder.endLoop()) {
				lastStats = statsRecorder.collectAndResetStats();
			}
		}

		onStopped();

		al_destroy_timer(mainTimer);
		al_destroy_event_queue(eventQueue);
		al_destroy_display(mainDisplay);
		uninstallAddons();
		al_uninstall_system();
	}

	private void transition(GameState state) {
		if (isCollectingStats())
			statsRecorder.transition(state);
	}

	protected void initAddons(AllegroAddon... addons) {
		for (AllegroAddon addon : addons) {
			switch (addon) {
				case Keyboard:
					checkInstall(al_install_keyboard(), addon);
					break;

				case Joystick:
					checkInstall(al_install_joystick(), addon);
					break;

				case Mouse:
					checkInstall(al_install_mouse(), addon);
					break;

				case Primitives:
					checkInstall(al_init_primitives_addon(), addon);
					break;

				case Font:
					checkInstall(al_init_font_addon(), addon);
					break;

				case Audio:
					checkInstall(al_install_audio(), addon);
					break;

				case AudioCodecs:
					checkInstall(al_init_acodec_addon(), addon);
					break;

				case Image:
					checkInstall(al_init_image_addon(), addon);
					break;

				default:
					throw new AssertionError("Unknown addon " + addon);
			}
			initializedAddons.add(addon);
		}
	}

	private static void checkInstall(boolean installed, AllegroAddon addon) {
		if (!installed)
			throw new AllegroException("Failed to install " + addon);
	}

	private void uninstallAddons() {
		for (AllegroAddon addon : initializedAddons) {
			switch (addon) {
				case Keyboard:
					al_uninstall_keyboard();
					break;

				case Joystick:
					al_uninstall_joystick();
					break;

				case Mouse:
					al_uninstall_mouse();
					break;

				case Primitives:
					al_shutdown_primitives_addon();
					break;

				case Font:
					al_shutdown_font_addon();
					break;

				case Audio:
					al_uninstall_audio();
					break;

				case AudioCodecs:
					//There is no shutdown step for audio codecs
					break;

				case Image:
					al_shutdown_image_addon();
					break;

				default:
					throw new AssertionError("Unknown addon " + addon);
			}
		}
		initializedAddons.clear();
	}

	protected void onAllegroStarted() throws Exception {}

	protected ALLEGRO_DISPLAY createDisplay() {
		return al_create_display(640, 480);
	}

	protected abstract void update();

	protected abstract void render();

	protected void onStopped() {}

	//Utility methods

	public int getDisplayHeight() {
		return al_get_display_height(mainDisplay);
	}

	public int getDisplayWidth() {
		return al_get_display_width(mainDisplay);
	}

	/**
	 * If the keyboard addon is installed, return if the given key pressed at the start of the update call.
	 */
	protected boolean isKeyDown(int keyCode) {
		return keyState.isKeyDown(keyCode);
	}

	/**
	 * If the mouse addon is installed, return the current mouse position in screen coordinates.
	 */
	protected Point getMousePos() {
		return new Point(mouseState.x, mouseState.y);
	}

	/**
	 * If the mouse addon is installed, return the current mouse position transformed by the inverse of the current
	 * target bitmap transform (al_transform_coordinates(al_get_current_inverse_transform(), mouseX, mouseY)).
	 */
	protected Point getMousePosTransformed() {
		return getMousePosTransformed(al_get_current_inverse_transform());
	}

	/**
	 * If the mouse addon is installed, return the current mouse position transformed by the given transform.
	 */
	protected Point getMousePosTransformed(ALLEGRO_TRANSFORM transform) {
		FloatByReference mouseX = new FloatByReference(mouseState.x);
		FloatByReference mouseY = new FloatByReference(mouseState.y);

		al_transform_coordinates(transform, mouseX, mouseY);

		return new Point(mouseX.getValue(), mouseY.getValue());
	}

	/**
	 * If the mouse addon is installed, return if the designated mouse button is pressed.
	 *
	 * @param button 0 for primary button, 1 for secondary button, 2 for middle, etc.
	 */
	protected boolean isMouseButtonDown(int button) {
		return (mouseState.buttons & (1 << button)) != 0;
	}

	/**
	 * If the joystick addon is installed and at least one joystick was plugged in, returns true if the joystick
	 * being pressed in the given direction. This method is used for simple "d-pad" like functionality. Always returns
	 * false if joystick addon is not installed or no joystick is connected.
	 *
	 * @see #joy1
	 */
	protected boolean isJoyDirection(Direction direction) {
		if (joy1 != null) {
			switch (direction) {
				case Up:    return joy1.stick[0].axis[1] < -0.5f;
				case Right: return joy1.stick[0].axis[0] > 0.5f;
				case Down:  return joy1.stick[0].axis[1] > 0.5f;
				case Left:  return joy1.stick[0].axis[0] < -0.5f;
				default:    return false;
			}
		} else {
			return false;
		}
	}
}
