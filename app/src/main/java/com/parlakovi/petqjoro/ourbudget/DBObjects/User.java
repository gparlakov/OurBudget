package com.parlakovi.petqjoro.ourbudget.DBObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.IArrayAdapterItem;

import java.util.Date;

/**
 * Created by gparl_000 on 6/28/2015.
 */
@DatabaseTable(tableName = "user")
public class User implements IArrayAdapterItem {

    @Override
    public String getRowText() {
        return this.getName();
    }

    public User() {
    }

    @DatabaseField(generatedId = true)
    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    @DatabaseField()
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @DatabaseField(canBeNull = false)
    private Date createTimeStamp;
    public Date getCreateTimeStamp() {
        return createTimeStamp;
    }
    public void setCreateTimeStamp(Date createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    @DatabaseField(canBeNull = false)
    private Date syncTimeStamp;
    public Date getSyncTimeStamp() {
        return syncTimeStamp;
    }
    public void setSyncTimeStamp(Date syncTimeStamp) {
        this.syncTimeStamp = syncTimeStamp;
    }
}
