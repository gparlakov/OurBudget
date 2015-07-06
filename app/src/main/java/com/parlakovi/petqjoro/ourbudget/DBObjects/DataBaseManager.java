package com.parlakovi.petqjoro.ourbudget.DBObjects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.parlakovi.petqjoro.ourbudget.Global;

import java.sql.SQLException;
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
        Global.DAOManager = new DataAccessObjectsManager(connectionSource);

        try {
            TableUtils.createTableIfNotExists(connectionSource, User.class);

            TableUtils.createTableIfNotExists(connectionSource, Expense.class);

            TableUtils.createTableIfNotExists(connectionSource, ExpenseType.class);

            TableUtils.createTableIfNotExists(connectionSource, ExpenseEdit.class);

            setInitialDataForUsers();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    private void setInitialDataForUsers() throws SQLException{

        Dao<User, Integer> daoUser = Global.DAOManager.getDaoUser();
        Date now = Calendar.getInstance().getTime();

        User user_petya = new User();
        user_petya.setName("Petq");
        user_petya.setCreateTimeStamp(now);
        user_petya.setSyncTimeStamp(now);


        User user_joro = new User();
        user_joro.setName("Joro");
        user_joro.setCreateTimeStamp(now);
        user_joro.setSyncTimeStamp(now);

        Collection<User> allUsers = daoUser.queryForAll();
        if (!allUsers.contains(user_joro) ) {
            daoUser.create(user_petya);
            daoUser.create(user_joro);
        }
    }
}
