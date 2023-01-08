package com.lapin.web3;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectToDB {

    private Connection connection;

    private static final Logger logger = Logger.getLogger(ConnectToDB.class);

    /**
     * Initializes the connection to the database and creates the "entities" table if it doesn't exist.
     */
    @PostConstruct
    public void init() {
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/PostgresDS");
            connection = dataSource.getConnection();
            // create the "entities" table if it doesn't exist
            String createTableSql = "CREATE TABLE IF NOT EXISTS entities " +
                    "(id SERIAL PRIMARY KEY, x DOUBLE PRECISION, y DOUBLE PRECISION, r DOUBLE PRECISION, hitResult VARCHAR(255))";
            PreparedStatement createTableStmt = connection.prepareStatement(createTableSql);
            createTableStmt.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new entry into the "entities" table.
     *
     * @param entry the entry to insert
     */
    public void insertIntoTable(Entry entry) {
        try {
            String insertSql = "INSERT INTO entities (x, y, r, hitResult) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setDouble(1, entry.getX());
            insertStmt.setDouble(2, entry.getY());
            insertStmt.setDouble(3, entry.getR());
            insertStmt.setString(4, entry.getHitResult());
            insertStmt.executeUpdate();
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
        try {
            String selectSql = "SELECT * FROM entities";
            PreparedStatement selectStmt = connection.prepareStatement(selectSql);
            ResultSet resultSet = selectStmt.executeQuery();
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
