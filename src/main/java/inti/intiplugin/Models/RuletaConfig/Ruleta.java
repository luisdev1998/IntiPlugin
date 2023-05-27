package inti.intiplugin.Models.RuletaConfig;

import java.util.ArrayList;
import java.util.List;

public class Ruleta {
    public Ruleta(){
        this.Rewards = new ArrayList<>();
    }
    public String Ruleta;
    public Boolean AllPlayer;
    public String Title;
    public String Subtitle;
    public List<RuletaRewards> Rewards;
}
