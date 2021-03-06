package com.parlakovi.petqjoro.ourbudget.DBObjects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.IArrayAdapterItem;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gparl_000 on 6/28/2015.
 */
@DatabaseTable(tableName = "expenseType")
public class ExpenseType implements IArrayAdapterItem, Serializable {
    public ExpenseType(){}

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, canBeNull = true)
    private User expenseTargetUser;

    @DatabaseField(canBeNull = false)
    private String description;

    @DatabaseField(canBeNull = false)
    private Date createTimeStamp;

    @DatabaseField(canBeNull = true)
    private Date syncTimeStamp;

/*    @DatabaseField(dataType = DataType.BOOLEAN)
    public boolean HasSubtype;*/

    public int getId() {
        return id;
    }

    @Override
    public String getRowText() {
        return  getDescription();
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getExpenseTargetUser() {
        return expenseTargetUser;
    }

    public void setExpenseTargetUser(User expenseTargetUser) {
        this.expenseTargetUser = expenseTargetUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Date createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public Date getSyncTimeStamp() {
        return syncTimeStamp;
    }

    public void setSyncTimeStamp(Date syncTimeStamp) {
        this.syncTimeStamp = syncTimeStamp;
    }
}
