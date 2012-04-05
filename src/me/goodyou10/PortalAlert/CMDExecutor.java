package me.goodyou10.PortalAlert;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDExecutor implements CommandExecutor {

	private PortalAlert plugin;

	public CMDExecutor(PortalAlert plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		// They typed /portalalert
		if (cmd.getName().equalsIgnoreCase("portalalert")) {
			// Is there something after it?
			if (args.length < 1) {
				return false;
			}
			// If they typed /portalalert reload
			if (args[0].equalsIgnoreCase("reload")) {
				// Are they on console
				if (player != null) {
					sender.sendMessage("PortalAlert can only be reloaded through the console!");
					return true;
				}
				// Reload the config
				this.plugin.reloadConfig();
				this.plugin.loadConfig();
				sender.sendMessage("[PortalAlert v" + PortalAlert.Version
						+ "] Configuration has been reloaded!");
			}
			// Else if they typed /portalalert version
			else if (args[0].equalsIgnoreCase("version")) {
				sender.sendMessage("This build of PortalAlert is version "
						+ PortalAlert.Version);
			} else {
				return false;
			}
			return true;
		}
		return false;
	}

}
