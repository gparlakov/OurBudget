package com.parlakovi.petqjoro.ourbudget.services;

import com.j256.ormlite.dao.Dao;
import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.Global;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by georgi.parlakov on 6.7.2015
 */
public class Users {
    public User SaveNew(User user) throws SQLException{
        User result = new User();

        Calendar calendar = Calendar.getInstance();
        result.setName(user.getName());
        result.setCreateTimeStamp(calendar.getTime());
        result.setSyncTimeStamp(calendar.getTime());

        Dao<User, Integer> daoUser = Global.DBHelper.getDao(User.class);
        daoUser.create(result);

        return result;
    }
}
