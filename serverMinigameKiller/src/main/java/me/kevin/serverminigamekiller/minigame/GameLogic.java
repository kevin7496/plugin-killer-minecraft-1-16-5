package me.kevin.serverminigamekiller.minigame;

import me.kevin.serverminigamekiller.Main;
import me.kevin.serverminigamekiller.handler.TitileAPI;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic implements Listener {

    World world = Bukkit.getWorld("world");
    ItemMeta meta;

    public boolean gamerun;
    public Player killer;
    public List<Player> surv = new ArrayList<>();
    public List<Player> dead = new ArrayList<>();
    public List<Player> players = new ArrayList<>();

    public List<Location> loc = new ArrayList<>();
    public Location locSpawnSurv = new Location(world, 4, 24, 59);
    public Location locSpawnKiller = new Location(world, -33, 14, -9);

    public ItemStack itemAxe = new ItemStack(Material.IRON_AXE);
    public ItemStack itemZippo = new ItemStack(Material.LAVA_BUCKET);
    int task = 10;

    InventoryUtils inv = new InventoryUtils();
    TitileAPI titileAPI = new TitileAPI();
    SpawnLoot spawnLoot = new SpawnLoot();

    Random r = new Random();

    //Коструктор
    public GameLogic () {
        //Координаты каждого блоков ворот
        loc.add(new Location(world, 1, 24, 61));
        loc.add(new Location(world, 2, 24, 61));
        loc.add(new Location(world, 3, 24, 61));
        loc.add(new Location(world, 4, 24, 61));
        loc.add(new Location(world, 5, 24, 61));
        loc.add(new Location(world, 6, 24, 61));

        loc.add(new Location(world, 1, 25, 61));
        loc.add(new Location(world, 2, 25, 61));
        loc.add(new Location(world, 3, 25, 61));
        loc.add(new Location(world, 4, 25, 61));
        loc.add(new Location(world, 5, 25, 61));
        loc.add(new Location(world, 6, 25, 61));

        loc.add(new Location(world, 1, 26, 61));
        loc.add(new Location(world, 2, 26, 61));
        loc.add(new Location(world, 3, 26, 61));
        loc.add(new Location(world, 4, 26, 61));
        loc.add(new Location(world, 5, 26, 61));
        loc.add(new Location(world, 6, 26, 61));

        loc.add(new Location(world, 1, 27, 61));
        loc.add(new Location(world, 2, 27, 61));
        loc.add(new Location(world, 3, 27, 61));
        loc.add(new Location(world, 4, 27, 61));
        loc.add(new Location(world, 5, 27, 61));
        loc.add(new Location(world, 6, 27, 61));

    }

    //Удаление блоков
    private void removeAllBlock() {
        for (int x = -100; x <= 100; x++) {
            for (int y = 0; y <= 255; y++) {
                for (int z = -100; z <= 100; z++) {
                    if (world.getBlockAt(x, y, z).getType() == Material.POPPY) {
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                    if (world.getBlockAt(x, y, z).getType() == Material.TRIPWIRE) {
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
    }

    //Удаление всех сущностей
    private void removeEntities() {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) {
                continue;
            }
            if (entity.getType() != EntityType.PLAYER) {
                entity.remove();
            }
        }
    }

    //Запуск таймеры до начало игры
    public void starttimer() {
        new BukkitRunnable() {

            @Override
            public void run() {
                task--;
                for (Player p1 : Bukkit.getOnlinePlayers()) {
                    titileAPI.sendTitle(p1, ChatColor.YELLOW + "До начало игры:", "" + task);
                }
                if (task <= 0) {
                    cancel();
                    start();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }


    //Создание предмета
    public void items() {
        meta = itemAxe.getItemMeta();
        List<String> axeLore = new ArrayList<>();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "ТОПОР");
        meta.setUnbreakable(true);
        meta.setLore(axeLore);
        AttributeModifier modifier = new AttributeModifier("generic.attackDamage", 4.0, AttributeModifier.Operation.ADD_NUMBER);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemAxe.setItemMeta(meta);
        itemAxe.setDurability((short) 0);

        meta = itemZippo.getItemMeta();
        List<String> zippoLore = new ArrayList<>();
        zippoLore.add(ChatColor.WHITE + "Zippo — металлическая бензиновая");
        zippoLore.add(ChatColor.WHITE + "ветрозащищённая зажигалка");
        meta.setDisplayName(ChatColor.GOLD + "Зажигалка Zippo");
        meta.setLore(zippoLore);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemZippo.setItemMeta(meta);
    }

    //Открытье Глав. Дверей выхода
    public void open_exit() {
        if (gamerun) {
            for (Location l : loc) {
                l.getBlock().setType(Material.REDSTONE_BLOCK);
            }
            return;
        }
        for (Location l : loc) {
            l.getBlock().setType(Material.AIR);
        }
    }

    //Событье если игрок зашёл в игру
    @EventHandler
    public void joinPlayer(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        players.add(p);
        if (gamerun) {
            p.setGameMode(GameMode.SPECTATOR);
        }
        if (players.size() >= 3) {
            items();
            starttimer();
        }
    }

    //Событье если игрок покинул игру
    @EventHandler
    public void leavePlayer(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        players.remove(p);
        if (gamerun) {
            if (surv.contains(p)) {
                surv.remove(p);
            }
            if (p.equals(killer)) {
                killer = null;
                end();
            }
        }
    }

    //Событье если умер игрок
    @EventHandler
    public void deadPlayer(PlayerDeathEvent e) {
        Player p = e.getEntity();
        p.setGameMode(GameMode.SPECTATOR);
        dead.add(p);
        for (Player p1 : dead) {
            p1.setGameMode(GameMode.SPECTATOR);
            p1.setDisplayName(ChatColor.GRAY + p1.getName());;
        }
        if (dead.size() == surv.size()) {
            end();
        }
    }

    //Кд на атакуещего
    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player attacker = (Player) e.getDamager();
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10, true, false));
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 10, true, false));
        }
    }

    //Старт игры
    public void start() {
        removeAllBlock();
        removeEntities();
        open_exit();
        //Перечеление всех игроков из спика players
        for (Player player : players) {
            player.getInventory().clear();
            inv.inventory_start(player);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.setGameMode(GameMode.ADVENTURE);
        }
        gamerun = true;
        spawnLoot.spawn();
        int index = r.nextInt(players.size());
        killer = players.get(index);
        players.remove(killer);
        surv.addAll(players);
        players.removeAll(surv);

        for (Player player : surv) {
            player.setGameMode(GameMode.ADVENTURE);
            titileAPI.sendTitle(player, ChatColor.GREEN + "Ты, Выживший.", ChatColor.WHITE + " Цель: Покинуть локацию.");
            player.getInventory().addItem(itemZippo);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 0, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 0, true, false));
            player.setCustomName(ChatColor.GREEN + player.getName());
            player.setDisplayName(ChatColor.GREEN + player.getName());
            player.teleport(locSpawnSurv);
        }

        killer.getInventory().addItem(itemAxe);
        titileAPI.sendTitle(killer, ChatColor.RED + "Ты, Маньяк.", ChatColor.WHITE + " Цель: Убить всех.");
        killer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, true, false));
        killer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 0, true, false));
        killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1, true, false));
        killer.getInventory().setItem(39, new ItemStack(Material.BRAIN_CORAL_BLOCK));
        killer.setCustomName(ChatColor.RED + killer.getName());
        killer.setDisplayName(ChatColor.RED + killer.getName());
        killer.setGameMode(GameMode.ADVENTURE);
        killer.teleport(locSpawnKiller);
    }


    //Конец игры
    public void end() {
        gamerun = false;
        task = 10;
        killer = null;
        surv.clear();
        dead.clear();
        players.clear();
        players.addAll(Bukkit.getOnlinePlayers());
        open_exit();
        //Перечеление всех игроков из спика players
        for (Player player : players) {
            player.getInventory().clear();
            titileAPI.sendTitle(player, ChatColor.GREEN + "Игра закончена.", "");
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.setGameMode(GameMode.ADVENTURE);
        }
        open_exit();
    }
}
