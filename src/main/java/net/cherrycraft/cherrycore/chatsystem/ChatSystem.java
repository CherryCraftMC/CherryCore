package net.cherrycraft.cherrycore.chatsystem;

import me.clip.placeholderapi.PlaceholderAPI;
import net.cherrycraft.cherrycore.CherryCore;
import net.cherrycraft.cherrycore.chatsystem.utils.RankUtil;
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
                roleColor = RankUtil.OWNER.getColor();
                break;
            case "manager":
                roleColor = RankUtil.MANAGER.getColor();
                break;
            case "developer":
                roleColor = RankUtil.DEVELOPER.getColor();
                break;
            case "game-master":
                roleColor = RankUtil.GAME_MASTER.getColor();
                break;
            case "admin":
                roleColor = RankUtil.ADMIN.getColor();
                break;
            case "moderator":
                roleColor = RankUtil.MODERATOR.getColor();
                break;
            case "trainee":
                roleColor = RankUtil.TRAINEE.getColor();
                break;
            case "designer":
                roleColor = RankUtil.DESIGNER.getColor();
                break;
            case "builder":
                roleColor = RankUtil.BUILDER.getColor();
                break;
            case "youtuber":
                roleColor = RankUtil.YOUTUBE.getColor();
                break;
            case "cherry-platinum-lifetime":
                roleColor = RankUtil.CHERRY_PLATINUM_LIFETIME.getColor();
                break;
            case "cherry-platinum":
                roleColor = RankUtil.CHERRY_PLATINUM.getColor();
                break;
            case "cherry-gold-lifetime":
                roleColor = RankUtil.CHERRY_GOLD_LIFETIME.getColor();
                break;
            case "cherry-gold":
                roleColor = RankUtil.CHERRY_GOLD.getColor();
                break;
            case "cherry-lifetime":
                roleColor = RankUtil.CHERRY_LIFETIME.getColor();
                break;
            case "cherry":
                roleColor = RankUtil.CHERRY.getColor();
                break;
            default:
                roleColor = RankUtil.DEFAULT.getColor();
                break;
        }
        event.setFormat(serializer.serialize(miniMessage.deserialize(PlaceholderAPI.setPlaceholders(event.getPlayer(), CherryCore.getInstance().getConfig().getString("format", "%luckperms_prefix%{display_name} %luckperms_suffix% &#1883C4» &#7D878C{message}").replace("{name}", event.getPlayer().getName()).replace("{display_name}", event.getPlayer().getDisplayName())))).replace("{message}", event.getMessage().replace("%", "‰")).replace("{playername}", serializer.serialize(miniMessage.deserialize(roleColor + player.getName()))));
    }


}
