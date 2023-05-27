package inti.intiplugin.Models.MythicConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MythicMob {
    public MythicMob (){
        this.OnRankedRewards = new ArrayList<>();
    }
    public String Name;
    public Boolean Glowing;
    public String GlowColor;
    public Boolean Coin;
    public String GlowCoinColor;
    public Integer Stack;
    public Integer MinMoney;
    public Integer MaxMoney;
    public MythicMobCoin MythicMobCoin;
    public Boolean OnCommandEnable;
    public String OnCommandWorld;
    public String OnCommandCords;
    public Boolean OnRankedEnable;
    public List<Map<String, List<String>>> OnRankedRewards;
    public List<String> OnDefaultRewards;
    public String OnRankedTopMessage;
}