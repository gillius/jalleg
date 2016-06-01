package org.gillius.jalleg.framework;

import org.gillius.jalleg.binding.ALLEGRO_EVENT;
import org.gillius.jalleg.binding.ALLEGRO_KEYBOARD_STATE;
import org.gillius.jalleg.binding.ALLEGRO_TIMER_EVENT;

import java.util.EnumSet;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public abstract class Game implements Runnable {

	protected ALLEGRO_TIMER mainTimer;
	protected ALLEGRO_EVENT_QUEUE eventQueue;
	protected ALLEGRO_DISPLAY mainDisplay;

	protected double gameTime;
	protected ALLEGRO_KEYBOARD_STATE keys;

	private EnumSet<AllegroAddon> initializedAddons = EnumSet.noneOf(AllegroAddon.class);

	private double targetFrameRate = 60.0;

	public double getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setTargetFrameRate(double targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}

	@Override
	public void run() {
		al_install_system(ALLEGRO_VERSION_INT, null);

		eventQueue = al_create_event_queue();
		mainTimer = al_create_timer(1.0 / targetFrameRate);
		mainDisplay = createDisplay();
		if (mainDisplay == null)
			throw new AllegroException("Unable to create display");

		al_register_event_source(eventQueue, al_get_timer_event_source(mainTimer));
		al_register_event_source(eventQueue, al_get_display_event_source(mainDisplay));

		onAllegroStarted();

		ALLEGRO_EVENT event = new ALLEGRO_EVENT();
		keys = new ALLEGRO_KEYBOARD_STATE();

		al_start_timer(mainTimer);

		boolean run = true;

		while(run) {
			event.setType(Integer.TYPE);
			al_wait_for_event(eventQueue, event);

			if (event.type == ALLEGRO_EVENT_TIMER) {
				ALLEGRO_TIMER_EVENT timerEvent = event.asType(ALLEGRO_TIMER_EVENT.class);
				gameTime = timerEvent.timestamp;
				if (initializedAddons.contains(AllegroAddon.Keyboard))
					al_get_keyboard_state(keys);

				update();

			} else if (event.type == ALLEGRO_EVENT_DISPLAY_CLOSE) {
				run = false;
			}

			if (al_is_event_queue_empty(eventQueue)) {
				render();
				al_flip_display();
			}
		}

		onStopped();

		al_destroy_timer(mainTimer);
		al_destroy_event_queue(eventQueue);
		al_destroy_display(mainDisplay);
		uninstallAddons();
		al_uninstall_system();
	}

	protected void initAddons(AllegroAddon... addons) {
		for (AllegroAddon addon : addons) {
			switch (addon) {
				case Keyboard:
					checkInstall(al_install_keyboard(), addon);
					break;

				case Primitives:
					checkInstall(al_init_primitives_addon(), addon);
					break;

				case Font:
					checkInstall(al_init_font_addon(), addon);
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

				case Primitives:
					al_shutdown_primitives_addon();
					break;

				case Font:
					al_shutdown_font_addon();
					break;

				default:
					throw new AssertionError("Unknown addon " + addon);
			}
		}
		initializedAddons.clear();
	}

	protected void onAllegroStarted() {}

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

	protected boolean isKeyDown(int keyCode) {
		return keys.isKeyDown(keyCode);
	}
}
