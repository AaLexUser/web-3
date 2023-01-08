package com.lapin.web3.db;

public enum DBQuery {
    CREATE_ENTRIES_TABLE("CREATE TABLE IF NOT EXISTS entries" +
            " (id SERIAL PRIMARY KEY, x DOUBLE PRECISION, y DOUBLE PRECISION, r DOUBLE PRECISION," +
            " hit_result VARCHAR(10))"),
    INSERT_ENTRY("INSERT INTO entries (x, y, r, hit_result) VALUES (?, ?, ?, ?)"),
    SELECT_ALL_ENTRIES("SELECT * FROM entries");
    private final String query;

    DBQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
