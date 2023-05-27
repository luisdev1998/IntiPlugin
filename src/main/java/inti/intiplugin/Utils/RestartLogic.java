package inti.intiplugin.Utils;

import inti.intiplugin.Aditionals.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RestartLogic {
    private final Plugin Plugin;
    private final ConfigManager ConfigManager;
    public RestartLogic(Plugin Plugin, ConfigManager ConfigManager){
        this.Plugin = Plugin;
        this.ConfigManager = ConfigManager;
    }
    private void Sonido(Player Player){
        Player.playSound(Player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0F, 1.5F);
    }
    public void Mensaje(String command, String Time){
        if(command.equals(ConfigManager.getRestartConfigCommandMessage().replace(" ","").replace("{time}",""))){
            for (Player Player: Bukkit.getOnlinePlayers()) {
                Sonido(Player);
                Player.sendTitle(ConfigManager.getRestartConfigTitle().replace("{time}",Time),ConfigManager.getRestartConfigSubTitle().replace("{time}",Time));
            }
        }
    }

    public void CuentaRegresiva(String command){
        if(command.equals(ConfigManager.getRestartConfigCommandRestart())){
            for (Player Player: Bukkit.getOnlinePlayers()) {
                Sonido(Player);
                Player.sendTitle("§a§l¡REINICIO EN 3!","§cSolo tomará 1 minuto :D");
                Bukkit.getScheduler().runTaskLater(Plugin, () -> {
                    Sonido(Player);
                    Player.sendTitle("§a§l¡REINICIO EN 2!","§cSolo tomará 1 minuto :D");
                },40L);
                Bukkit.getScheduler().runTaskLater(Plugin, () -> {
                    Sonido(Player);
                    Player.sendTitle("§a§l¡REINICIO EN 1!","§cSolo tomará 1 minuto :D");
                },80L);
            }
            Bukkit.getScheduler().runTaskLater(Plugin, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist on");
            },120L);
            Bukkit.getScheduler().runTaskLater(Plugin, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "Stop");
            },160L);
        }
    }
}
