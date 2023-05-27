package inti.intiplugin.Commands;

import inti.intiplugin.Aditionals.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class IntiCommands implements CommandExecutor {
    private Plugin Plugin;
    private final ConfigManager ConfigManager;
    private final ConfigCommands configCommands;
    private final RestartCommands restartCommands;
    private final RuletaCommands ruletaCommands;
    private final MythicmobsCommands MythicmobsCommands;
    public IntiCommands(Plugin Plugin, ConfigManager ConfigManager, ConfigCommands configCommands, RestartCommands restartCommands, RuletaCommands ruletaCommands, MythicmobsCommands MythicmobsCommands) {
        this.Plugin = Plugin;
        this.ConfigManager = ConfigManager;
        this.configCommands = configCommands;
        this.restartCommands = restartCommands;
        this.ruletaCommands = ruletaCommands;
        this.MythicmobsCommands = MythicmobsCommands;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            return true;
        }
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission(ConfigManager.getPluginPermission())) {
                player.sendMessage(ChatColor.RED + "No tienes el permiso para ejecutar este comando.");
                return true;
            }
        }
        switch (strings[0]){
            case "reload":
                return configCommands.onCommand(commandSender,command,s,strings);
            case "restart":
                return restartCommands.onCommand(commandSender,command,s,strings);
            case "ruleta":
                return ruletaCommands.onCommand(commandSender,command,s,strings);
            case "spawn":
                return MythicmobsCommands.onCommand(commandSender,command,s,strings);
            default:
                commandSender.sendMessage(ChatColor.YELLOW + Plugin.getName() + ChatColor.RED +" Este comando no existe");
                return true;
        }
    }
}
