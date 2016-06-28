package org.gillius.jalleg.example;

import org.gillius.jalleg.framework.AllegroConfig;
import org.gillius.jalleg.framework.io.AllegroLoader;
import org.gillius.jalleg.framework.io.Memfile;

public class ConfigExample {
	public static void main(String[] args) throws Exception {
		AllegroConfig config =
				AllegroConfig.fromConfigAndDestroy(
						AllegroLoader.loadConfigFromClasspath("/org/gillius/jalleg/example/configExample.ini"));

		System.out.println("Game speed is " + config.getConfigValue("", "game speed"));
		System.out.println("Player 1 name is " + config.getConfigValue("player 1", "name"));
		System.out.println("Player 2 name is " + config.getConfigValue("player 2", "name"));
		System.out.println("Value with default is " + config.getConfigValue("", "undefined", "default"));

		config.setConfigValue("new section", "value", 123);

		//Need to create a memfile big enough to hold the result in memory. There's not really a way to know in advance
		//how big we need. But memfiles know their size so if trying to write too much the result will just truncate --
		//there should not be a native crash.
		Memfile memfile = new Memfile(4000);
		config.saveConfig(memfile.getFile());

		System.out.println("Config file after modification:");
		System.out.println(new String(memfile.getData()));
	}
}
