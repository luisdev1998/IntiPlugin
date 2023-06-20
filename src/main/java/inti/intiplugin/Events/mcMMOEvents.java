package inti.intiplugin.Events;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import inti.intiplugin.Aditionals.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class mcMMOEvents implements Listener {
    private final ConfigManager ConfigManager;

    public mcMMOEvents(ConfigManager ConfigManager){
        this.ConfigManager = ConfigManager;
    }

    @EventHandler
    private void onLevelUp(McMMOPlayerLevelUpEvent event){
        if(ConfigManager.getmcMMOConfig().mcMMOLevelupEnable){
            Player player = event.getPlayer().getPlayer();
            String skill = event.getSkill().getName();
            String level = String.valueOf(event.getSkillLevel()-1);
            String newlevel = String.valueOf(event.getSkillLevel());
            player.sendTitle(
                    ConfigManager.getmcMMOConfig().mcMMOLevelupTitle.replace("{skill}",skill).replace("{level}",level).replace("{newlevel}",newlevel),
                    ConfigManager.getmcMMOConfig().mcMMOLevelupSubTitle.replace("{skill}",skill).replace("{level}",level).replace("{newlevel}",newlevel)
            );
            player.playSound(
                    player.getLocation(),
                    ConfigManager.getmcMMOConfig().mcMMOLevelupSound,
                    ConfigManager.getmcMMOConfig().mcMMOLevelupSoundVolume.floatValue(),
                    ConfigManager.getmcMMOConfig().mcMMOLevelupSoundPitch.floatValue()
            );
        }
    }
}
