package net.cherrycraft.cherrycore.chatsystem;

import me.clip.placeholderapi.PlaceholderAPI;
import net.cherrycraft.cherrycore.CherryCore;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatSystem implements Listener {

    MiniMessage miniMessage = MiniMessage.builder().build();
    LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPlaceholderChat(AsyncPlayerChatEvent event) {
        String format = serializer.serialize(miniMessage.deserialize(PlaceholderAPI.setPlaceholders(event.getPlayer(), CherryCore.getInstance().getConfig().getString("format", "%luckperms_prefix%{display_name} %luckperms_suffix% &#1883C4» &#7D878C{message}").replace("{name}", event.getPlayer().getName()).replace("{display_name}", event.getPlayer().getDisplayName()))));
        event.setFormat(format.replace("{message}", event.getMessage().replace("%", "‰")));
    }
}
