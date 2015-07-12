package com.parlakovi.petqjoro.ourbudget.DBObjects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.parlakovi.petqjoro.ourbudget.Global;
import com.parlakovi.petqjoro.ourbudget.services.Users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by gparl_000 on 6/28/2015.
 */
public class DataBaseManager extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ourBudgetDatabase.db";

    private static final int DATABASE_VERSION = 1;

    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource){
        try {
            TableUtils.createTableIfNotExists(connectionSource, User.class);

            TableUtils.createTableIfNotExists(connectionSource, Expense.class);

            TableUtils.createTableIfNotExists(connectionSource, ExpenseType.class);

            TableUtils.createTableIfNotExists(connectionSource, ExpenseEdit.class);

            TableUtils.createTableIfNotExists(connectionSource, ExpenseSubType.class);

            Collection<User> users = setInitialDataForUsers();

            setInitialExpenseTypes(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    private Collection<User> setInitialDataForUsers() throws SQLException{

        Users usersMgr = new Users();
        Dao<User, Integer> daoUser = Global.DBHelper.getDao(User.class);
        Date now = Calendar.getInstance().getTime();

        User user_petya = new User();
        user_petya.setName("Petq");
        user_petya.setCreateTimeStamp(now);
        user_petya.setSyncTimeStamp(now);

        User user_joro = new User();
        user_joro.setName("Joro");
        user_joro.setCreateTimeStamp(now);
        user_joro.setSyncTimeStamp(now);

        User user_famili = new User();
        user_petya.setName("Семейство Парлъкови");
        user_petya.setCreateTimeStamp(now);
        user_petya.setSyncTimeStamp(now);


        Collection<User> allUsers = daoUser.queryForAll();
        if (!allUsers.contains(user_joro) ) {

            daoUser.create(user_famili);
            daoUser.create(user_petya);
            daoUser.create(user_joro);
        }

        ArrayList<User> users = new ArrayList<>();
        users.add(user_petya);
        users.add(user_joro);
        users.add(user_famili);

        return users;
    }

    private void setInitialExpenseTypes(Collection<User> family) throws SQLException{
        Dao<ExpenseType, Integer> daoExpenseType = Global.DBHelper.getDao(ExpenseType.class);
        Date now = Calendar.getInstance().getTime();

        for (User user: family) {
            ExpenseType familyExpenseType = new ExpenseType();
            familyExpenseType.setCreateTimeStamp(now);
            familyExpenseType.setSyncTimeStamp(now);
            familyExpenseType.setDescription("Family Common");
            familyExpenseType.setExpenseTargetUser(user);

            daoExpenseType.create(familyExpenseType);
        }
    }
}
