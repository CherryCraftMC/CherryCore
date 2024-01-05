package net.cherrycraft.cherrycore.tablist;

import me.clip.placeholderapi.PlaceholderAPI;
import net.cherrycraft.cherrycore.CherryCore;
import net.cherrycraft.cherrycore.chatsystem.utils.RankUtil;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Tablist implements Listener {

    static MiniMessage miniMessage = MiniMessage.builder().build();
    static LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();
    private final LuckPerms luckPerms = LuckPermsProvider.get();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String header = serializer.serialize(miniMessage.deserialize("<#C81F3B><bold>Cherry<#31AF14><bold>Craft"));
        String footer = serializer.serialize(miniMessage.deserialize("<#31AF14>play.cherrycraft.net"));

        player.setPlayerListHeader(header);
        player.setPlayerListFooter(footer);

        // Schedule a repeating task that updates the tablist every second
        Bukkit.getScheduler().runTaskTimerAsynchronously(CherryCore.getInstance(), () -> {
            // Get a list of all online players
            List<Player> players = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());

            // Sort the players based on their ranks
            players.sort(Comparator.comparingInt(player1 -> {
                String primaryGroup = luckPerms.getUserManager().getUser(player1.getUniqueId()).getPrimaryGroup();
                RankUtil rankColor = RankUtil.valueOf(primaryGroup.toUpperCase());
                return rankColor.getPriority();
            }));

            // Update the tablist
            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                String primaryGroup = luckPerms.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup();
                Team team = scoreboard.getTeam(primaryGroup);
                if (team == null) {
                    team = scoreboard.registerNewTeam(primaryGroup);
                }

                // Remove the player from their current team, if any
                for (Team existingTeam : scoreboard.getTeams()) {
                    if (existingTeam.hasEntry(p.getName())) {
                        existingTeam.removeEntry(p.getName());
                    }
                }

                // Add the player to the team
                team.addEntry(p.getName());

                // Update the team's prefix and suffix
                String prefix = PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix% ");
                String suffix = PlaceholderAPI.setPlaceholders(p, " %luckperms_suffix%");
                team.setPrefix(prefix);
                team.setSuffix(suffix);

                // Update the team's display name
                RankUtil rankColor = RankUtil.fromGroupName(primaryGroup);
                team.setDisplayName(rankColor.getColor());
            }
        }, 0L, 20L); // 20 ticks = 1 second
    }
}