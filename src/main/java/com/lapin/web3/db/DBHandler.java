package com.lapin.web3.db;


import com.lapin.web3.Entry;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {
    private static final Logger logger = Logger.getLogger(DBHandler.class);
    private DBConnector connector;

    public DBHandler(){
        connector = new DBConnector();
    }
    /**
     * Creates the "entities" table if it doesn't exist.
     */
    public void createTable(){
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQuery.CREATE_ENTRIES_TABLE.getQuery())) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * Inserts a new entry into the "entities" table.
     *
     * @param entry the entry to insert
     */
    public void insertIntoTable(Entry entry) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQuery.INSERT_ENTRY.getQuery())) {
            statement.setDouble(1, entry.getX());
            statement.setDouble(2, entry.getY());
            statement.setDouble(3, entry.getR());
            statement.setString(4, entry.getHitResult());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * Selects all entries from the "entities" table.
     *
     * @return a list of all entries in the table
     */
    public List<Entry> selectAllFromTable() {
        List<Entry> entries = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQuery.SELECT_ALL_ENTRIES.getQuery())) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Entry entry = new Entry();
                entry.setId(resultSet.getInt("id"));
                entry.setX(resultSet.getDouble("x"));
                entry.setY(resultSet.getDouble("y"));
                entry.setR(resultSet.getDouble("r"));
                entry.setHitResult(resultSet.getString("hitResult"));
                entries.add(entry);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return entries;
    }
}

