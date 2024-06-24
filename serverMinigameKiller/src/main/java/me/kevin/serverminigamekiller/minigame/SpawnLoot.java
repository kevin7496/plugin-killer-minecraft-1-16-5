package me.kevin.serverminigamekiller.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnLoot implements Listener {

    World world = Bukkit.getWorld("world");

    List<Location> locations = new ArrayList<>();

    //Конструктор
    public SpawnLoot() {
        //Точки спавна лута
        locations.add(new Location(world, 37, 15, 30));
        locations.add(new Location(world, 25, 14, 48));
        locations.add(new Location(world, 10, 16, 31));
        locations.add(new Location(world, -2, 15, 16));

        locations.add(new Location(world, 24, 14, -7));
        locations.add(new Location(world, -3, 14, 36));
        locations.add(new Location(world, -11, 14, 39));
        locations.add(new Location(world, -7, 14, 42));

        locations.add(new Location(world, -9, 14, 44));
        locations.add(new Location(world, -6, 14, 49));
        locations.add(new Location(world, -15, 14, 48));
        locations.add(new Location(world, -15, 14, 45));

        locations.add(new Location(world, -17, 14, 41));
        locations.add(new Location(world, -22, 14, 46));
        locations.add(new Location(world, -35, 15, 32));
        locations.add(new Location(world, -35, 15, 25));

        locations.add(new Location(world, -35, 15, 27));
        locations.add(new Location(world, -12, 14, 6));
        locations.add(new Location(world, -28, 15, -13));
        locations.add(new Location(world, -34, 14, 19));

    }

    //Спавн лута
    public void spawn() {
        Random r = new Random();
        for (Location loc : locations) {
            loc.getBlock().setType(Material.AIR);
        }
        for (int i = 0; i < 5; i++) {
            Location loc = locations.get(r.nextInt(locations.size()));
            Block block = loc.getBlock();
            block.setType(Material.DEAD_HORN_CORAL_FAN);
            BlockData blockData = block.getBlockData();
            if (blockData instanceof Waterlogged) {
                Waterlogged waterlogged = (Waterlogged) blockData;
                waterlogged.setWaterlogged(false);
                block.setBlockData(waterlogged);
            }
        }

        for (int i = 0; i < 7; i++) {
            Location loc = locations.get(r.nextInt(locations.size()));
            Block block = loc.getBlock();
            block.setType(Material.DEAD_FIRE_CORAL_FAN);
            BlockData blockData = block.getBlockData();
            if (blockData instanceof Waterlogged) {
                Waterlogged waterlogged = (Waterlogged) blockData;
                waterlogged.setWaterlogged(false);
                block.setBlockData(waterlogged);
            }
        }

        for (int i = 0; i < 1; i++) {
            Location loc = locations.get(r.nextInt(locations.size()));
            Block block = loc.getBlock();
            block.setType(Material.DEAD_BUBBLE_CORAL_FAN);
            BlockData blockData = block.getBlockData();
            if (blockData instanceof Waterlogged) {
                Waterlogged waterlogged = (Waterlogged) blockData;
                waterlogged.setWaterlogged(false);
                block.setBlockData(waterlogged);
            }
        }
    }
}
