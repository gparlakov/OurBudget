package com.parlakovi.petqjoro.ourbudget;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.parlakovi.petqjoro.ourbudget.DBObjects.DataBaseManager;
import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.SimpleTextArrayAdapter;
import com.parlakovi.petqjoro.ourbudget.activities.addEditUserActivity;
import com.parlakovi.petqjoro.ourbudget.services.Users;

import java.sql.SQLException;
import java.util.Collection;


public class MainActivity extends OrmLiteBaseActivity<DataBaseManager> {

    private final static String CHOSEN_USER = "CHOSEN_USER";
    private static final int REQUEST_CODE_ADD_USER = 1000;

    private Spinner mUserSelectSpinner;
    private SimpleTextArrayAdapter mUserSelectSpinnerAdapter;
    private Spinner getUserSelectSpinner(){
        if (mUserSelectSpinner == null){
            mUserSelectSpinner = (Spinner) findViewById(R.id.spinner_payer);

            mUserSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (id == -1) {
                        /*Toast.makeText(parent.getContext(), "selected add new", Toast.LENGTH_SHORT).show();*/
                        getUserSelectSpinner().setSelection(0, false);

                        Intent addUserIntent = new Intent(MainActivity.this, addEditUserActivity.class);
                        addUserIntent.putExtra(addEditUserActivity.USER_ID_EXTRA, 0);
                        startActivityForResult(addUserIntent, REQUEST_CODE_ADD_USER);
                    }

                    /*mUserSelectSpinner.setSelection(position);*/
                    writeDownSelection();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        return mUserSelectSpinner;
    }

    public final static String NEWLY_CREATED_USER = "com.parlakovi.petqjoro.ourbuget.NEWLY_CREATED_USER";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final Activity act = this;
        switch (requestCode){
            case REQUEST_CODE_ADD_USER:{
                if (resultCode == RESULT_OK){
                    User user = (User)data.getSerializableExtra(NEWLY_CREATED_USER);

                    new AsyncTask<User, Void, Boolean>() {

                        SQLException sqlException;

                        @Override
                        protected Boolean doInBackground(User... params) {
                            User user = params[0];

                            Users usersMgr = new Users();
                            try {
                                User result = usersMgr.SaveNew(user);
                                mUserSelectSpinnerAdapter.add(user);
                                return true;
                            } catch (SQLException e) {
                                sqlException = e;
                                return false;
                            }
                        }

                        @Override
                        protected void onPostExecute(Boolean addedUserOk) {

                            if (!addedUserOk){
                                sqlException.printStackTrace();
                                Toast.makeText(act, "FAIL User not added", Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute(user);
                }
                break;
            }
            default:
                Log.e(Global.Log_Tag, "Result returned from an UNKNOWN request");
                finish();
                break;
        }
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

                mUserSelectSpinnerAdapter =
                        new SimpleTextArrayAdapter(this, R.layout.simple_text_view_title, R.id.simple_text_view_title_firstTextView);

                getUserSelectSpinner().setAdapter(mUserSelectSpinnerAdapter);

                mUserSelectSpinnerAdapter.addAll(users);
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
