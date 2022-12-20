package com.lapin.web3;

import org.apache.log4j.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ConnectToDB implements Serializable {

    @Resource(name="jdbc/lab3")
    private DataSource dataSource;

    private Connection connection;

    private static final Logger logger = Logger.getLogger(ConnectToDB.class);

    @PostConstruct
    public void init() {
        try {
            connection = dataSource.getConnection();
            // create the "entities" table if it doesn't exist
            String createTableSql = "CREATE TABLE IF NOT EXISTS entities " +
                    "(id SERIAL PRIMARY KEY, x DOUBLE PRECISION, y DOUBLE PRECISION, r DOUBLE PRECISION, hitResult VARCHAR(255))";
            PreparedStatement createTableStmt = connection.prepareStatement(createTableSql);
            createTableStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void insertIntoTable(Entry entry) {
        try {
            String insertSql = "INSERT INTO entities (id, x, y, r, hitResult) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setDouble(1, entry.getId());
            insertStmt.setDouble(2, entry.getX());
            insertStmt.setDouble(3, entry.getY());
            insertStmt.setDouble(4, entry.getR());
            insertStmt.setString(5, entry.getHitResult());
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

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
