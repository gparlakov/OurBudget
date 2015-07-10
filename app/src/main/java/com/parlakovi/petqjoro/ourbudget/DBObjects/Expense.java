package com.parlakovi.petqjoro.ourbudget.DBObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.IArrayAdapterItem;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gparl_000 on 6/28/2015.
 */
@DatabaseTable(tableName = "expense")
public class Expense implements IArrayAdapterItem, Serializable {
    public Expense() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, canBeNull = false)
    private User payer;

    @DatabaseField(canBeNull = false)
    private Date createTimeStamp;

    @DatabaseField(canBeNull = false)
    private double amount;

    @DatabaseField(canBeNull = false)
    private Date syncTimeStamp;

    @DatabaseField(canBeNull = false, defaultValue = "false")
    private boolean hasEdits;

    public int getId() {
        return id;
    }

    @Override
    public String getRowText() {
        StringBuilder builder = new StringBuilder();
        builder.append("Payed by ").append(getPayer())
                .append(" on ").append(getCreateTimeStamp())
                .append(" sum ").append(getAmount());

        return  builder.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public Date getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Date createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getSyncTimeStamp() {
        return syncTimeStamp;
    }

    public void setSyncTimeStamp(Date syncTimeStamp) {
        this.syncTimeStamp = syncTimeStamp;
    }

    public boolean isHasEdits() {
        return hasEdits;
    }

    public void setHasEdits(boolean hasEdits) {
        this.hasEdits = hasEdits;
    }
}
