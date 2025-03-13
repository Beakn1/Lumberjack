package org.beakn.lumberjack;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class LumberManager {

    public static List<Material> logMaterials = Arrays.asList(Material.BIRCH_LOG, Material.ACACIA_LOG, Material.CHERRY_LOG,
            Material.JUNGLE_LOG, Material.MANGROVE_LOG, Material.SPRUCE_LOG, Material.DARK_OAK_LOG, Material.OAK_LOG, Material.DARK_OAK_LOG,
            Material.PALE_OAK_LOG);

    public static void lumberTree(Block origin, Player lumberer) {
        if (LumberConfig.isProgressiveBreakEnabled()) {
            breakProgressive(origin, lumberer);
        } else {
            breakInstantly(origin, lumberer);
        }
    }

    private static void breakProgressive(Block origin, Player lumberer) {

        List<Block> toBreak = getConnectedBlocks(origin);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (toBreak.isEmpty()) {
                    this.cancel();
                    return;
                }

                Block current = toBreak.getFirst();

                Material blockType = current.getType();
                current.setType(Material.AIR);
                current.getWorld().dropItemNaturally(current.getLocation(), new ItemStack(blockType));

                if (LumberConfig.isParticlesEnabled()) spawnParticle(current, blockType);

                if (LumberConfig.isSoundEnabled()) lumberer.playSound(current.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 1, 1);

                toBreak.removeFirst();
            }
        }.runTaskTimer(PluginProvider.getPlugin(), 0L, 2L);
    }

    private static List<Block> getConnectedBlocks(Block origin) {
        List<Block> connectedBlocks = new ArrayList<>();
        Queue<Block> toCheck = new LinkedList<>();
        Set<Block> visited = new HashSet<>();

        toCheck.add(origin);
        visited.add(origin);

        while (!toCheck.isEmpty()) {
            Block current = toCheck.poll();
            if (!logMaterials.contains(current.getType())) continue;

            connectedBlocks.add(current);

            int[][] directions = {
                    {1, 0, 0}, {-1, 0, 0},
                    {0, 1, 0}, {0, -1, 0},
                    {0, 0, 1}, {0, 0, -1}
            };

            for (int[] dir : directions) {
                Block adjacentBlock = current.getRelative(dir[0], dir[1], dir[2]);
                if (logMaterials.contains(adjacentBlock.getType()) && !visited.contains(adjacentBlock)) {
                    toCheck.add(adjacentBlock);
                    visited.add(adjacentBlock);
                }
            }
        }

        return connectedBlocks;
    }


    private static void breakInstantly(Block origin, Player lumberer) {
        Queue<Block> toBreak = new LinkedList<>();
        toBreak.add(origin);

        while (!toBreak.isEmpty()) {
            Block current = toBreak.poll();
            if (!logMaterials.contains(current.getType())) continue;

            Material blockType = current.getType();
            current.setType(Material.AIR);
            current.getWorld().dropItemNaturally(current.getLocation(), new ItemStack(blockType));

            if (LumberConfig.isParticlesEnabled()) spawnParticle(current, blockType);

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

    private static void spawnParticle(Block block, Material blockType) {
        Random random = new Random();
        double offsetX = random.nextDouble();
        double offsetY = random.nextDouble();
        double offsetZ = random.nextDouble();
        if (getColour(blockType) == null) return;
        block.getWorld().spawnParticle(Particle.DUST, block.getLocation().clone().add(offsetX, offsetY, offsetZ), 3, new Particle.DustOptions(Objects.requireNonNull(getColour(blockType)), 1.5f));
    }

    private static Color getColour(Material logType) {
        switch (logType) {
            case OAK_LOG -> {
                return Color.fromRGB(84, 64, 39);
            }
            case BIRCH_LOG -> {
                return Color.fromRGB(204, 204, 204);
            }
            case ACACIA_LOG -> {
                return Color.fromRGB(98, 92, 83);
            }
            case JUNGLE_LOG -> {
                return Color.fromRGB(67, 62, 21);
            }
            case SPRUCE_LOG -> {
                return Color.fromRGB(47, 31, 15);
            }
            case DARK_OAK_LOG -> {
                return Color.fromRGB(31, 24, 13);
            }
            case MANGROVE_LOG -> {
                return Color.fromRGB(74, 59, 36);
            }
            case CHERRY_LOG -> {
                return Color.fromRGB(44, 26, 33);
            }
            case PALE_OAK_LOG -> {
                return Color.fromRGB(76, 67, 66);
            }
            default -> {
                return null;
            }
        }
    }
}

