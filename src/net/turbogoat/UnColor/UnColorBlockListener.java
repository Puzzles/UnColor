package net.turbogoat.UnColor;

import java.util.logging.Logger;

import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;


public class UnColorBlockListener extends BlockListener {
	private UnColor plugin;
	Logger log = Logger.getLogger("Minecraft");
	
	public UnColorBlockListener(UnColor instance) {
		plugin = instance;
	}
	
	public void onBlockDamage(BlockDamageEvent event) {
		Block b = event.getBlock();
		Player player = event.getPlayer();
		if (!player.getItemInHand().getType().toString().equals("AIR")) {
			return;
		}
		if(b.getType().toString().equalsIgnoreCase("WOOL")) {
			if (plugin.checkPlayerUCQuick(player) || plugin.checkPlayerUnColor(player)) {
				b.setData(DyeColor.WHITE.getData());
				return;
			}
			if (plugin.checkPlayerRCQuick(player) || plugin.checkPlayerReColor(player)) {
				this.reColorBlock(b, player);
			}
				

		}
	}
	
	private void reColorBlock(Block block, Player player) {
		Block b = block;
	    switch (plugin.getPlayerColor(player)) {
	    case 0:
	    	b.setData(DyeColor.WHITE.getData());
	    	break;
	    case 1:
	    	b.setData(DyeColor.ORANGE.getData());
	    	break;
	    case 2:
	    	b.setData(DyeColor.MAGENTA.getData());
	    	break;
	    case 3:
	    	b.setData(DyeColor.LIGHT_BLUE.getData());
	    	break;
	    case 4:
	    	b.setData(DyeColor.YELLOW.getData());
	    	break;
	    case 5:
	    	b.setData(DyeColor.LIME.getData());
	    	break;
	    case 6:
	    	b.setData(DyeColor.PINK.getData());
	    	break;
	    case 7:
	    	b.setData(DyeColor.GRAY.getData());
	    	break;
	    case 8:
	    	b.setData(DyeColor.SILVER.getData());
	    	break;
	    case 9:
	    	b.setData(DyeColor.CYAN.getData());
	    	break;
	    case 10:
	    	b.setData(DyeColor.PURPLE.getData());
	    	break;
	    case 11:
	    	b.setData(DyeColor.BLUE.getData());
	    	break;
	    case 12:
	    	b.setData(DyeColor.BROWN.getData());
	    	break;
	    case 13:
	    	b.setData(DyeColor.GREEN.getData());
	    	break;
	    case 14:
	    	b.setData(DyeColor.RED.getData());
	    	break;
	    case 15:
	    	b.setData(DyeColor.BLACK.getData());
	    	break;
	    }
	    return;
	}

}
