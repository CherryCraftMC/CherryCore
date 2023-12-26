package net.cherrycraft.cherrycore.languageSystem.data;

import net.cherrycraft.cherrycore.database.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private String lang;

    public PlayerData(UUID PlayerUUID, String LanguageName) {
        this.uuid = PlayerUUID;
        this.lang = LanguageName;
    }

    // Get
    public UUID getPlayerUUID() {
        return uuid;
    }

    public String getSelectedLanguage() {
        return lang;
    }

    // Set
    public void setSelectedLanguage(String LanguageName) {
        this.lang = LanguageName;
        String query = "UPDATE ReefRealm_Languages SET language = ? WHERE uuid = ?";
        try (Connection connection = MySQL.GetConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, LanguageName);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
