package me.kevin.serverminigamekiller.handler;

import me.kevin.serverminigamekiller.minigame.GameLogic;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ItemUse implements Listener {

    GameLogic gameLogic = new GameLogic();
    onInterectiv onInterectiv = new onInterectiv(gameLogic);
    TitileAPI titileAPI = new TitileAPI();

    @EventHandler
    public void onItemUseSyringe(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block targetclick = e.getClickedBlock();
        Block targetcast = p.getTargetBlockExact(5);
        Random r = new Random();
        int pross = r.nextInt(10) + 1;

        if ((targetcast != null && onInterectiv.interactableBlocks.contains(targetcast.getType())) ||
                (targetclick != null && onInterectiv.interactableBlocks.contains(targetclick.getType()))) {
            return;
        }

        if (targetcast == null && targetclick == null) {
            if (p.getInventory().getItemInMainHand().getType() == Material.IRON_HORSE_ARMOR) {
                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                p.playSound(p.getLocation(), Sound.BLOCK_HONEY_BLOCK_HIT, 10, 2);
                if (pross == 2) {
                    titileAPI.sendSubtitle(p, ChatColor.GREEN + "Жидкость оказалась Бустом");
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999999, 1, true, false));
                } else {
                    titileAPI.sendSubtitle(p, ChatColor.RED + "Жидкость оказалась Ядом");
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 9999999, 10, true, false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999999, 3, true, false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 3, true, false));
                }
            }
        }
    }
}
