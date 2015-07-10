package com.parlakovi.petqjoro.ourbudget.UI.Controllers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.j256.ormlite.dao.Dao;
import com.parlakovi.petqjoro.ourbudget.DBObjects.Expense;
import com.parlakovi.petqjoro.ourbudget.DBObjects.ExpenseType;
import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.Global;
import com.parlakovi.petqjoro.ourbudget.R;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.SimpleTextArrayAdapter;
import com.parlakovi.petqjoro.ourbudget.UI.Interfaces.ISaveInstanceStateHandler;
import com.parlakovi.petqjoro.ourbudget.activities.BaseActivity;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by georgi.parlakov on 10.7.2015
 */
public class ExpenseSpinnerWithAddNewController implements ISaveInstanceStateHandler {

    private final Spinner mExpenseTypeSpinner;
    private final BaseActivity mActivity;
    private SimpleTextArrayAdapter mExpenseTypeAdapter;

    public ExpenseSpinnerWithAddNewController(Spinner spinner, BaseActivity activity){
        mExpenseTypeSpinner = spinner;
        mActivity = activity;

        InitSpinner();
    }

    private void InitSpinner() {
        mExpenseTypeAdapter =
                new SimpleTextArrayAdapter(mActivity, R.layout.simple_text_view_title, R.id.simple_text_view_title_firstTextView);

        mExpenseTypeSpinner.setAdapter(mExpenseTypeAdapter);


        new AsyncTask<Void, Void, Collection<ExpenseType>>() {

            SQLException mSqlException;

            @Override
            protected Collection<ExpenseType> doInBackground(Void... params) {
                try {
                    Dao<ExpenseType, Integer> daoExpenses = Global.DBHelper.getDao(ExpenseType.class);
                    return daoExpenses.queryForAll();
                } catch (SQLException e) {
                    mSqlException = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Collection<ExpenseType> expenseTypes) {
                if (expenseTypes == null) {
                    mSqlException.printStackTrace();
                    mActivity.Finish("Cold not init spinner", "DB problem");
                }
                mExpenseTypeAdapter.addAll(expenseTypes);
                mExpenseTypeSpinner.setSelection(0);
                InitSpinnerEvents();
            }
        }.execute();
    }

    private void InitSpinnerEvents() {
        mExpenseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {

    }
}
