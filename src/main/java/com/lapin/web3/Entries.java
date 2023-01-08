package com.lapin.web3;

import com.lapin.web3.db.DBHandler;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@ManagedBean
@Getter
@Setter
public class Entries implements Serializable {

    private Entry entry;
    private List<Entry> entries;
    private DBHandler db;

    public Entries(){
        entry = new Entry();
        entries = new ArrayList<>();

        db = new DBHandler();
        entries = db.selectAllFromTable();
    }
    public void addEntry(){
        entries.add(entry);
        entries = db.selectAllFromTable();
        entry = new Entry();
    }
}
