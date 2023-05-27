package inti.intiplugin.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Objects;

public class GlowLogic {
    private static Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
    private static ArrayList<Team> teams = new ArrayList<>();

    public static void GlowEntity(String color, Entity entity) {
        Team team = scoreboard.getTeam(color.toString());
        if (team == null) {
            team = scoreboard.registerNewTeam(color.toString());
            teams.add(team);
        }
        team.setColor(ChatColor.valueOf(color));
        team.addEntry(entity.getUniqueId().toString());
    }
}
