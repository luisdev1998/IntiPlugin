package inti.intiplugin.Aditionals;

import inti.intiplugin.Models.FirstjoinConfig.FirstItems;
import inti.intiplugin.Models.MythicConfig.MythicMob;
import inti.intiplugin.Models.RankbenefitConfig.Rankbenefit;
import inti.intiplugin.Models.RuletaConfig.Ruleta;
import inti.intiplugin.Models.RuletaConfig.RuletaRewards;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private ConfigurationSection ConfigurationSection;

    public ConfigManager(ConfigurationSection ConfigurationSection) {
        this.ConfigurationSection = ConfigurationSection;
    }

    public void updateConfigurationSection(ConfigurationSection newConfigurationSection) {
        this.ConfigurationSection = newConfigurationSection;
    }

    public String getPluginPermission() {
        return ConfigurationSection.getString("plugin-permission");
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public Boolean getWhitelistConfigEnable() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("whitelist-config");
        return config.getBoolean("enable");
    }

    public Integer getWhitelistConfigTime() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("whitelist-config");
        return config.getInt("seconds-after-start-server");
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public Boolean getRestartConfigEnable() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("restart-config");
        return config.getBoolean("enable");
    }

    public String getRestartConfigCommandMessage() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("restart-config");
        return config.getString("command-message");
    }

    public String getRestartConfigCommandRestart() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("restart-config");
        return config.getString("command-restart");
    }

    public String getRestartConfigTitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("restart-config");
        return config.getString("title");
    }

    public String getRestartConfigSubTitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("restart-config");
        return config.getString("subtitle");
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public List<Rankbenefit> getRankBenefitConfigEnable(){
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("rankbenefit-config");
        List<Rankbenefit> listRankbenefit = new ArrayList<>();
        for(String rank : config.getKeys(false)){
            Rankbenefit Rankbenefit = new Rankbenefit();
            Rankbenefit.Rank = config.getString(rank + ".rank");
            Rankbenefit.OnJoinEnable = config.getBoolean(rank + ".on-join.enable");
            Rankbenefit.OnJoinSound = config.getString(rank + ".on-join.sound");
            Rankbenefit.OnJoinBroadcaastMessage = config.getStringList(rank + ".on-join.broadcast-message");
            Rankbenefit.OnHitEnable = config.getBoolean(rank + ".on-hit.enable");
            Rankbenefit.OnHitSound = config.getString(rank + ".on-hit.sound");
            Rankbenefit.OnHitInterval = config.getInt(rank + ".on-hit.seconds-interval-sound");
            Rankbenefit.OnDeathEnable = config.getBoolean(rank + ".on-death.enable");
            Rankbenefit.OnDeathSound = config.getString(rank + ".on-death.sound");
            listRankbenefit.add(Rankbenefit);
        }
        return listRankbenefit;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    public Boolean getDeathConfigEnable(){
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("death-config");
        return config.getBoolean("enable");
    }
    public String getDeathConfigTitle(){
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("death-config");
        return config.getString("title");
    }
    public String getDeathConfigSubTitle(){
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("death-config");
        return config.getString("subtitle");
    }
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    public Boolean getFirstitemConfigEnable() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getBoolean("enable");
    }

    public String getFirstitemConfigFirstTitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getString("fist-title");
    }

    public String getFirstitemConfigFirstSubtitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getString("fist-subtitle");
    }

    public Boolean getFirstitemConfigSecondMessageEnable() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getBoolean("second-message-enable");
    }

    public String getFirstitemConfigSecondTitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getString("second-title");
    }

    public String getFirstitemConfigSecondSubtitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getString("second-subtitle");
    }

    public Boolean getFirstitemConfigThirdMessageEnable() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getBoolean("third-message-enable");
    }

    public String getFirstitemConfigThirdTitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getString("third-title");
    }

    public String getFirstitemConfigThirdSubtitle() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getString("third-subtitle");
    }

    public List<String> getFirstitemConfigBroadcastMessage() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getStringList("broadcast-message");
    }

    public List<String> getFirstitemConfigCommands() {
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config");
        return config.getStringList("commands");
    }
    public List<FirstItems> getFirstitemConfigItems(){
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("firstjoin-config.items");
        List<FirstItems> ListItems = new ArrayList<>();
        for(String items : config.getKeys(false)){
            FirstItems FirstItem = new FirstItems();
            FirstItem.MaterialName = config.getString(items + ".material");
            FirstItem.Quantity = config.getInt(items + ".quantity");
            FirstItem.DisplayName = config.getString(items + ".display-name");
            if(config.contains(items + ".equip")) {
                FirstItem.Equip = config.getBoolean(items + ".equip");
            }else{
                FirstItem.Equip = false;
            }
            FirstItem.Lore = config.getStringList(items + ".lore");
            FirstItem.Enchantments = config.getStringList(items + ".enchantments");
            ListItems.add(FirstItem);
        }
        return ListItems;
    }
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    public List<Ruleta> getRuletaConfigRuleta(){
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("ruleta-config");
        List<Ruleta> ListRuleta = new ArrayList<>();
        for(String ruleta : config.getKeys(false)){
            Ruleta Ruleta = new Ruleta();
            Ruleta.Ruleta = ruleta;
            Ruleta.AllPlayer = config.getBoolean(ruleta + ".all-player");
            Ruleta.Title = config.getString(ruleta + ".title");
            Ruleta.Subtitle = config.getString(ruleta + ".subtitle");
            ConfigurationSection rewardsSection = config.getConfigurationSection(ruleta + ".rewards");
            for(String rewards : rewardsSection.getKeys(false)){
                RuletaRewards RuletaRewards = new RuletaRewards();
                RuletaRewards.Symbol = rewardsSection.getString(rewards + ".symbol");
                RuletaRewards.Title = rewardsSection.getString(rewards + ".title").replace("{symbol}",RuletaRewards.Symbol);
                RuletaRewards.Subtitle = rewardsSection.getString(rewards + ".subtitle").replace("{symbol}",RuletaRewards.Symbol);
                RuletaRewards.Commands = rewardsSection.getStringList(rewards + ".commands");
                Ruleta.Rewards.add(RuletaRewards);
            }
            ListRuleta.add(Ruleta);
        }
        return ListRuleta;
    }
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    public List<MythicMob> getMythicmobConfigMobs(){
        ConfigurationSection config = ConfigurationSection.getConfigurationSection("mythicmob-config");
        List<MythicMob> listMythicMob = new ArrayList<>();
        for(String mob : config.getKeys(false)){
            MythicMob Mythicmob = new MythicMob();
            Mythicmob.Name = config.getString(mob + ".name");
            Mythicmob.Glowing = config.getBoolean(mob + ".glowing");
            Mythicmob.GlowColor = config.getString(mob + ".glow-color");
            Mythicmob.Coin = config.getBoolean(mob + ".coins.enable");
            Mythicmob.GlowCoinColor = config.getString(mob + ".coins.glow-color");
            Mythicmob.Stack = config.getInt(mob + ".coins.stack");
            Mythicmob.MinMoney = config.getInt(mob + ".coins.min-money");
            Mythicmob.MaxMoney = config.getInt(mob + ".coins.max-money");
            Mythicmob.OnCommandEnable = config.getBoolean(mob + ".command-spawn.enable");
            Mythicmob.OnCommandWorld = config.getString(mob + ".command-spawn.world");
            Mythicmob.OnCommandCords = config.getString(mob + ".command-spawn.cords");
            Mythicmob.OnRankedEnable = config.getBoolean(mob + ".ranked-damage.enable");

            ConfigurationSection rankedRewardsSection = config.getConfigurationSection(mob + ".ranked-damage.rewards-ranked");
            for (String rank : rankedRewardsSection.getKeys(false)) {
                Map<String, List<String>> reward = new HashMap<>();
                reward.put(rank, rankedRewardsSection.getStringList(rank + ".commands"));
                Mythicmob.OnRankedRewards.add(reward);
            }

            Mythicmob.OnDefaultRewards = config.getStringList(mob + ".ranked-damage.rewards-default");
            Mythicmob.OnRankedTopMessage = config.getString(mob + ".ranked-damage.top-message");

            listMythicMob.add(Mythicmob);
        }
        return listMythicMob;
    }
}
