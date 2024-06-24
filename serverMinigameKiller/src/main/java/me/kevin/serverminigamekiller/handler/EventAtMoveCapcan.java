package me.kevin.serverminigamekiller.handler;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventAtMoveCapcan implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Material blockUnderPlayer = p.getLocation().getBlock().getType();

        if (p.getGameMode() == GameMode.ADVENTURE) {
            if (blockUnderPlayer == Material.TRIPWIRE) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 10, true, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 160, 128, true, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 160, 10, true, false));
                p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 10, 2);
                p.getLocation().getBlock().setType(Material.POPPY);
            }
        }
    }
}
