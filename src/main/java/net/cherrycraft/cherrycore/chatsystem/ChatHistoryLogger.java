package net.cherrycraft.cherrycore.chatsystem;

import net.cherrycraft.cherrycore.CherryCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static net.cherrycraft.cherrycore.database.MySQL.GetConnection;

public class ChatHistoryLogger implements Listener {

    private static final int BATCH_SIZE = 25; // Adjust this based on your requirements
    private final List<String> chatHistory = new ArrayList<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws SQLException {
        String message = event.getMessage();
        String uuid = event.getPlayer().getUniqueId().toString();
        String serverName = CherryCore.getInstance().getConfig().getString("server-name");
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        chatHistory.add(uuid + "," + serverName + "," + message + "," + timestamp);
        if (chatHistory.size() >= BATCH_SIZE) {
            saveChatHistory();
            chatHistory.clear();
        }
    }

    private void saveChatHistory() throws SQLException {
        Connection connection = GetConnection();
        String insertQuery = "INSERT INTO Chat_Logger (uuid, ServerName, Message, Time) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(insertQuery);
            for (String record : chatHistory) {
                String[] parts = record.split(",", 4);
                pstmt.setString(1, parts[0]); // uuid
                pstmt.setString(2, parts[1]); // serverName
                pstmt.setString(3, parts[2]); // message
                pstmt.setString(4, parts[3]); // timestamp
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}