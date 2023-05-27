package inti.intiplugin.Commands;

import inti.intiplugin.Utils.MythicmobLogic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MythicmobsCommands implements CommandExecutor {
    private MythicmobLogic MythicmobLogic;
    public MythicmobsCommands(MythicmobLogic MythicmobLogic){
        this.MythicmobLogic = MythicmobLogic;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        MythicmobLogic.spawnMythicMob(strings[1]);
        return true;
    }
}
