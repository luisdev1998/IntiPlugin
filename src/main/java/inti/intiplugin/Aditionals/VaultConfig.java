package inti.intiplugin.Aditionals;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultConfig {
    private Economy economy = null;
    private final Plugin plugin;

    public VaultConfig(Plugin plugin) {
        this.plugin = plugin;
    }

    public Economy setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }
        economy = rsp.getProvider();
        return economy;
    }
}
