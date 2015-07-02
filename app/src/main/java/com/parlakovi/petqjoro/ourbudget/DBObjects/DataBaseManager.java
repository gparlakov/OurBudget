package com.parlakovi.petqjoro.ourbudget.DBObjects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Calendar;
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

            Dao<User, Integer> daoUser = DataAccessObjectsManager.getDaoUser(connectionSource);

            Date now = Calendar.getInstance().getTime();
            User user = new User();
            user.setName("Petq");
            user.setCreateTimeStamp(now);
            user.setSyncTimeStamp(now);
            daoUser.create(user);

            User user1 = new User();
            user1.setName("Joro");
            user1.setCreateTimeStamp(now);
            user1.setSyncTimeStamp(now);
            daoUser.create(user1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }


}
