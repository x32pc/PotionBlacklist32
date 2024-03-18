package com.x32pc.github.manager;

import com.x32pc.github.PotionBlacklist32;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private final PotionBlacklist32 potionBlacklist32;

    public DatabaseManager(String dbName, PotionBlacklist32 main) {
        this.potionBlacklist32 = main;
        try {
            String dbPath = "jdbc:sqlite:" + dbName;
            connection = DriverManager.getConnection(dbPath);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void createTables() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS blacklist_potion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player_uuid VARCHAR(36) NOT NULL" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToBlacklist(String playerUUID) {

        String insertQuery = "INSERT INTO blacklist_potion (player_uuid) VALUES (?);";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, playerUUID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBlacklisted(String playerUUID) {
        String query = "SELECT COUNT(*) FROM blacklist_potion WHERE player_uuid = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, playerUUID);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void removeFromBlacklist(String playerUUID) {
        String query = "DELETE FROM blacklist_potion WHERE player_uuid = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, playerUUID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}