package me.kevin.serverminigamekiller.handler;

import me.kevin.serverminigamekiller.Main;
import me.kevin.serverminigamekiller.minigame.GameLogic;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class onInterectiv implements Listener {

    private GameLogic gameLogic;
    private BukkitRunnable task;
    final List<Material> interactableBlocks;

    public onInterectiv(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        interactableBlocks = new ArrayList<>();
        interactableBlocks.add(Material.DEAD_HORN_CORAL_FAN);
        interactableBlocks.add(Material.DEAD_FIRE_CORAL_FAN);
        interactableBlocks.add(Material.DEAD_BUBBLE_CORAL_FAN);
        interactableBlocks.add(Material.SPONGE);
    }

    public void timer(Player p) {
        if (task != null) {
            task.cancel();
        }
        task = new BukkitRunnable() {
            @Override
            public void run() {
                interact(p);
            }
        };
        task.runTaskTimer(Main.getInstance(), 0, 10);
    }

    @EventHandler
    public void moveInteract(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block target = p.getTargetBlock(null, 5);
        if (target != null || target.getType() != Material.AIR) {
            timer(p);
        }else {
            task.cancel();
        }
    }

    public void interact(Player p) {
        Block target = p.getTargetBlock(null, 5);
        if (target != null) {
            if (p.equals(gameLogic.killer)) {
                return;
            }
            if (interactableBlocks.contains(target.getType())) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Взаимодействие"));
            }else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
            }
        }
        else{
            task.cancel();
        }
    }
}
