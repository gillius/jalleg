package org.gillius.jalleg.example;

import com.sun.jna.Pointer;
import org.gillius.jalleg.binding.ALLEGRO_EVENT;
import org.gillius.jalleg.binding.ALLEGRO_USER_EVENT;
import org.gillius.jalleg.binding.AllegroLibrary.*;
import org.gillius.jalleg.binding.NativeTextLogWriter;
import org.gillius.jalleg.framework.AllegroAddon;
import org.gillius.jalleg.framework.Game;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public class NativeDialogs extends Game {

	@Override
	protected void onAllegroStarted() throws Exception {
		initAddons(AllegroAddon.NativeDialogs);

		ALLEGRO_TEXTLOG textlog = al_open_native_text_log("Native Dialogs!", ALLEGRO_TEXTLOG_NO_CLOSE | ALLEGRO_TEXTLOG_MONOSPACE);
		jalleg_append_native_text_log(textlog, "123\n456\n");

		PrintWriter pw = new PrintWriter(new NativeTextLogWriter(textlog), true);
		pw.println("Here be dragons!");
		pw.println("Here be dragons!");

		ALLEGRO_MENU menu = al_create_menu();
		al_append_menu_item(menu, "Exit", (short) 1, 0, null, null);
		al_set_display_menu(mainDisplay, menu);

		al_register_event_source(eventQueue, al_get_default_menu_event_source());
	}

	@Override
	protected void update() {
	}

	@Override
	protected void render() {
	}

	@Override
	protected void onUnhandledEvent(ALLEGRO_EVENT event) {
		if (event.type == ALLEGRO_EVENT_MENU_CLICK) {
			ALLEGRO_USER_EVENT userEvent = event.asType(ALLEGRO_USER_EVENT.class);
			if (Pointer.nativeValue(userEvent.data1) == 1)
				stop();
		}
	}

	public static void main(String[] args) {
		new NativeDialogs().run();
	}
}
