package net.cherrycraft.cherrycore.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.cherrycraft.cherrycore.database.MySQL;
import net.cherrycraft.cherrycore.languageSystem.data.LanguageData;
import net.cherrycraft.cherrycore.languageSystem.data.PlayerData;
import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LanguageManager implements Listener {

    private static final Map<String, LanguageData> languages = new HashMap<>();
    private static final Map<UUID, PlayerData> players = new HashMap<>();

    public void loadAllLanguages() {
        if (!languages.entrySet().isEmpty()) {
            languages.clear();
        }
        String repositoryBaseUrl = "https://api.github.com/repos/CherryCraftMC/ServerLanguages/contents/languages";
        try {
            URL url = new URL(repositoryBaseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseBody = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
                Gson gson = new Gson();
                JsonArray filesArray = gson.fromJson(responseBody, JsonArray.class);
                for (JsonElement fileElement : filesArray) {
                    JsonObject fileObject = fileElement.getAsJsonObject();
                    String fileName = fileObject.get("name").getAsString();
                    String fileDownloadUrl = fileObject.get("download_url").getAsString();
                    try (InputStream inputStream = new URL(fileDownloadUrl).openStream()) {
                        StringBuilder textBuilder = new StringBuilder();
                        try (Reader reader = new BufferedReader(new InputStreamReader
                                (inputStream, StandardCharsets.UTF_8))) {
                            int c = 0;
                            while ((c = reader.read()) != -1) {
                                textBuilder.append((char) c);
                            }
                        }
                        Map<String, String> langmsg = new HashMap<>();
                        String[] fileSpit = textBuilder.toString().split("\n");
                        String[] lines = fileSpit;
                        for (String line : lines) {
                            if (line.contains("=")) {
                                String[] parts = line.split("=", 2);
                                String key = parts[0].trim();
                                String value = parts[1].trim();
                                langmsg.put(key, value);
                            }
                        }
                        languages.put(fileName.replace(".lang", ""), new LanguageData(fileName.replace(".lang", ""), langmsg));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllLanguages() {
        List<String> AllLanguages = new ArrayList<>();
        for (Map.Entry<String, LanguageData> lang : languages.entrySet()) {
            AllLanguages.add(lang.getValue().getLanguageName());
        }
        return AllLanguages;
    }

    public Map<String, LanguageData> getLanguagesData() {
        return languages;
    }

    public String getMessage(Player player, String message) {
        PlayerData pData = players.get(player.getUniqueId());
        LanguageData lData = languages.get(pData.getSelectedLanguage());
        String text = lData.getLanguageFile().get(message);
        if (lData == null) {
            return "Language not found";
        }
        return text;
    }

    public String getPlayersLanguage(Player player) {
        PlayerData pData = players.get(player.getUniqueId());
        return pData.getSelectedLanguage();
    }

    public void setPlayersLanguage(Player player, String LanguageName) {
        PlayerData pData = players.get(player.getUniqueId());
        pData.setSelectedLanguage(LanguageName);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!MySQL.userExists(event.getPlayer().getUniqueId().toString())) {
            // User doesn't exist, create a new entry
            MySQL.createUser(event.getPlayer().getUniqueId().toString());
        }
        if (!players.containsKey(event.getPlayer().getUniqueId())) {
            String language = null;
            Connection connection = MySQL.GetConnection();
            String query = "SELECT language FROM ReefRealm_Languages WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, event.getPlayer().getUniqueId().toString());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    language = resultSet.getString("language");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (language != null) {
                players.put(event.getPlayer().getUniqueId(), new PlayerData(event.getPlayer().getUniqueId(), language));
            } else {
                players.put(event.getPlayer().getUniqueId(), new PlayerData(event.getPlayer().getUniqueId(), null));
                setPlayersLanguage(event.getPlayer(), "en_US");
            }
        }
    }
}
