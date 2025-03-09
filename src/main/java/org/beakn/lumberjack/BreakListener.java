package org.beakn.lumberjack;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (LumberManager.logMaterials.contains(block.getType())) {
            LumberManager.lumberTree(block, event.getPlayer());
        }
    }
}
