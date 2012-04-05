package me.goodyou10.PortalAlert;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalAlert extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft");
	private CMDExecutor cmdExecutor;

	public static double Version = 1.31;
	public static String cancel_player;
	public static String cancel_console;
	public static String portal_player;
	public static String portal_admins;

	public void onEnable() {
		// Load the config
		this.loadConfig();
		
		// Load the listener
		new PortalListener(this);

		// Register the commands
		this.cmdExecutor = new CMDExecutor(this);
		this.getCommand("portalalert").setExecutor(cmdExecutor);

		this.logInfo("Plugin Enabled!");
	}

	public void onDisable() {
		this.logInfo("Plugin Disabled.");
	}

	public void logInfo(String string) {
		this.log.log(Level.INFO, "[PortalAlert v" + Version + "] " + string);
	}

	public void loadConfig() {
		final FileConfiguration config = this.getConfig();
		// Does the config file exists?
		if (!new File("plugins" + File.separator + "PortalAlert"
				+ File.separator + "config.yml").exists()) {
			this.logInfo("No configuration file found, creating a new one now!");
		} else {
			this.logInfo("Configuration file loaded!");
		}
		config.options().copyDefaults();
		this.saveDefaultConfig();
		
		cancel_player = config.getString("message.cancel.player",
				"You are not allowed to travel through portals!");
		cancel_console = config.getString("message.cancel.console",
				"{PLAYERNAME} tried to use a portal!");
		portal_player = config
				.getString("message.portal.player",
						"You have entered a portal and have been teleported to a different world!");
		portal_admins = config
				.getString(
						"message.portal.admin",
						"{PLAYERNAME} has entered a portal and has been teleported to a different world!");
	}
}
