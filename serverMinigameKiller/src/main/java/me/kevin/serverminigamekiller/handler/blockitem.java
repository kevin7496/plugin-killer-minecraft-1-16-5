package me.kevin.serverminigamekiller.handler;

import me.kevin.serverminigamekiller.minigame.GameLogic;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class blockitem implements Listener {

    private GameLogic gameLogic;

    public blockitem(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }
    @EventHandler
    public void onClickRemonBlock(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block targetclick = e.getClickedBlock();
        Block targetcast = p.getTargetBlockExact(5);

        ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "Ящик с инструментами");
            meta.removeItemFlags();
        }
        item.setItemMeta(meta);
        if (targetclick != null && targetcast != null) {
            if (targetclick.getType() == Material.DEAD_FIRE_CORAL_FAN) {
                if (p.equals(gameLogic.killer)) {
                    return;
                }
                if (p.getInventory().firstEmpty() != -1) {
                    targetclick.setType(Material.AIR);
                    p.getInventory().addItem(item);
                    p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Ремонтный ящик +1"));
                }else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Нет места"));
                }
            }
        }
    }

    @EventHandler
    public void onClickSyringe(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block targetclick = e.getClickedBlock();
        Block targetcast = p.getTargetBlockExact(5);

        ItemStack item = new ItemStack(Material.IRON_HORSE_ARMOR);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + "ШПРИЦ С НЕИЗВЕСНОЙ ЖИДКОСТЬЮ");
            meta.removeItemFlags();
        }
        item.setItemMeta(meta);
        if (targetclick != null && targetcast != null) {
            if (targetclick.getType() == Material.DEAD_BUBBLE_CORAL_FAN) {
                if (p.equals(gameLogic.killer)) {
                    return;
                }
                if (p.getInventory().firstEmpty() != -1) {
                    targetclick.setType(Material.AIR);
                    p.getInventory().addItem(item);
                    p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "ШПРИЦ С НЕИЗВЕСНОЙ ЖИДКОСТЬЮ +1"));
                }else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Нет места"));
                }
            }
        }
    }

    @EventHandler
    public void onClickHealBlock(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block targetclick = e.getClickedBlock();
        Block targetcast = p.getTargetBlockExact(5);

        ItemStack heal = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) heal.getItemMeta();
        if (meta != null) {
            meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1), false);
            meta.setDisplayName(ChatColor.GOLD + "Аптечка первой помощи");
            meta.setLore(null);
            meta.removeItemFlags();
        }
        heal.setItemMeta(meta);
        if (targetclick != null && targetcast != null) {
            if (targetclick.getType() == Material.DEAD_HORN_CORAL_FAN) {
                if (p.equals(gameLogic.killer)) {
                    return;
                }

                if (p.getInventory().firstEmpty() != -1) {
                    targetclick.setType(Material.AIR);
                    p.getInventory().addItem(heal);
                    p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Ремонтный ящик +1"));
                }else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Нет места"));
                }
            }
        }
    }
}
