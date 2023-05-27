package inti.intiplugin.Commands;

import inti.intiplugin.Models.RuletaConfig.Ruleta;
import inti.intiplugin.Utils.RuletaLogic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RuletaCommands implements CommandExecutor {
    private RuletaLogic RuletaLogic;
    private Plugin Plugin;
    public RuletaCommands(Plugin Plugin, RuletaLogic RuletaLogic){
        this.RuletaLogic = RuletaLogic;
        this.Plugin = Plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Ruleta Ruleta = RuletaLogic.verificarRuleta(strings[1]);
        if(Ruleta != null){
            if(Ruleta.AllPlayer) {
                for (Player Player : Plugin.getServer().getOnlinePlayers()) {
                    RuletaLogic.Girar(Ruleta, Player);
                }
                return true;
            }
            Player Player = Plugin.getServer().getPlayerExact(strings[2]);
            if(Player != null) {
                RuletaLogic.Girar(Ruleta, Player);
            }else{
                commandSender.sendMessage(ChatColor.YELLOW + Plugin.getName() + ChatColor.RED +" El jugador " + strings[1] + " no existe");
            }
        }else{
            commandSender.sendMessage(ChatColor.YELLOW + Plugin.getName() + ChatColor.RED +" Esta ruleta no existe");
        }
        return false;
    }
}
