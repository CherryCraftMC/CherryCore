package net.cherrycraft.cherrycore.chatsystem;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class ChatFilter implements Listener {
    @EventHandler
    public void onChatEvent(AsyncChatEvent event) throws SQLException {
        Player player = event.getPlayer();
        String uuid = event.getPlayer().getUniqueId().toString();

    }
}
