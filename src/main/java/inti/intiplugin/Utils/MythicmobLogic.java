package inti.intiplugin.Utils;

import inti.intiplugin.Aditionals.ConfigManager;
import inti.intiplugin.Models.MythicConfig.MythicMob;
import inti.intiplugin.Models.MythicConfig.MythicMobCoin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MythicmobLogic {
    private final Plugin Plugin;
    private final ConfigManager ConfigManager;
    private final Map<UUID, Map<UUID, Double>> mobDamageMap = new HashMap<>();
    public MythicmobLogic(Plugin Plugin, ConfigManager ConfigManager){
        this.Plugin = Plugin;
        this.ConfigManager = ConfigManager;
    }
    public MythicMob MythicConfigExist(String MythicMobName){
        for(MythicMob mythicmob : ConfigManager.getMythicmobConfigMobs()){
            if(mythicmob.Name.equals(MythicMobName)){
                return mythicmob;
            }
        }
        return null;
    }
    public boolean isSpecialNugetGold(ItemStack item) {
        if (item.getType() != Material.GOLD_NUGGET) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        NamespacedKey key = new NamespacedKey(Plugin, "mythic_coin");
        boolean hasSpecialTag = itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING);

        return hasSpecialTag;
    }

    public int getMoneyFromMythicCoin(ItemStack item) {
        if (item.getType() != Material.GOLD_NUGGET) {
            return 0;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return 0;
        }

        NamespacedKey keyMaxMoney = new NamespacedKey(Plugin, "mythic_coin_MaxMoney");
        NamespacedKey keyMinMoney = new NamespacedKey(Plugin, "mythic_coin_MinMoney");
        if (itemMeta.getPersistentDataContainer().has(keyMaxMoney, PersistentDataType.INTEGER) &&
                itemMeta.getPersistentDataContainer().has(keyMinMoney, PersistentDataType.INTEGER)) {
            return ThreadLocalRandom.current().nextInt(
                    itemMeta.getPersistentDataContainer().getOrDefault(keyMinMoney, PersistentDataType.INTEGER, 0),
                    itemMeta.getPersistentDataContainer().getOrDefault(keyMaxMoney, PersistentDataType.INTEGER, 0)+ 1);
        }
        return 0;
    }
    public void dropCoin(Entity Entity, MythicMobCoin MythicMobCoin) {
        final Location localizacion = Entity.getLocation();
        for (int i = 1; i <= MythicMobCoin.Stack; ++i) {
            Bukkit.getScheduler().runTaskLater(Plugin, () -> {
                Entity.getWorld().playSound(Entity.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 2.0F, 2.0F);
                Item item = Entity.getWorld().dropItem(localizacion,MythicMobCoin.createMythicCoin(Plugin));
                double x = ThreadLocalRandom.current().nextDouble(-0.23, 0.17);
                double z = ThreadLocalRandom.current().nextDouble(-0.23, 0.17);
                item.setVelocity(new Vector(x, 0.6D, z));
                item.setCustomNameVisible(true);
                GlowLogic.GlowEntity(MythicMobCoin.GlowCoinColor, item);
                item.setGlowing(true);
            }, i * 2L);
        }
    }

    public void trackDamage(UUID mobId, UUID playerId, double damage) {
        mobDamageMap.putIfAbsent(mobId, new HashMap<>());
        Map<UUID, Double> playerDamageMap = mobDamageMap.get(mobId);
        playerDamageMap.put(playerId, playerDamageMap.getOrDefault(playerId, 0.0) + damage);
    }

    public void getDamageRewards(UUID mobId, MythicMob MythicMob) {
        if (!mobDamageMap.containsKey(mobId)) {
            return;
        }
        Map<UUID, Double> playerDamageMap = mobDamageMap.get(mobId);
        List<Map.Entry<UUID, Double>> sortedEntries = new ArrayList<>(playerDamageMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        int rank = 1;
        for (Map.Entry<UUID, Double> entry : sortedEntries) {
            Player player = Plugin.getServer().getPlayer(entry.getKey());
            Double damage = entry.getValue();
            if (player != null) {
                List<String> commandsToExecute = new ArrayList<>();
                boolean isRankedReward = false;

                for (Map<String, List<String>> reward : MythicMob.OnRankedRewards) {
                    for (Map.Entry<String, List<String>> rewardEntry : reward.entrySet()) {
                        if (Integer.parseInt(rewardEntry.getKey()) == rank) {
                            commandsToExecute.addAll(rewardEntry.getValue());
                            isRankedReward = true;
                            break;
                        }
                    }
                }

                if (commandsToExecute.isEmpty()) {
                    commandsToExecute = MythicMob.OnDefaultRewards;
                } else if (isRankedReward) {
                    Plugin.getServer().broadcastMessage(MythicMob.OnRankedTopMessage
                                        .replace("%player",player.getName())
                                        .replace("%top%",String.valueOf(rank)
                                        .replace("%damage",String.valueOf(damage))));
                }

                for (String command : commandsToExecute) {
                    String commandToDispatch = replaceWithRandom(command.replace("%player%", player.getName()));
                    Plugin.getServer().dispatchCommand(Plugin.getServer().getConsoleSender(), commandToDispatch);
                }

                rank++;
            }
        }

        // Elimina la entrada del mapa para evitar fugas de memoria
        mobDamageMap.remove(mobId);
    }


    public void spawnMythicMob(String mob){
        MythicMob mythicMob = MythicConfigExist(mob);
        if(mythicMob != null){
            Plugin.getServer().dispatchCommand(Plugin.getServer().getConsoleSender(),"mm mobs spawn "+ mob + " 1 " + mythicMob.OnCommandWorld+","+mythicMob.OnCommandCords);
        }
    }

    public String replaceWithRandom(String command) {
        Pattern pattern = Pattern.compile("\\{(\\d+)-(\\d+)\\}");
        Matcher matcher = pattern.matcher(command);
        StringBuffer result = new StringBuffer();

        Random random = new Random();

        while (matcher.find()) {
            int min = Integer.parseInt(matcher.group(1));
            int max = Integer.parseInt(matcher.group(2));
            int randomNumber = random.nextInt(max - min + 1) + min;
            matcher.appendReplacement(result, String.valueOf(randomNumber));
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
