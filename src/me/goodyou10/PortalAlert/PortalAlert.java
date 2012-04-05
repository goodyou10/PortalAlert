package me.goodyou10.PortalAlert;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalAlert extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft");
	private CMDExecutor cmdExecutor;

	public static double Version = 1.3;
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
			// Config file wasn't found, so create a new one
			this.logInfo("No configuration file found, creating one now!");
			config.options()
					.header("\r\nPortalAlert main configuration file\r\n\r\nThis is the global configuration file. Anything placed into here will\r\nbe applied to the whole server.\r\n\r\nAbout editing this file:\r\n- DO NOT USE TABS. You MUST use spaces or Bukkit will complain. If\r\n  you use an editor like Notepad++ (recommended for Windows users), you\r\n  must configure it to \"replace tabs with spaces.\" In Notepad++, this can\r\n  be changed in Settings > Preferences > Language Menu.\r\n- If you want to check the format of this file before putting it\r\n  into PortalAlert, paste it into http://yaml-online-parser.appspot.com/\r\n  and see if it gives ERROR:.\r\n- Lines starting with # are comments and so they are ignored.\r\n- {PLAYERNAME} will be replaced with the player's name.\r\n");
			config.options().copyHeader(true);
			config.addDefault("message.cancel.player",
					"You are not allowed to travel through portals!");
			config.addDefault("message.cancel.console",
					"{PLAYERNAME} tried to use a portal!");
			config.addDefault("message.portal.player",
					"You have entered a portal and have been teleported to a different world!");
			config.addDefault(
					"message.portal.admin",
					"{PLAYERNAME} has entered a portal and has been teleported to a different world!");
			config.options().copyDefaults(true);
			this.saveConfig();
		} else {
			this.logInfo("Configuration file loaded!");
		}
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
