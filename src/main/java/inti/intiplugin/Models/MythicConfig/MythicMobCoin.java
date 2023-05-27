package inti.intiplugin.Models.MythicConfig;

import inti.intiplugin.Utils.GlowLogic;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class MythicMobCoin {
    public String GlowCoinColor;
    public Integer Stack;
    public Integer MinMoney;
    public Integer MaxMoney;

    public ItemStack createMythicCoin(Plugin Plugin) {
        ItemStack nugetGold = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta nugetGoldMeta = nugetGold.getItemMeta();

        NamespacedKey key = new NamespacedKey(Plugin, "mythic_coin");
        nugetGoldMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "special");

        NamespacedKey keyMaxMoney = new NamespacedKey(Plugin, "mythic_coin_MinMoney");
        nugetGoldMeta.getPersistentDataContainer().set(keyMaxMoney, PersistentDataType.INTEGER, MinMoney);

        NamespacedKey keyMinMoney = new NamespacedKey(Plugin, "mythic_coin_MaxMoney");
        nugetGoldMeta.getPersistentDataContainer().set(keyMinMoney, PersistentDataType.INTEGER, MaxMoney);

        nugetGold.setItemMeta(nugetGoldMeta);
        return nugetGold;
    }
}
