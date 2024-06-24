package me.kevin.serverminigamekiller;


import me.kevin.serverminigamekiller.handler.*;
import me.kevin.serverminigamekiller.minigame.GameLogic;
import me.kevin.serverminigamekiller.minigame.InventoryUtils;
import me.kevin.serverminigamekiller.minigame.SpawnLoot;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        GameLogic gameLogic = new GameLogic();
        getServer().getPluginManager().registerEvents(new RemontGenereint(), this);
        getServer().getPluginManager().registerEvents(new EventAtMoveCapcan(), this);
        getServer().getPluginManager().registerEvents(new blockitem(gameLogic), this);
        getServer().getPluginManager().registerEvents(new onInterectiv(gameLogic), this);
        getServer().getPluginManager().registerEvents(new InventoryUtils(), this);
        getServer().getPluginManager().registerEvents(new GameLogic(), this);
        getServer().getPluginManager().registerEvents(new SpawnLoot(), this);
        getServer().getPluginManager().registerEvents(new TitileAPI(), this);
        getServer().getPluginManager().registerEvents(new ItemUse(), this);
        getLogger().info("Включён");

    }
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("Выключён");

    }
}