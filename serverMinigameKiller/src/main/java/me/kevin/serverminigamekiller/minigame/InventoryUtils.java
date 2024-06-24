package me.kevin.serverminigamekiller.minigame;

import com.sun.tools.classfile.ModuleMainClass_attribute;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class InventoryUtils implements Listener {

    GameLogic gameLogic = new GameLogic();

    public static void fullInventory(Player player, ItemStack itemStack, int[] slot_empty) {
        Inventory inv = player.getInventory();
        inv.clear();

        List<Integer> armorSlots = Arrays.asList(36, 37, 38, 39);

        for (int i = 0; i < inv.getSize(); i++) {
            boolean emptySlot = false;
            for (int slots_empty : slot_empty) {
                if (i == slots_empty) {
                    emptySlot = true;
                    break;
                }
            }
            if (!emptySlot && !armorSlots.contains(i)) {
                ItemStack newItemStack = itemStack.clone();
                ItemMeta itemMeta = newItemStack.getItemMeta();
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                newItemStack.setItemMeta(itemMeta);
                inv.setItem(i, newItemStack);
            }
        }
        player.getInventory().setItemInOffHand(null);

        player.updateInventory();
    }

    public void inventory_start(Player p ) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "Заблокированая ячейка");
            itemMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemMeta);
        fullInventory(p, item, new int[]{3,4,5});
    }

    @EventHandler
    public void inventoryDrop(PlayerDropItemEvent e) {
        ItemStack item = e.getItemDrop().getItemStack();
        if (item.getType() == Material.BARRIER || item.getType() == Material.BRAIN_CORAL_BLOCK || item.getType() == Material.IRON_AXE) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void picked(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (p.equals(gameLogic.killer)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getClickedInventory() != null && event.getClickedInventory().getHolder() instanceof Player) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && clickedItem.getType() == Material.BARRIER) {
                    event.setCancelled(true);
                }
                if (clickedItem != null && clickedItem.getType() == Material.BRAIN_CORAL_BLOCK) {
                    event.setCancelled(true);
                }
                if (clickedItem != null && clickedItem.getType() == Material.IRON_AXE) {
                    event.setCancelled(true);
                }
            }
            if (event.getSlot() == 40) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        ItemStack itemMain = e.getMainHandItem();
        ItemStack itemleft = e.getOffHandItem();
        if (itemMain.getType() == Material.BARRIER) {
            e.setCancelled(true);
        }
        if (itemleft != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemHold(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItem(event.getNewSlot()) != null &&
                player.getInventory().getItem(event.getNewSlot()).getType() == Material.BARRIER) {
            event.setCancelled(true);
        }
    }
}