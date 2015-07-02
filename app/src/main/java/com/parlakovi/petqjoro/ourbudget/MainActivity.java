package com.parlakovi.petqjoro.ourbudget;

import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
            Collection<User> users = daoUser.queryForAll();

            SimpleTextArrayAdapter textAdapter =
                    new SimpleTextArrayAdapter(this, R.layout.simple_text_view_title, R.id.simple_text_view_title_firstTextView);
            final Spinner spinner = (Spinner)findViewById(R.id.spinner_payer);
            spinner.setAdapter(textAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (id == -1){
                        Toast.makeText(parent.getContext(), "selected add new", Toast.LENGTH_SHORT).show();
                    }
                    spinner.setSelection(0, false);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

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
