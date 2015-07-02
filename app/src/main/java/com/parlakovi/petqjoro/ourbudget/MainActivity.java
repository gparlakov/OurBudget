package com.parlakovi.petqjoro.ourbudget;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.parlakovi.petqjoro.ourbudget.DBObjects.DataBaseManager;
import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.SimpleTextArrayAdapter;

import java.sql.SQLException;
import java.util.Collection;


public class MainActivity extends OrmLiteBaseActivity<DataBaseManager> {

    private final static String CHOSEN_USER = "CHOSEN_USER";

    private Spinner mSpinner;
    private Spinner getUserSelectSpinner(){
        if (mSpinner == null){
            mSpinner = (Spinner) findViewById(R.id.spinner_payer);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (id == -1) {
                        Toast.makeText(parent.getContext(), "selected add new", Toast.LENGTH_SHORT).show();
                        getUserSelectSpinner().setSelection(0, false);
                    }

                    /*mSpinner.setSelection(position);*/
                    writeDownSelection();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        return  mSpinner;
    }



    private void writeDownSelection() {
        Log.i(Global.Log_Tag,  getUserSelectSpinner().getSelectedItemId()+ "");
    }

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            try {
                Dao<User, Integer> daoUser = getHelper().getDao(User.class);
                Collection<User> users = daoUser.queryForAll();

                SimpleTextArrayAdapter textAdapter =
                        new SimpleTextArrayAdapter(this, R.layout.simple_text_view_title, R.id.simple_text_view_title_firstTextView);

                getUserSelectSpinner().setAdapter(textAdapter);

                textAdapter.addAll(users);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings: {
                // call settings activity/fragment
                handled = true;
                break;
            }
            default:{
                handled = super.onOptionsItemSelected(item);
            }
        }

        return handled;
    }

    @Override
     protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CHOSEN_USER, 0);
    }
}
