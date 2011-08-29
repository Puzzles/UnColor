package net.turbogoat.UnColor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class UnColor extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	private final UnColorBlockListener blockListener = new UnColorBlockListener(this);
	public static Map<Player, Boolean> ucEnabled = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> ucqEnabled = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> rcEnabled = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> rcqEnabled = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> rcColor = new HashMap<Player, Integer>();
	PermissionManager permissions = null;
		
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
		log.info("UnColor plugin enabled.");
		if (this.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
			permissions = PermissionsEx.getPermissionManager();
		}

	}
	public void onDisable() {
		log.info("UnColor plugin disabled.");
	}
	
	public int getPlayerColor(Player player) {
		if (rcColor.containsKey(player)) {
			return rcColor.get(player);
		}
		else {
			return 0;
		}
	}
	
	public boolean checkPlayerUCQuick(Player player) {
		if (ucqEnabled.containsKey(player)) {
			if(ucqEnabled.get(player)) {
				ucqEnabled.put(player, false);
				player.sendMessage(ChatColor.RED + "UnColor Quick-mode off");
				return true;
			}
		}
		return false;
	}
	
	public boolean checkPlayerUnColor(Player player) {
		if(ucEnabled.containsKey(player)) {
			if(ucEnabled.get(player)) {
					return true;
			} else {
				return false;
			}
		} else {
			ucEnabled.put(player, false);
			return false;
		}
	}
	
	public boolean checkPlayerReColor(Player player){
		if(rcEnabled.containsKey(player)) {
			return rcEnabled.get(player);
		}
		rcEnabled.put(player, false);
		return false;
	}
	
	public boolean checkPlayerRCQuick(Player player) {
		if (rcqEnabled.containsKey(player)) {
			if(rcqEnabled.get(player)) {
				rcqEnabled.put(player, false);
				player.sendMessage(ChatColor.RED + "ReColor Quick-mode off");
				return true;
			}
		}
		return false;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("uc")){
			Player player = (Player) sender;
			if (permissions != null) {
				if (!permissions.has(player, "uncolor.uncolor")) {
					player.sendMessage("You don't have permission to use this command");
					return true;
				}
			} 
			if (ucEnabled.containsKey(player)) {
				if (ucEnabled.get(player)) {
					ucEnabled.put(player, false);
					player.sendMessage(ChatColor.RED + "UnColor mode off.");
				} else {
					ucEnabled.put(player, true);
					player.sendMessage(ChatColor.GREEN + "UnColor mode on - Hit colored woolblocks to make " +
						"them white.");
				}
			} else {
				ucEnabled.put(player, true);
				player.sendMessage(ChatColor.GREEN + "UnColor mode on - Hit colored woolblocks to make " +
						"them white.");
			}
			ucqEnabled.put(player, false);
			rcEnabled.put(player, false);
			rcqEnabled.put(player, false);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("ucq")) {
			Player player = (Player) sender;
			if (permissions != null) {
				if (!permissions.has(player, "uncolor.uncolor")) {
					player.sendMessage("You don't have permission to use this command");
					return true;
				}
			}
			if (ucqEnabled.containsKey(player)){
				if (ucqEnabled.get(player)) {
					ucqEnabled.put(player, false);
					player.sendMessage(ChatColor.RED + "UnColor Quick-mode off");
				} else {
					ucqEnabled.put(player, true);
				    player.sendMessage(ChatColor.GREEN + "UnColor Quick-mode on - Hit a colored woolblock to make " +
							"it white.");
				}	
			} else {
				ucqEnabled.put(player, true);
				player.sendMessage(ChatColor.GREEN + "UnColor Quick-mode on - Hit a colored woolblock to make " +
						"it white.");
			}
			ucEnabled.put(player, false);
			rcEnabled.put(player, false);
			rcqEnabled.put(player, false);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("rc")) {
			Player player = (Player) sender;
			if (permissions != null) {
				if (!permissions.has(player, "uncolor.recolor")) {
					player.sendMessage("You don't have permission to use this command");
					return true;
				}
			}
			if (rcEnabled.containsKey(player)){
				if (rcEnabled.get(player) && args.length < 1) {
					player.sendMessage(ChatColor.RED + "ReColor off");
					rcEnabled.put(player, false);
				} else {
					if (args.length > 0) {
						if (this.setPlayerColor(player, args[0])) {
							player.sendMessage(ChatColor.GREEN + "Color set to: " + args[0]);
						} else {
							player.sendMessage(ChatColor.RED + "Invalid Color! use /rclist for a list of colors");
							return true;
						}
					}
					rcEnabled.put(player, true);
				    player.sendMessage(ChatColor.GREEN + "ReColor on - hit blocks to make them change color");
				}	
			} else {
				if (args.length > 0) {
					if (this.setPlayerColor(player, args[0])) {
						player.sendMessage(ChatColor.GREEN + "Color set to: " + args[0]);
					} else {
						player.sendMessage(ChatColor.RED + "Invalid Color! use /rclist for a list of colors");
						return true;
					}
				}
				player.sendMessage(ChatColor.GREEN + "ReColor on - hit blocks to make them change color");
				rcEnabled.put(player, true);
			}
			ucqEnabled.put(player, false);
			ucEnabled.put(player, false);
			rcqEnabled.put(player, false);
			return true;
			
		}
		if(cmd.getName().equalsIgnoreCase("rcq")) {
			Player player = (Player) sender;
			if (permissions != null) {
				if (!permissions.has(player, "uncolor.recolor")) {
					player.sendMessage("You don't have permission to use this command");
					return true;
				}
			}
			if (rcqEnabled.containsKey(player)){
				if (rcqEnabled.get(player) && args.length < 1) {
					player.sendMessage(ChatColor.RED + "ReColor Quick-Mode off");
					rcqEnabled.put(player, false);
				} else {
					if (args.length > 0) {
						if (this.setPlayerColor(player, args[0])) {
							player.sendMessage(ChatColor.GREEN + "Color set to: " + args[0]);
						} else {
							player.sendMessage(ChatColor.RED + "Invalid Color!");
							return true;
						}
					}
					rcqEnabled.put(player, true);
				    player.sendMessage(ChatColor.GREEN + "ReColor Quick-Mode on - hit a block to make it " +
				    	"change color");
				}	
			} else {
				if (args.length > 0) {
					if (this.setPlayerColor(player, args[0])) {
						player.sendMessage(ChatColor.GREEN + "Color set to: " + args[0]);
					} else {
						player.sendMessage(ChatColor.RED + "Invalid Color!");
						return true;
					}
				}
				player.sendMessage(ChatColor.GREEN + "ReColor Quick-Mode on - hit a block to make it " +
				    	"change color");
				rcqEnabled.put(player, true);
			}
			ucqEnabled.put(player, false);
			ucEnabled.put(player, false);
			rcEnabled.put(player, false);
			return true;
			
		}
		if(cmd.getName().equalsIgnoreCase("rclist")) {
			Player player = (Player) sender;
			if (permissions != null) {
				if (!permissions.has(player, "uncolor.recolor")) {
					player.sendMessage("You don't have permission to use this command");
					return true;
				}
			}
			player.sendMessage("Valid colors are: White, Black, Red, Magenta, Pink, Purple, Yellow, Orange, " +
				"Light_Blue, Blue, Gray, Light_Gray, Cyan, Brown, Green, Lime");
			return true;
		}
		return false;
	}
	
	private boolean setPlayerColor(Player player, String string) {
		if (string.equalsIgnoreCase("white")) {
			rcColor.put(player, 0);
			return true;
		}
		if (string.equalsIgnoreCase("orange")) {
			rcColor.put(player, 1);
			return true;
		}
		if (string.equalsIgnoreCase("magenta")) {
			rcColor.put(player, 2);
			return true;
		}
		if (string.equalsIgnoreCase("light_blue")) {
			rcColor.put(player, 3);
			return true;
		}
		if (string.equalsIgnoreCase("yellow")) {
			rcColor.put(player, 4);
			return true;
		}
		if (string.equalsIgnoreCase("lime")) {
			rcColor.put(player, 5);
			return true;
		}
		if (string.equalsIgnoreCase("pink")) {
			rcColor.put(player, 6);
			return true;
		}
		if (string.equalsIgnoreCase("gray")) {
			rcColor.put(player, 7);
			return true;
		}
		if (string.equalsIgnoreCase("light_gray")) {
			rcColor.put(player, 8);
			return true;
		}
		if (string.equalsIgnoreCase("cyan")) {
			rcColor.put(player, 9);
			return true;
		}
		if (string.equalsIgnoreCase("purple")) {
			rcColor.put(player, 10);
			return true;
		}
		if (string.equalsIgnoreCase("blue")) {
			rcColor.put(player, 11);
			return true;
		}
		if (string.equalsIgnoreCase("brown")) {
			rcColor.put(player, 12);
			return true;
		}
		if (string.equalsIgnoreCase("green")) {
			rcColor.put(player, 13);
			return true;
		}
		if (string.equalsIgnoreCase("red")) {
			rcColor.put(player, 14);
			return true;
		}
		if (string.equalsIgnoreCase("black")) {
			rcColor.put(player, 15);
			return true;
		}
		return false;
	}
}
