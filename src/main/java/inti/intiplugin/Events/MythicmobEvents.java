package inti.intiplugin.Events;

import inti.intiplugin.Models.MythicConfig.MythicMob;
import inti.intiplugin.Models.MythicConfig.MythicMobCoin;
import inti.intiplugin.Utils.GlowLogic;
import inti.intiplugin.Utils.MythicmobLogic;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class MythicmobEvents implements Listener {
    private final Plugin Plugin;
    private final MythicmobLogic MythicmobLogic;
    private final Economy Economy;
    public MythicmobEvents(Plugin Plugin, MythicmobLogic MythicmobLogic, Economy Economy){
        this.Plugin = Plugin;
        this.MythicmobLogic = MythicmobLogic;
        this.Economy = Economy;
    }
    @EventHandler
    public void GlowMob(MythicMobSpawnEvent event){
        MythicMob MythicMob = MythicmobLogic.MythicConfigExist(event.getMobType().getInternalName());
        if(MythicMob != null){
            if(MythicMob.Glowing){
                GlowLogic.GlowEntity(MythicMob.GlowColor,event.getEntity());
                event.getEntity().setGlowing(true);
            }
        }
    }

    @EventHandler
    public void OnMythicHit(EntityDamageByEntityEvent event){
        Entity damagedEntity = event.getEntity();
        if (!(damagedEntity instanceof LivingEntity)) {
            return;
        }
        boolean isMythicMob = MythicBukkit.inst().getAPIHelper().isMythicMob(damagedEntity);
        if (isMythicMob) {
            ActiveMob activeMobOpt = MythicBukkit.inst().getMobManager().getMythicMobInstance(damagedEntity);
            if (activeMobOpt != null) {
                String mythicMobName = activeMobOpt.getType().getInternalName();
                MythicMob MythicMob = MythicmobLogic.MythicConfigExist(mythicMobName);
                if(MythicMob != null){
                    if(MythicMob.OnRankedEnable){
                        //aquí
                        if (event.getDamager() instanceof Player) {
                            Player attacker = (Player) event.getDamager();
                            UUID mobId = damagedEntity.getUniqueId();
                            UUID playerId = attacker.getUniqueId();
                            double damage = event.getDamage();
                            MythicmobLogic.trackDamage(mobId, playerId, damage);
                        }

                    }
                }
            }
        }
    }

    @EventHandler
    public void DropCoin(MythicMobDeathEvent event){
        MythicMob MythicMob = MythicmobLogic.MythicConfigExist(event.getMobType().getInternalName());
        if(MythicMob != null){
            if(MythicMob.Coin){
                MythicMobCoin mythicMobCoinInstance = new MythicMobCoin();
                mythicMobCoinInstance.GlowCoinColor = MythicMob.GlowCoinColor;
                mythicMobCoinInstance.Stack = MythicMob.Stack;
                mythicMobCoinInstance.MinMoney = MythicMob.MinMoney;
                mythicMobCoinInstance.MaxMoney = MythicMob.MaxMoney;
                MythicMob.MythicMobCoin = mythicMobCoinInstance;
                MythicmobLogic.dropCoin(event.getEntity(),MythicMob.MythicMobCoin);
            }
        }
    }

    @EventHandler
    private void onItemMerge(ItemMergeEvent event) {
        ItemStack item1 = event.getEntity().getItemStack();
        ItemStack item2 = event.getTarget().getItemStack();

        if (MythicmobLogic.isSpecialNugetGold(item1) || MythicmobLogic.isSpecialNugetGold(item2)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack pickedUpItem = event.getItem().getItemStack();
        if (MythicmobLogic.isSpecialNugetGold(pickedUpItem)) {
            int Money = MythicmobLogic.getMoneyFromMythicCoin(pickedUpItem);
            EconomyResponse response = Economy.depositPlayer(event.getPlayer(), Money);
            if (response.transactionSuccess()) {
                event.getPlayer().getWorld().playSound(event.getPlayer(), Sound.ENTITY_ENDER_EYE_DEATH, 2.0F, 1.0F);
                event.getPlayer().sendMessage("§aHas recibido: §e" + Money + "⛃");
            }
            event.setCancelled(true);
            event.getItem().remove();
        }
    }

    @EventHandler
    public void onMythicMobDeath(EntityDeathEvent event) {
        Entity deadEntity = event.getEntity();
        UUID mobId = deadEntity.getUniqueId();
        boolean isMythicMob = MythicBukkit.inst().getAPIHelper().isMythicMob(deadEntity);
        if (isMythicMob) {
            ActiveMob activeMobOpt = MythicBukkit.inst().getMobManager().getMythicMobInstance(deadEntity);
            if (activeMobOpt != null) {
                String mythicMobName = activeMobOpt.getType().getInternalName();
                MythicMob MythicMob = MythicmobLogic.MythicConfigExist(mythicMobName);
                MythicmobLogic.getDamageRewards(mobId,MythicMob);
            }
        }
    }
}
