package com.lapin.web3;

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
    private ConnectToDB db;

    public Entries(){
        entry = new Entry();
        entries = new ArrayList<>();

        db = new ConnectToDB();
        db.init();
        entries = db.selectAllFromTable();
    }
    public void addEntry(){
        entries.add(entry);
        entries = db.selectAllFromTable();
        entry = new Entry();
    }
}
