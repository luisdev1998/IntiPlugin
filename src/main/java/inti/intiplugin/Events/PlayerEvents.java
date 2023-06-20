package inti.intiplugin.Events;

import inti.intiplugin.Aditionals.ConfigManager;
import inti.intiplugin.Models.FirstjoinConfig.FirstItems;
import inti.intiplugin.Models.RankbenefitConfig.Rankbenefit;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerEvents implements Listener {
    private final Plugin Plugin;
    private final ConfigManager ConfigManager;
    private Map<UUID, Long> playerHitTimestamps;
    public PlayerEvents(Plugin Plugin, ConfigManager ConfigManager){
        this.Plugin = Plugin;
        this.ConfigManager = ConfigManager;
        playerHitTimestamps = new HashMap<>();
    }

    public String getPlayerPrimaryGroup(Player player) {
        // Obtén la instancia de LuckPerms
        LuckPerms luckPerms = LuckPermsProvider.get();

        // Obtén el objeto User para el jugador
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if (user != null) {
            // Obtén el grupo principal del jugador
            String primaryGroup = user.getPrimaryGroup();
            return primaryGroup;
        }

        return null;
    }

    @EventHandler
    private void onPlayerDeath(PlayerRespawnEvent event){
        ////*** death-config ***////
        Player Player = event.getPlayer();
        if(ConfigManager.getDeathConfigEnable()){
            Player.playSound(Player.getLocation(), Sound.ENTITY_ITEM_BREAK, 2.0F, 1.0F);
            Player.sendTitle(ConfigManager.getDeathConfigTitle(), ConfigManager.getDeathConfigSubTitle(), 5, 120, 5);
        }
        //************************//
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event){
        ////*** rankbenefit-config ***////
        if(ConfigManager.getRankBenefitConfigEnable().Enable){
            Player Player = event.getEntity();
            String rank = getPlayerPrimaryGroup(Player);
            if (rank != null) {
                for (Rankbenefit rankPlayer : ConfigManager.getRankBenefitConfigEnable().Rankbenefit) {
                    if(rankPlayer.Rank.equals(rank)) {
                        if(rankPlayer.OnDeathEnable){
                            String[] soundInfo = rankPlayer.OnJoinSound.split(",");
                            Sound sound = Sound.valueOf(soundInfo[0]);
                            float volume = Float.parseFloat(soundInfo[1]);
                            float pitch = Float.parseFloat(soundInfo[2]);
                            Player.playSound(Player.getLocation(),sound,volume,pitch);
                        }
                    }
                }
            }
        }
        //*****************************//
    }

    @EventHandler
    private void onPlayerFirstJoin(PlayerJoinEvent event){
        Player Player = event.getPlayer();
        if (!Player.hasPlayedBefore()){
            if(ConfigManager.getFirstitemConfigEnable()){
                //Entregar items
                for(FirstItems items : ConfigManager.getFirstitemConfigItems()){
                    ItemStack customItem = items.createCustomItemStack();
                    if (items.Equip) {
                        String materialName = items.MaterialName.toUpperCase();
                        if (materialName.contains("HELMET")) {
                            Player.getInventory().setHelmet(customItem);
                        } else if (materialName.contains("CHESTPLATE")) {
                            Player.getInventory().setChestplate(customItem);
                        } else if (materialName.contains("LEGGINGS")) {
                            Player.getInventory().setLeggings(customItem);
                        } else if (materialName.contains("BOOTS")) {
                            Player.getInventory().setBoots(customItem);
                        } else {
                            Player.getInventory().addItem(customItem);
                        }
                    } else {
                        Player.getInventory().addItem(customItem);
                    }
                }
                //Ejecutar Comandos
                for(String commands : ConfigManager.getFirstitemConfigCommands()){
                    Plugin.getServer().dispatchCommand(Plugin.getServer().getConsoleSender(), commands.replace("%player%",Player.getName()));
                }
                Plugin.getServer().getScheduler().runTaskLater(Plugin, () -> {
                    //Message
                    Player.sendTitle(ConfigManager.getFirstitemConfigFirstTitle(), ConfigManager.getFirstitemConfigFirstSubtitle(), 5, 100, 5);
                    //Broadcastmessage
                    for(String broadcast : ConfigManager.getFirstitemConfigBroadcastMessage()){
                        Plugin.getServer().broadcastMessage(broadcast.replace("%player%",Player.getName()));
                    }
                    //Sound
                    for (Player Players : Plugin.getServer().getOnlinePlayers()) {
                        Players.playSound(Players.getLocation(), Sound.ENTITY_STRIDER_HAPPY, 1.0F, 2.0F);
                    }
                }, 5L);
                if(ConfigManager.getFirstitemConfigSecondMessageEnable()){
                    Plugin.getServer().getScheduler().runTaskLater(Plugin, () -> {
                        Player.sendTitle(ConfigManager.getFirstitemConfigSecondTitle(), ConfigManager.getFirstitemConfigSecondSubtitle(), 5, 100, 5);
                    }, 105L);
                }
                if(ConfigManager.getFirstitemConfigThirdMessageEnable()){
                    Plugin.getServer().getScheduler().runTaskLater(Plugin, () -> {
                        Player.sendTitle(ConfigManager.getFirstitemConfigThirdTitle(), ConfigManager.getFirstitemConfigThirdSubtitle(), 5, 100, 5);
                    }, 210L);
                }
            }
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        ////*** rankbenefit-config ***////
        if(ConfigManager.getRankBenefitConfigEnable().Enable){
            Player Player = event.getPlayer();
            String rank = getPlayerPrimaryGroup(Player);
            if (rank != null) {
                for(Rankbenefit rankPlayer : ConfigManager.getRankBenefitConfigEnable().Rankbenefit){
                    if(rankPlayer.Rank.equals(rank)) {
                        if(rankPlayer.OnJoinEnable){
                            //BroadcastMessage
                            for(String broadcastmessage : rankPlayer.OnJoinBroadcaastMessage){
                                Plugin.getServer().broadcastMessage(broadcastmessage.replace("%player%",Player.getName()));
                            }
                            //Sound
                            for (Player PlayerOnline : Plugin.getServer().getOnlinePlayers()) {
                                String[] soundInfo = rankPlayer.OnJoinSound.split(",");
                                Sound sound = Sound.valueOf(soundInfo[0]);
                                float volume = Float.parseFloat(soundInfo[1]);
                                float pitch = Float.parseFloat(soundInfo[2]);
                                PlayerOnline.playSound(PlayerOnline.getLocation(),sound,volume,pitch);
                            }
                        }
                    }
                }
            }
        }
        //*****************************//
    }

    @EventHandler
    private void onPlayerHit(EntityDamageEvent event){
        if (event.isCancelled()) {
            return;
        }
        ////*** rankbenefit-config ***////
        if(ConfigManager.getRankBenefitConfigEnable().Enable){
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                double healthBefore = player.getHealth();
                double damage = event.getDamage();
                double healthAfter = Math.max(healthBefore - damage, 0);
                if (healthBefore > healthAfter) {
                    String rank = getPlayerPrimaryGroup(player);
                    if (rank != null) {
                        for (Rankbenefit rankPlayer : ConfigManager.getRankBenefitConfigEnable().Rankbenefit) {
                            if (rankPlayer.Rank.equals(rank)) {
                                if(rankPlayer.OnHitEnable){
                                    UUID playerId = player.getUniqueId();
                                    long currentTime = System.currentTimeMillis();
                                    long lastHitTime = playerHitTimestamps.getOrDefault(playerId, 0L);
                                    long timeDifference = currentTime - lastHitTime;
                                    if (timeDifference >= rankPlayer.OnHitInterval*1000) { // 1000 ms = 1 segundo
                                        String[] soundInfo = rankPlayer.OnHitSound.split(",");
                                        Sound sound = Sound.valueOf(soundInfo[0]);
                                        float volume = Float.parseFloat(soundInfo[1]);
                                        float pitch = Float.parseFloat(soundInfo[2]);
                                        playerHitTimestamps.put(playerId, currentTime);
                                        player.getWorld().playSound(player.getLocation(),sound,volume,pitch);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //*****************************//
    }

    @EventHandler
    private void onPlayerDeath(EntityDeathEvent event){
        //*****************************//
        if(ConfigManager.getRankBenefitConfigEnable().Enable){
            if(event.getEntity() instanceof Player){
                Player Player = (Player) event.getEntity();
                String rank = getPlayerPrimaryGroup(Player);
                if (rank != null) {
                    for (Rankbenefit rankPlayer : ConfigManager.getRankBenefitConfigEnable().Rankbenefit) {
                        if(rankPlayer.Rank.equals(rank)) {
                            if(rankPlayer.OnDeathEnable){
                                String[] soundInfo = rankPlayer.OnDeathSound.split(",");
                                Sound sound = Sound.valueOf(soundInfo[0]);
                                float volume = Float.parseFloat(soundInfo[1]);
                                float pitch = Float.parseFloat(soundInfo[2]);
                                Player.getWorld().playSound(Player.getLocation(),sound,volume,pitch);
                            }
                        }
                    }
                }
            }
        }
        //*****************************//
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}
