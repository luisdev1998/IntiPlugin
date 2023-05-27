package inti.intiplugin.Commands;

import inti.intiplugin.Aditionals.ConfigManager;
import inti.intiplugin.Aditionals.CustomConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ConfigCommands implements CommandExecutor {
    private Plugin Plugin;

    private ConfigManager ConfigManager;
    public ConfigCommands(Plugin Plugin, ConfigManager ConfigManager){
        this.Plugin = Plugin;
        this.ConfigManager = ConfigManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(ChatColor.YELLOW + Plugin.getName() + ChatColor.GREEN +" Configuración Reiniciada");
        Plugin.getConfig().options().copyDefaults();
        Plugin.saveDefaultConfig();
        CustomConfig.reload();
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();
        ConfigManager.updateConfigurationSection(CustomConfig.get());
        return true;
    }
}
