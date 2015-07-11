package com.parlakovi.petqjoro.ourbudget.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.j256.ormlite.dao.Dao;
import com.parlakovi.petqjoro.ourbudget.DBObjects.ExpenseType;
import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.Global;
import com.parlakovi.petqjoro.ourbudget.MainActivity;
import com.parlakovi.petqjoro.ourbudget.R;

import java.sql.SQLException;
import java.util.Collection;

public class addEditExpenseTypeActivity extends BaseAddEditActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_expense_type);

        mTitle = getString(R.string.title_addExpenseType);

        final Activity self = this;

        InitUI();
    }

    private void InitUI() {

       final Activity self = this;

        new AsyncTask<Void, Void, Collection<User>>() {
            public SQLException mSqlException;

            @Override
            protected Collection<User> doInBackground(Void... voids) {
                try {
                    Dao<User, Integer> usersDB = Global.DBHelper.getDao(User.class);
                    return  usersDB.queryForAll();
                } catch (SQLException e) {
                    mSqlException = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Collection<User> users) {
                if (users == null){
                    mSqlException.printStackTrace();
                }
                else {
                    ArrayAdapter<User> adapter = new ArrayAdapter<User>(self, R.layout.simple_text_view_title);
                    Spinner usersSpinner = (Spinner)self.findViewById(R.id.spinner_expenseType_user);
                    usersSpinner.setAdapter(adapter);
                    adapter.addAll(users);
                }

            }
        }.execute();

    }

    private void InitUIHandlers(){
        Button buttonOK = (Button)this.findViewById(R.id.button_addExpenseType_OK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseType newUser = new ExpenseType();

               /* EditText editText_addExpenseType_forUser = (EditText) self.findViewById(R.id.editText_addUser_Name);

                newUser.setName(editText_addUserName.getText().toString());

                Intent resultIntent = getIntent();
                resultIntent.putExtra(MainActivity.NEWLY_CREATED_USER, newUser);
                self.setResult(RESULT_OK, resultIntent);

                finish();*/
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_expense_type, menu);
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
