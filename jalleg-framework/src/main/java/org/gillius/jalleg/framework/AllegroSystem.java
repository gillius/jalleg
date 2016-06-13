package org.gillius.jalleg.framework;

import java.util.EnumSet;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * Represents the initialized state of Allegro. With Allegro being a native library that can be loaded only once per
 * JVM, this is a true singleton class, accessed via {@link #INSTANCE}. This class is thread-safe in that multiple
 * threads can ask for initialization of Allegro and addons.
 */
public class AllegroSystem {
	public static final AllegroSystem INSTANCE = new AllegroSystem();

	private boolean installed;
	private EnumSet<AllegroAddon> initializedAddons = EnumSet.noneOf(AllegroAddon.class);

	private AllegroSystem() {}

	public synchronized boolean isInstalled() {
		return installed;
	}

	public synchronized void install() throws AllegroException {
		if (!installed) {
			if (!al_install_system(ALLEGRO_VERSION_INT, null))
				throw new AllegroException("Failed to initialize Allegro");
			installed = true;
		}
	}

	/**
	 * Initializes the requested add-ons, {@link #install()}ing Allegro if not already installed.
	 */
	public synchronized void initAddons(AllegroAddon... addons) throws AllegroException {
		install();

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

	public synchronized boolean isAddonInstalled(AllegroAddon addon) {
		return initializedAddons.contains(addon);
	}

	private static void checkInstall(boolean installed, AllegroAddon addon) {
		if (!installed)
			throw new AllegroException("Failed to install " + addon);
	}

	/**
	 * Uninstall all addons.
	 */
	public synchronized void uninstallAddons() {
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

	public synchronized void uninstall() {
		if (installed) {
			//I assume uninstall system will safely uninstall all addons
			al_uninstall_system();
			installed = false;
		}
	}
}
