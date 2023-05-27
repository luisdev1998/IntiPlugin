package inti.intiplugin.Utils;

import inti.intiplugin.Aditionals.ConfigManager;
import inti.intiplugin.Models.RuletaConfig.Ruleta;
import inti.intiplugin.Models.RuletaConfig.RuletaRewards;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class RuletaLogic {
    private final Plugin Plugin;
    private final ConfigManager ConfigManager;
    public RuletaLogic(Plugin Plugin, ConfigManager ConfigManager){
        this.Plugin = Plugin;
        this.ConfigManager = ConfigManager;
    }
    public Ruleta verificarRuleta(String ruleta){
        for(Ruleta Ruleta : ConfigManager.getRuletaConfigRuleta()){
            if(Ruleta.Ruleta.equals(ruleta)){
                return Ruleta;
            }
        }
        return null;
    }
    public void Girar(Ruleta Ruleta, Player Player){
        Player.playSound(Player.getLocation(), Sound.AMBIENT_CAVE, 2.0f, 2.0f);
        Player.playSound(Player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 2.0f, 2.0f);
        Player.sendTitle(Ruleta.Title, Ruleta.Subtitle, 10, 60, 10);
        Plugin.getServer().getScheduler().runTaskLater(Plugin, () -> {
            BukkitScheduler scheduler = Plugin.getServer().getScheduler();
            for(int i = 1; i < 25; ++i) {
                scheduler.runTaskLater(Plugin, Generar(i, Ruleta, Player), (i * i / 4));
            }
        }, 60L);
    }
    private Runnable Generar(int count, Ruleta Ruleta, Player Player){
        return () -> {
            Random rand = new Random();
            int randomNum = rand.nextInt(Ruleta.Rewards.size());
            if (count == 24) {
                Plugin.getServer().getScheduler().runTaskLater(Plugin, () -> {
                    Premio(randomNum, Ruleta, Player);
                }, 40L);
            } else {
                Player.playSound(Player.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1.0F, 2.0F);
                Player.sendTitle(Ruleta.Rewards.get(randomNum).Symbol, "§7§l▲", 0, 60, 0);
            }
        };
    }

    private void Premio(int randomNum, Ruleta Ruleta, Player Player){
        RuletaRewards reward = Ruleta.Rewards.get(randomNum);

        for(String commands : reward.Commands){
            Plugin.getServer().dispatchCommand(Plugin.getServer().getConsoleSender(), commands.replace("%player%",Player.getName()));
        }
        Player.playSound(Player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
        Player.sendTitle(reward.Title.replace("{symbol}",reward.Symbol), reward.Subtitle, 5, 120, 5);
        Plugin.getServer().getConsoleSender().sendMessage(Player.getName() + " ganó: " + reward.Subtitle);
    }
}
