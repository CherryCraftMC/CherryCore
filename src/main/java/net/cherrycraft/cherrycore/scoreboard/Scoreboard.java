package net.cherrycraft.cherrycore.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import net.cherrycraft.cherrycore.CherryCore;
import net.cherrycraft.cherrycore.utils.scoreboard.adventure.ScoreboardSystem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Scoreboard implements Listener {

    private final Map<UUID, ScoreboardSystem> boards = new HashMap<>();
    MiniMessage miniMessage = MiniMessage.builder().build();
    LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

    private String serverType;

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ScoreboardSystem board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String serverType = CherryCore.getInstance().getConfig().getString("server-type");

        if (serverType == null) {
            serverType = CherryCore.getInstance().getConfig().getString("server-type");

            if (serverType == null) {
                Bukkit.getLogger().severe("Server type is not set in the config file.");
                return;
            }
        }

        switch (serverType) {
            case "skypvp":
                setupSkyPVPScoreboard(player);
                System.out.println("SkyPVP scoreboard set up for " + player.getName());
                break;
            case "lobby":
                setupLobbyScoreboard(player);
                System.out.println("Lobby scoreboard set up for " + player.getName());
                break;
            default:
                Bukkit.getLogger().severe("Unknown server type: " + serverType);
                break;
        }
    }


    private void setupSkyPVPScoreboard(Player player) {
        ScoreboardSystem scoreboard = new ScoreboardSystem(player);
        scoreboard.updateTitle(miniMessage.deserialize("<#C81F3B><bold>Cherry<#31AF14><bold>Craft"));
        updateBoardSkyPVP(scoreboard);
        Bukkit.getScheduler().runTaskTimerAsynchronously(CherryCore.getInstance(), () -> {
            for (ScoreboardSystem board : this.boards.values()) {
                updateBoardSkyPVP(board);
            }
        }, 0L, 20L);

        this.boards.put(player.getUniqueId(), scoreboard);
    }

    private void setupLobbyScoreboard(Player player) {
        ScoreboardSystem scoreboard = new ScoreboardSystem(player);
        scoreboard.updateTitle(miniMessage.deserialize("<#C81F3B><bold>Cherry<#31AF14><bold>Craft"));
        updateBoardLobby(scoreboard);
        Bukkit.getScheduler().runTaskTimerAsynchronously(CherryCore.getInstance(), () -> {
            for (ScoreboardSystem board : this.boards.values()) {
                updateBoardLobby(board);
            }
        }, 0L, 20L);
        this.boards.put(player.getUniqueId(), scoreboard);
    }

    private void updateBoardSkyPVP(ScoreboardSystem board) {
        board.updateLines(

                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>Level <#31AF14>%reefrealmskypvp_level%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>Progress: <#31AF14>%reefrealmskypvp_required_score%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<gray>[%reefrealmskypvp_progress%<gray>]")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>Coins: <#31AF14>%reefrealmskypvp_coins%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>K/D: <#31AF14>%reefrealmskypvp_kd%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>Kills: <#31AF14>%reefrealmskypvp_kills%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>Deaths: <#31AF14>%reefrealmskypvp_deaths%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>Killstreak: <#31AF14>%reefrealmskypvp_killstreak%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<#C81F3B>cherrycraft.net"))
        );
    }

    private void updateBoardLobby(ScoreboardSystem board) {
        board.updateLines(

                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<white>Rank %luckperms_prefix%")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "")),
                miniMessage.deserialize(PlaceholderAPI.setPlaceholders(board.getPlayer(), "<#C81F3B>cherrycraft.net"))
        );
    }

}
