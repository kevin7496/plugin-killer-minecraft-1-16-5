package me.kevin.serverminigamekiller.handler;

import me.kevin.serverminigamekiller.minigame.GameLogic;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class RemontGenereint implements Listener {
    private Map<Block, Integer> generatorProgress = new HashMap<>();
    private final int repairThreshold = 100;
    private final Material repairTool = Material.LEATHER_HORSE_ARMOR;
    private final int totalGenerators = 5;
    private int repairedGenerators = 0;
    TitileAPI titileAPI = new TitileAPI();// Track active generators and their task IDs
    GameLogic gameLogic = new GameLogic();
    Player killer = gameLogic.killer;

    @EventHandler
    public void onGeneratorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && clickedBlock.getType() == Material.SPONGE) {
            if (!player.equals(killer)) {
                if (player.getInventory().getItemInMainHand().getType() == repairTool) {
                    repairGenerator(player, clickedBlock);
                } else {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Нужен ящик с иструментами для ремонта!"));
                }
            }else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Это тебе не нужно"));
            }
        }
    }

    private void repairGenerator(Player player, Block block) {
        int progress = generatorProgress.getOrDefault(block, 0);
        progress++;

        if (progress >= repairThreshold) {
            player.sendMessage("Генератор починен и включен!");
            block.setType(Material.AIR);
            generatorProgress.remove(block);
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            player.playSound(block.getLocation(), Sound.ENTITY_BEE_LOOP, 10, 0);
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1);
            repairedGenerators++;
            for (Player p : Bukkit.getOnlinePlayers()) {
                titileAPI.sendSubtitle(p, ChatColor.YELLOW + "ПОЧИНИЛИ " + repairedGenerators + "/" + totalGenerators);
            }
            if (repairedGenerators >= totalGenerators) {
                gameLogic.open_exit();
            }

        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1, 3, true, false));
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Прогресс ремонта генератора: " + progress + "%"));
            generatorProgress.put(block, progress);
        }
    }
}
