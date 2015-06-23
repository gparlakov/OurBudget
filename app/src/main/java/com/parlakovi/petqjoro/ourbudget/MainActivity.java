package com.parlakovi.petqjoro.ourbudget;

import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.parlakovi.petqjoro.ourbudget.DBObjects.DataAccessObjectsManager;
import com.parlakovi.petqjoro.ourbudget.DBObjects.DataBaseManager;
import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.IArrayAdapterItem;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.SimpleTextArrayAdapter;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


public class MainActivity extends OrmLiteBaseActivity<DataBaseManager> {

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            Dao<User, Integer> daoUser = getHelper().getDao(User.class);
            TextView tv = (TextView)findViewById(R.id.textView_Hello);

            /*tv.setText(tv.getText() + "Creating Petya" + System.getProperty("line.separator"));
            Log.i(Global.Log_Tag, "Creating petya");
            Date now = Calendar.getInstance().getTime();
            User user = new User();
            user.setName("Petya");
            user.setCreateTimeStamp(now);
            user.setSyncTimeStamp(now);
            daoUser.create(user);

            tv.setText(tv.getText() + "Petya id = " + user.getId());

            tv.setText(tv.getText() + "Creating Joro" + System.getProperty("line.separator"));

            User user1 = new User();
            user1.setName("Joro");
            user1.setCreateTimeStamp(now);
            user1.setSyncTimeStamp(now);
            daoUser.create(user1);

            tv.setText(tv.getText() + "Joro id = " + user.getId());*/

            Collection<User> users = daoUser.queryForAll();


            /*Iterator<User> iteratorUsers = users.iterator();
            while (iteratorUsers.hasNext()){
                User nextUser = iteratorUsers.next();
                Log.i(Global.Log_Tag, "User " + nextUser.getName());
                tv.setText(tv.getText() + "User" + nextUser.getName() + System.getProperty("line.separator"));
               *//* daoUser.delete(nextUser);*//*
            }*/

            SimpleTextArrayAdapter textAdapter =
                    new SimpleTextArrayAdapter(this, R.id.simple_text_view_title);
            Spinner spinner = (Spinner)findViewById(R.id.spinner_payer);
            spinner.setAdapter(textAdapter);

            textAdapter.addAll(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
