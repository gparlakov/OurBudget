package com.parlakovi.petqjoro.ourbudget.DBObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.IArrayAdapterItem;

import java.io.Serializable;

/**
 * Created by gparl_000 on 7/12/2015.
 */
@DatabaseTable(tableName = "expenseSubType")
public class ExpenseSubType implements Serializable, IArrayAdapterItem{

    @DatabaseField(generatedId = true)
    public int Id;

    @DatabaseField()
    public String Description;

    @DatabaseField(foreign = true, canBeNull = true)
    public ExpenseType ForExpenseType;

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public String getRowText() {
        return Description;
    }
}
