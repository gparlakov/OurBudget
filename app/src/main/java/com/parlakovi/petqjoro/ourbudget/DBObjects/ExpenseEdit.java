package com.parlakovi.petqjoro.ourbudget.DBObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gparl_000 on 6/28/2015.
 */
@DatabaseTable
public class ExpenseEdit {
    public ExpenseEdit() {}

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, canBeNull = false)
    private ExpenseType type;

    @DatabaseField(foreign = true, canBeNull = false)
    private Expense expense;

    @DatabaseField(canBeNull = false)
    private double amountToAdd;
}
