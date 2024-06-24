package me.kevin.serverminigamekiller.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class TitileAPI implements Listener {

    public void sendTitle(Player p, String title, String subtitle) {
        p.sendTitle(title, subtitle, 10, 10, 10);
    }

    public void sendSubtitle(Player p, String subtitle) {
        p.sendTitle("", subtitle, 10, 10, 10);
    }

    public void clearTitle(Player p) {
        p.sendTitle("", "", 0, 0, 0);
    }

}
