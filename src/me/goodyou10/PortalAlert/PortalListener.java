package me.goodyou10.PortalAlert;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {

	private PortalAlert plugin;

	public PortalListener(PortalAlert plugin) {
		this.plugin = plugin;

		// Register the event
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		// Get the player
		Player eventPlayer = event.getPlayer();
		String playername = eventPlayer.getName();

		// Are they allowed to travel through portals?
		if (!eventPlayer.hasPermission("portalalert.allowportal")) {
			event.setCancelled(true);
			eventPlayer.sendMessage(ChatColor.RED
					+ "[PortalAlert] "
					+ PortalAlert.cancel_player.replaceAll("\\{PLAYERNAME\\}",
							playername));
			plugin.logInfo(PortalAlert.cancel_console.replaceAll(
					"\\{PLAYERNAME\\}", playername));
			return;
		}

		// Send the player a message
		eventPlayer.sendMessage(PortalAlert.portal_player.replaceAll(
				"\\{PLAYERNAME\\}", playername));

		// Send message to all players with the permission
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.hasPermission("portalalert.recieve")) {
				player.sendMessage(ChatColor.GREEN
						+ "[PortalAlert] "
						+ PortalAlert.portal_admins.replaceAll(
								"\\{PLAYERNAME\\}", playername));
			}
		}
		plugin.logInfo(PortalAlert.portal_admins.replaceAll("\\{PLAYERNAME\\}",
				playername));
	}
}