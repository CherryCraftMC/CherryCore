package net.cherrycraft.cherrycore.database;

import java.sql.*;

public class MySQL {

    static Connection connection;
    private static String host;
    private static String database;
    private static String user;
    private static String password;
    private static int port;

    public static void Login(String host, String database, String user, String password, int port) {
        MySQL.host = host;
        MySQL.database = database;
        MySQL.user = user;
        MySQL.password = password;
        MySQL.port = port;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection GetConnection() {
        if (connection != null) {
            Login(host, database, user, password, port);
        }
        return connection;
    }

    public static void createTables() {
        Connection connection = GetConnection();

        String query1 = "CREATE TABLE IF NOT EXISTS `ReefRealm_Languages` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `uuid` VARCHAR(36) NOT NULL,\n" +
                "  `language` VARCHAR(36) NULL DEFAULT 'en_US',\n" +
                "  PRIMARY KEY (`id`));";

        String query2 = "CREATE TABLE IF NOT EXISTS `Chat_Logger` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `uuid` VARCHAR(36) NOT NULL,\n" +
                "  `ServerName` VARCHAR(50) NOT NULL,\n" +
                "  `Time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `Message` VARCHAR(255) NOT NULL,\n" +
                "  PRIMARY KEY (`id`));";

        String query3 = "CREATE TABLE IF NOT EXISTS `Chat_Filter` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `uuid` VARCHAR(36) NOT NULL UNIQUE,\n" +
                "  `chatFilter` INT DEFAULT 0,\n" +
                "   PRIMARY KEY (`id`))";


        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            // executing the queries
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query2);
            stmt.executeUpdate(query3);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean userExists(String playerUUID) {
        Connection connection = GetConnection();
        String query = "SELECT * FROM ReefRealm_Languages WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerUUID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createUser(String playerUUID) {
        Connection connection = GetConnection();
        String query = "INSERT INTO ReefRealm_Languages (uuid) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerUUID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getLanguage(String playerUUID) {
        Connection connection = GetConnection();
        String query = "SELECT language FROM ReefRealm_Languages WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerUUID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String language = resultSet.getString("language");
                System.out.println(language);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
