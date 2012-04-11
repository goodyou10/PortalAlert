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

	@EventHandler(ignoreCancelled = true)
	public void onPlayerPortal(PlayerPortalEvent event) {
		// Get the player
		Player eventPlayer = event.getPlayer();
		String playername = eventPlayer.getName();
		int x = event.getFrom().getBlockX();
		int y = event.getFrom().getBlockY();
		int z = event.getFrom().getBlockZ();

		// Are they allowed to travel through portals?
		if (!eventPlayer.hasPermission("portalalert.allowportal")) {
			event.setCancelled(true);
			eventPlayer.sendMessage(ChatColor.RED
					+ "[PortalAlert] "
					+ plugin.replace(PortalAlert.cancel_player, playername, x,
							y, z));
			plugin.logInfo(plugin.replace(PortalAlert.cancel_console,
					playername, x, y, z));
			return;
		}

		// Send the player a message
		eventPlayer.sendMessage(plugin.replace(PortalAlert.portal_player,
				playername, x, y, z));

		// Send message to all players with the permission
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.hasPermission("portalalert.recieve")) {
				player.sendMessage(ChatColor.GREEN
						+ "[PortalAlert] "
						+ plugin.replace(PortalAlert.portal_admins, playername,
								x, y, z));
			}
		}
		plugin.logInfo(plugin.replace(PortalAlert.portal_admins, playername, x,
				y, z));
	}
}