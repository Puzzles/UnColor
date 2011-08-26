package net.turbogoat.UnColor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class UnColor extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	private final UnColorBlockListener blockListener = new UnColorBlockListener(this);
	public static Map<Player, Boolean> ucEnabled = new HashMap<Player, Boolean>();
	
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
		log.info("UnColor started");
	}
	public void onDisable() {
		log.info("UnColor stopped");
	}
	
	public boolean checkPlayerUnColor(Player player) {
		if(ucEnabled.containsKey(player)) {
			if(ucEnabled.get(player)) {
				if(player.getItemInHand().getType().toString().equalsIgnoreCase("AIR")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			ucEnabled.put(player, false);
			return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("uc")){
			Player player = (Player) sender;
			if (ucEnabled.containsKey(player)) {
				if (ucEnabled.get(player)) {
					ucEnabled.put(player, false);
					player.sendMessage("UnColor disabled.");
				} else {
					ucEnabled.put(player, true);
					player.sendMessage("UnColor enabled.");
				}
			} else {
				ucEnabled.put(player, true);
				player.sendMessage("UnColor enabled.");
			}
			return true;
		}
		return false;
	}
}
