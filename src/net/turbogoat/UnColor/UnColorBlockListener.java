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
		if(b.getType().toString().equalsIgnoreCase("WOOL") && plugin.checkPlayerUnColor(player)) {
			b.setData(DyeColor.WHITE.getData());
		}
	}
}
