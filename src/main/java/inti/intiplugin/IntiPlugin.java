package inti.intiplugin;

import inti.intiplugin.Aditionals.ConfigManager;
import inti.intiplugin.Aditionals.VaultConfig;
import inti.intiplugin.Aditionals.CustomConfig;
import inti.intiplugin.Commands.*;
import inti.intiplugin.Listeners.MythicmobEvents;
import inti.intiplugin.Listeners.PlayerEvents;
import inti.intiplugin.Utils.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class IntiPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "INTICRAFT PLUGIN INICIANDO");

        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        CustomConfig.setup();
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();

        //Instances
        ConfigManager ConfigManager = new ConfigManager(CustomConfig.get());
        VaultConfig VaultConfig = new VaultConfig(this);

        WhitelistLogic WhitelistLogic = new WhitelistLogic(this,ConfigManager);
        RestartLogic RestartLogic = new RestartLogic(this,ConfigManager);
        RuletaLogic RuletaLogic = new RuletaLogic(this,ConfigManager);
        MythicmobLogic MythicmobLogic = new MythicmobLogic(this,ConfigManager);

        ConfigCommands ConfigCommands = new ConfigCommands(this,ConfigManager);
        RestartCommands RestartCommands = new RestartCommands(RestartLogic);
        RuletaCommands RuletaCommands = new RuletaCommands(this,RuletaLogic);
        MythicmobsCommands MythicmobsCommands = new MythicmobsCommands(MythicmobLogic);

        //Run
        WhitelistLogic.run();
        this.getCommand("intiplugin").setExecutor(new IntiCommands(this, ConfigManager, ConfigCommands, RestartCommands, RuletaCommands, MythicmobsCommands));
        this.getServer().getPluginManager().registerEvents(new PlayerEvents(this,ConfigManager),this);
        this.getServer().getPluginManager().registerEvents(new MythicmobEvents(this, MythicmobLogic,VaultConfig.setupEconomy()),this);
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "INTICRAFT PLUGIN APAGANDO");
    }
}
