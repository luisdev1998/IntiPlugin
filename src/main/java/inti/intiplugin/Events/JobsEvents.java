package inti.intiplugin.Events;

import com.gamingmesh.jobs.api.JobsJoinEvent;
import com.gamingmesh.jobs.api.JobsLeaveEvent;
import com.gamingmesh.jobs.api.JobsLevelUpEvent;
import inti.intiplugin.Aditionals.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobsEvents implements Listener {
    private final ConfigManager ConfigManager;
    public JobsEvents(ConfigManager ConfigManager){
        this.ConfigManager = ConfigManager;
    }

    @EventHandler
    private void onJobsJoin(JobsJoinEvent event) {
        if(ConfigManager.getJobsConfig().JobJoinEnable){
            Player player = event.getPlayer().getPlayer();
            player.sendTitle(
                    ConfigManager.getJobsConfig().JobJoinTitle.replace("{job}",event.getJob().getName()),
                    ConfigManager.getJobsConfig().JobJoinSubTitle.replace("{job}",event.getJob().getName())
            );
            player.playSound(
                    player.getLocation(),
                    ConfigManager.getJobsConfig().JobJoinSound,
                    ConfigManager.getJobsConfig().JobJoinSoundVolume.floatValue(),
                    ConfigManager.getJobsConfig().JobJoinSoundPitch.floatValue()
            );
        }
    }

    @EventHandler
    private void onJobsLevelUp(JobsLevelUpEvent event) {
        if(ConfigManager.getJobsConfig().JobLevelupEnable){
            Player player = event.getPlayer().getPlayer();
            String job = event.getJobName();
            String level = String.valueOf(event.getLevel()-1);
            String newlevel = String.valueOf(event.getLevel());
            player.sendTitle(
                    ConfigManager.getJobsConfig().JobLevelupTitle.replace("{job}",job).replace("{level}",level).replace("{newlevel}",newlevel),
                    ConfigManager.getJobsConfig().JobLevelupSubTitle.replace("{job}",job).replace("{level}",level).replace("{newlevel}",newlevel)
            );
            player.playSound(
                    player.getLocation(),
                    ConfigManager.getJobsConfig().JobLevelupSound,
                    ConfigManager.getJobsConfig().JobLevelupSoundVolume.floatValue(),
                    ConfigManager.getJobsConfig().JobLevelupSoundPitch.floatValue()
            );
        }
    }

    @EventHandler
    private void onJobsLeave(JobsLeaveEvent event) {
        if(ConfigManager.getJobsConfig().JobLeftEnable){
            Player player = event.getPlayer().getPlayer();
            player.sendTitle(
                    ConfigManager.getJobsConfig().JobLeftTitle.replace("{job}",event.getJob().getName()),
                    ConfigManager.getJobsConfig().JobLeftSubTitle.replace("{job}",event.getJob().getName())
            );
            player.playSound(
                    player.getLocation(),
                    ConfigManager.getJobsConfig().JobLeftSound,
                    ConfigManager.getJobsConfig().JobLeftSoundVolume.floatValue(),
                    ConfigManager.getJobsConfig().JobLeftSoundPitch.floatValue()
            );
        }
    }
}
