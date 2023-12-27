package net.cherrycraft.cherrycore.tablist;

import me.clip.placeholderapi.PlaceholderAPI;
import net.cherrycraft.cherrycore.CherryCore;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tablist implements Listener {

    static MiniMessage miniMessage = MiniMessage.builder().build();
    static LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

    private final Map<UUID, ArmorStand> armorStands = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String header = serializer.serialize(miniMessage.deserialize("<#C81F3B><bold>Cherry<#31AF14><bold>Craft"));
        String footer = serializer.serialize(miniMessage.deserialize("<#31AF14>play.cherrycraft.net"));

        player.setPlayerListHeader(header);
        player.setPlayerListFooter(footer);

        // Get or create a team for the player
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(player.getName());
        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }

        // Add the player to the team
        team.addEntry(player.getName());

        // Schedule a repeating task that updates the team's prefix and suffix every 5 seconds
        Team finalTeam = team;
        Bukkit.getScheduler().runTaskTimerAsynchronously(CherryCore.getInstance(), () -> {
            String prefix = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix% ");
            String suffix = PlaceholderAPI.setPlaceholders(player, " %luckperms_suffix%");
            finalTeam.setPrefix(prefix);
            finalTeam.setSuffix(suffix);
        }, 0L, 100L); // 100 ticks = 5 seconds
    }
}
