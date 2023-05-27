package inti.intiplugin.Commands;

import inti.intiplugin.Utils.RestartLogic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
public class RestartCommands implements CommandExecutor {
    public RestartLogic RestartLogic;
    public RestartCommands(RestartLogic RestartLogic){
        this.RestartLogic = RestartLogic;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length > 2){
            RestartLogic.Mensaje(strings[1],strings[2]);
            return true;
        }
        RestartLogic.CuentaRegresiva(strings[1]);
        return true;
    }
}
