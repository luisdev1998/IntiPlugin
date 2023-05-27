package inti.intiplugin.Models.RankbenefitConfig;

import java.util.ArrayList;
import java.util.List;

public class Rankbenefit {
    public String Rank;
    public Boolean OnJoinEnable;
    public String OnJoinSound;
    public List<String> OnJoinBroadcaastMessage;
    public Boolean OnHitEnable;
    public String OnHitSound;
    public Integer OnHitInterval;
    public Boolean OnDeathEnable;
    public String OnDeathSound;
    public Rankbenefit(){
        this.OnJoinBroadcaastMessage = new ArrayList<>();
    }
}
