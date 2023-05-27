package inti.intiplugin.Utils;

import inti.intiplugin.Aditionals.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WhitelistLogic {
    private final Plugin Plugin;
    private final ConfigManager ConfigManager;
    public WhitelistLogic(Plugin Plugin, ConfigManager ConfigManager){
        this.Plugin = Plugin;
        this.ConfigManager = ConfigManager;
    }
    public void run(){
        if(ConfigManager.getWhitelistConfigEnable()){
            Integer segundos = ConfigManager.getWhitelistConfigTime()*20;
            Bukkit.getScheduler().runTaskLater(Plugin, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist off");
            },segundos);
        }
    }
}
