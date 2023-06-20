package inti.intiplugin.Models.RankbenefitConfig;

import java.util.ArrayList;
import java.util.List;

public class Rank {
    public Rank(){
        this.Rankbenefit = new ArrayList<>();
    }
    public Boolean Enable;
    public List<Rankbenefit> Rankbenefit;
}
