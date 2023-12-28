package net.cherrycraft.cherrycore.chatsystem;

import me.clip.placeholderapi.PlaceholderAPI;
import net.cherrycraft.cherrycore.CherryCore;
import net.cherrycraft.cherrycore.chatsystem.utils.RankColors;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatSystem implements Listener {

    MiniMessage miniMessage = MiniMessage.builder().build();
    LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPlaceholderChat(AsyncPlayerChatEvent event) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        Player player = event.getPlayer();
        String primaryGroup = luckPerms.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        String roleColor = "";
        switch (primaryGroup.toLowerCase()) {
            case "owner":
                roleColor = RankColors.OWNER.getColor();
                break;
            case "manager":
                roleColor = RankColors.MANAGER.getColor();
                break;
            case "developer":
                roleColor = RankColors.DEVELOPER.getColor();
                break;
            case "game-master":
                roleColor = RankColors.GAME_MASTER.getColor();
                break;
            case "admin":
                roleColor = RankColors.ADMIN.getColor();
                break;
            case "moderator":
                roleColor = RankColors.MODERATOR.getColor();
                break;
            case "trainee":
                roleColor = RankColors.TRAINEE.getColor();
                break;
            case "designer":
                roleColor = RankColors.DESIGNER.getColor();
                break;
            case "builder":
                roleColor = RankColors.BUILDER.getColor();
                break;
            case "youtuber":
                roleColor = RankColors.YOUTUBE.getColor();
                break;
            case "cherry-platinum-lifetime":
                roleColor = RankColors.CHERRY_PLATINUM_LIFETIME.getColor();
                break;
            case "cherry-platinum":
                roleColor = RankColors.CHERRY_PLATINUM.getColor();
                break;
            case "cherry-gold-lifetime":
                roleColor = RankColors.CHERRY_GOLD_LIFETIME.getColor();
                break;
            case "cherry-gold":
                roleColor = RankColors.CHERRY_GOLD.getColor();
                break;
            case "cherry-lifetime":
                roleColor = RankColors.CHERRY_LIFETIME.getColor();
                break;
            case "cherry":
                roleColor = RankColors.CHERRY.getColor();
                break;
            default:
                roleColor = RankColors.DEFAULT.getColor();
                break;
        }
        event.setFormat(serializer.serialize(miniMessage.deserialize(PlaceholderAPI.setPlaceholders(event.getPlayer(), CherryCore.getInstance().getConfig().getString("format", "%luckperms_prefix%{display_name} %luckperms_suffix% &#1883C4» &#7D878C{message}").replace("{name}", event.getPlayer().getName()).replace("{display_name}", event.getPlayer().getDisplayName())))).replace("{message}", event.getMessage().replace("%", "‰")).replace("{playername}", serializer.serialize(miniMessage.deserialize(roleColor + player.getName()))));
    }


}
