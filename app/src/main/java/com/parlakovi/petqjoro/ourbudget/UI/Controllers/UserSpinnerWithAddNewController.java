package com.parlakovi.petqjoro.ourbudget.UI.Controllers;

import android.app.Activity;
import android.content.Intent;
import java.sql.SQLException;
import java.util.Collection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.Global;
import com.parlakovi.petqjoro.ourbudget.MainActivity;
import com.parlakovi.petqjoro.ourbudget.R;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.SimpleTextArrayAdapter;
import com.parlakovi.petqjoro.ourbudget.UI.Interfaces.ISaveInstanceStateHandler;
import com.parlakovi.petqjoro.ourbudget.activities.BaseActivity;
import com.parlakovi.petqjoro.ourbudget.activities.addEditUserActivity;
import com.parlakovi.petqjoro.ourbudget.services.Users;


/**
 * Created by georgi.parlakov on 10.7.2015
 */
public class UserSpinnerWithAddNewController implements ISaveInstanceStateHandler {

    private final Spinner mUserSelectSpinner;
    private final BaseActivity mActivity;
    private int mUserSelectSpinnerNewUserPosition;
    private int mUserSelectSpinnerLastSelectPosition;
    private SimpleTextArrayAdapter mUserSelectSpinnerAdapter;

    public UserSpinnerWithAddNewController(Spinner spinner, BaseActivity activity){
        mUserSelectSpinner = spinner;
        mActivity = activity;

        InitSpinner();

    }

    private void InitSpinner() {
        mUserSelectSpinnerAdapter =
                new SimpleTextArrayAdapter(mActivity, R.layout.simple_text_view_title, R.id.simple_text_view_title_firstTextView);

        mUserSelectSpinner.setAdapter(mUserSelectSpinnerAdapter);


        new AsyncTask<Void, Void, Collection<User>>() {

            SQLException mSqlException;
            @Override
            protected Collection<User> doInBackground(Void... params) {
                try {
                    Dao<User, Integer> daoUser = Global.DBHelper.getDao(User.class);
                    return daoUser.queryForAll();
                } catch (SQLException e) {
                    mSqlException = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Collection<User> users) {
                if (users == null){
                    mSqlException.printStackTrace();
                    mActivity.Finish("Cold not init spinner", "DB problem");
                }
                mUserSelectSpinnerAdapter.addAll(users);
                mUserSelectSpinner.setSelection(0);
                InitSpinnerEvents();
            }
        }.execute();
    }

    private void InitSpinnerEvents() {
        mUserSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id == -1) {

                    Intent addUserIntent = new Intent(mActivity, addEditUserActivity.class);
                    addUserIntent.putExtra(addEditUserActivity.USER_ID_EXTRA, 0);
                    mActivity.startActivityForResult(addUserIntent, MainActivity.REQUEST_CODE_ADD_USER);

                    mUserSelectSpinnerNewUserPosition = position;
                } else {
                    mUserSelectSpinnerLastSelectPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void OnAddNewSuccess(Intent data, final Activity act) {
        User user = (User)data.getSerializableExtra(MainActivity.NEWLY_CREATED_USER);

        mUserSelectSpinner.setSelection(mUserSelectSpinnerNewUserPosition, true);
        new AsyncTask<User, Void, User>() {

            SQLException sqlException = null;

            @Override
            protected User doInBackground(User... params) {
                User user = params[0];

                Users usersMgr = new Users();
                try {
                    return usersMgr.SaveNew(user);
                } catch (SQLException e) {
                    sqlException = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(User user) {

                if (user == null){
                    sqlException.printStackTrace();

                    Toast.makeText(act, "FAIL User not added", Toast.LENGTH_LONG).show();
                }
                else{
                    mUserSelectSpinnerAdapter.add(user);
                    mUserSelectSpinner.setSelection(mUserSelectSpinnerNewUserPosition);
                }
            }
        }.execute(user);
    }

    public void OnAddNewCanceled() {
        mUserSelectSpinner.setSelection(mUserSelectSpinnerLastSelectPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {

    }
}
