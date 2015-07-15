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
    public static final String familiyName = "Семейство Парлъкови";
    private static final Date now = Calendar.getInstance().getTime();

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

            Collection<ExpenseType> defaultExpenseTypes = setInitialExpenseTypes(users);

            setInitialExpenseSubTypes(defaultExpenseTypes);
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


        User user_petya = new User();
        user_petya.setName("Петя");
        user_petya.setCreateTimeStamp(now);
        user_petya.setSyncTimeStamp(now);

        User user_joro = new User();
        user_joro.setName("Жоро");
        user_joro.setCreateTimeStamp(now);
        user_joro.setSyncTimeStamp(now);

        User user_famili = new User();
        user_famili.setName(familiyName);
        user_famili.setCreateTimeStamp(now);
        user_famili.setSyncTimeStamp(now);

        Collection<User> allUsers = daoUser.queryForAll();
        if (!allUsers.contains(user_joro) ) {

            daoUser.create(user_famili);
            daoUser.create(user_petya);
            daoUser.create(user_joro);
        }

        ArrayList<User> users = new ArrayList<>();
        users.add(user_famili);
        users.add(user_petya);
        users.add(user_joro);

        return users;
    }

    private Collection<ExpenseType> setInitialExpenseTypes(Collection<User> users) throws SQLException{
        Dao<ExpenseType, Integer> daoExpenseType = Global.DBHelper.getDao(ExpenseType.class);

        Collection<ExpenseType> result = new ArrayList<>();
        for (User user: users) {

            String description = "Лични: " + user.getName();
            if (user.getName() == familiyName){
                description = "Разходи общи";
            }

            ExpenseType expenseType = new ExpenseType();

            expenseType.setCreateTimeStamp(now);
            expenseType.setSyncTimeStamp(now);
            expenseType.setDescription(description);
            expenseType.setExpenseTargetUser(user);

            daoExpenseType.create(expenseType);
            result.add(expenseType);
        }

        return  result;
    }

    private void setInitialExpenseSubTypes(Collection<ExpenseType> expenseTypes) throws SQLException {
        Dao<ExpenseSubType, Integer> expenseSubTypeDao = Global.DBHelper.getDao(ExpenseSubType.class);

        for (ExpenseType expenseType: expenseTypes){
            ExpenseSubType defaultSubType = new ExpenseSubType();
            defaultSubType.ForExpenseType = expenseType;
            defaultSubType.Description = "Лични общи";

            if (expenseType.getDescription().equals("Разходи общи")){
                defaultSubType.Description = "Разходи общи";
            }

            expenseSubTypeDao.create(defaultSubType);
        }

    }

}
