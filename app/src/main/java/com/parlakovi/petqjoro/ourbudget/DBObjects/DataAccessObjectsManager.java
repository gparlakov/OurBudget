package com.parlakovi.petqjoro.ourbudget.DBObjects;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Calendar;


/**
 * Created by gparl_000 on 6/28/2015.
 */
public class DataAccessObjectsManager {
    private static Dao<Expense, Integer> daoExpense;
    private static Dao<User, Integer> daoUser;
    private static Dao<ExpenseType, Integer> daoExpenseType;
    private static Dao<ExpenseEdit, Integer> daoExpenseEdit;


    public static Dao<User, Integer> getDaoUser(ConnectionSource connectionSurce){
        if (daoUser == null){
            try {
                daoUser = DaoManager.createDao(connectionSurce, User.class);

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return daoUser;
    }

    public Dao<Expense, Integer> getDaoExpense(ConnectionSource connectionSurce){
        if (daoExpense == null){
            try {
                daoExpense = DaoManager.createDao(connectionSurce, Expense.class);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return daoExpense;
    }

    public Dao<ExpenseType, Integer> getDaoExpenseType(ConnectionSource connectionSurce){
        if (daoExpenseType == null){
            try {
                daoExpenseType = DaoManager.createDao(connectionSurce, ExpenseType.class);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return daoExpenseType;
    }

    public Dao<ExpenseEdit, Integer> getDaoExpenseEdit(ConnectionSource connectionSurce){
        if (daoExpenseEdit == null){
            try {
                daoExpenseEdit = DaoManager.createDao(connectionSurce, ExpenseEdit.class);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return daoExpenseEdit;
    }
}
