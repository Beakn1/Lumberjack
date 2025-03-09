package org.beakn.lumberjack;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class LumberManager {

    public static List<Material> logMaterials = Arrays.asList(Material.BIRCH_LOG, Material.ACACIA_LOG, Material.CHERRY_LOG,
            Material.JUNGLE_LOG, Material.MANGROVE_LOG, Material.SPRUCE_LOG, Material.DARK_OAK_LOG, Material.OAK_LOG, Material.DARK_OAK_LOG,
            Material.PALE_OAK_LOG);

    public static void lumberTree(Block origin, Player lumberer) {
        Queue<Block> toBreak = new LinkedList<>();
        toBreak.add(origin);

        while (!toBreak.isEmpty()) {
            Block current = toBreak.poll();
            if (!logMaterials.contains(current.getType())) continue;

            Material blockType = current.getType();
            current.setType(Material.AIR);
            current.getWorld().dropItemNaturally(current.getLocation(), new ItemStack(blockType));

            if (LumberConfig.isParticlesEnabled()) spawnParticle(current);

            if (LumberConfig.isSoundEnabled()) lumberer.playSound(current.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 1, 1);

            int[][] directions = {
                    {1, 0, 0}, {-1, 0, 0},
                    {0, 1, 0}, {0, -1, 0},
                    {0, 0, 1}, {0, 0, -1}
            };

            for (int[] dir : directions) {
                Block adjacentBlock = current.getRelative(dir[0], dir[1], dir[2]);
                if (logMaterials.contains(adjacentBlock.getType())) {
                    toBreak.add(adjacentBlock);
                }
            }
        }
    }

    private static void spawnParticle(Block block) {
        Random random = new Random();
        boolean brown = random.nextBoolean();
        float r = brown ? 0.4f : 0.0f;
        float g = brown ? 0.2f : 1.0f;
        float b = brown ? 0.1f : 0.0f;
        double offsetX = random.nextDouble();
        double offsetY = random.nextDouble();
        double offsetZ = random.nextDouble();
        block.getWorld().spawnParticle(Particle.DUST, block.getLocation().clone().add(offsetX, offsetY, offsetZ), 1, new Particle.DustOptions(org.bukkit.Color.fromRGB((int) (r * 255), (int) (g * 255), (int) (b * 255)), 1.5f));
    }
}

