package com.parlakovi.petqjoro.ourbudget.UI.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.j256.ormlite.dao.Dao;
import com.parlakovi.petqjoro.ourbudget.DBObjects.ExpenseType;
import com.parlakovi.petqjoro.ourbudget.Global;
import com.parlakovi.petqjoro.ourbudget.MainActivity;
import com.parlakovi.petqjoro.ourbudget.R;
import com.parlakovi.petqjoro.ourbudget.UI.Adapters.SimpleTextArrayAdapter;
import com.parlakovi.petqjoro.ourbudget.UI.Interfaces.ISaveInstanceStateHandler;
import com.parlakovi.petqjoro.ourbudget.activities.BaseActivity;
import com.parlakovi.petqjoro.ourbudget.activities.addEditExpenseTypeActivity;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by georgi.parlakov on 10.7.2015
 */
public class ExpenseSpinnerWithAddNewController implements ISaveInstanceStateHandler {

    private final String EXTRA_ITEMS = "ExpenseTypesItems";
    private final Spinner mExpenseTypeSpinner;
    private final BaseActivity mActivity;
    private SimpleTextArrayAdapter mExpenseTypeAdapter;
    private Collection<ExpenseType> ItemsRestored;
    private int mNextItemPosition;
    private int mLastItemPosition;
    private boolean mInitialSelectHasPassed;

    public ExpenseSpinnerWithAddNewController(Spinner spinner, BaseActivity activity){
        mExpenseTypeSpinner = spinner;
        mActivity = activity;

        InitSpinner();
    }

    private void InitSpinner() {
        mExpenseTypeAdapter =
                new SimpleTextArrayAdapter(mActivity,
                        R.layout.simple_text_view_title,
                        R.id.simple_text_view_title_firstTextView,
                        false);

        mExpenseTypeSpinner.setAdapter(mExpenseTypeAdapter);

        if (ItemsRestored != null){
            mExpenseTypeAdapter.addAll(ItemsRestored);
            mExpenseTypeSpinner.setSelection(0);
        }
        else {
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
                }
            }.execute();
        }

        InitSpinnerEvents();
    }

    private void InitSpinnerEvents() {

        mExpenseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             /*   if (id == -1) {

                    Intent addUserIntent = new Intent(mActivity, addEditExpenseTypeActivity.class);

                    mActivity.startActivityForResult(addUserIntent, MainActivity.REQUEST_CODE_ADD_EXPENSE_TYPE);

                    mNextItemPosition = position;
                } else {
                    mLastItemPosition = position;
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int x = 1;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
//        bundle.putSerializable(EXTRA_ITEMS, mExpenseTypeAdapter.getAllItems());
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        this.ItemsRestored = (Collection<ExpenseType>)bundle.getSerializable(EXTRA_ITEMS);
    }

    public void OnAddNewSuccess(Intent data, Activity activity) {
        final ExpenseType newExpenseType =
                (ExpenseType)data.getSerializableExtra(MainActivity.NEWLY_CREATED_EXPENSE_TYPE);

        new AsyncTask<Void, Void, ExpenseType>() {
            public SQLException mSqlException;

            @Override
            protected ExpenseType doInBackground(Void... voids) {
                try {
                    Dao<ExpenseType, Integer> expenseTypeDao = Global.DBHelper.getDao(ExpenseType.class);
                    Calendar cal = Calendar.getInstance();

                    newExpenseType.setCreateTimeStamp(cal.getTime());
                    newExpenseType.setSyncTimeStamp(cal.getTime());

                    newExpenseType.setId(expenseTypeDao.create(newExpenseType));
                } catch (SQLException e) {
                     mSqlException = e;
                }
                return  newExpenseType;
            }

            @Override
            protected void onPostExecute(ExpenseType expenseType) {
                if (mSqlException != null){
                    mSqlException.printStackTrace();
                    mActivity.Finish(mSqlException.getMessage(),
                            "New Expense Type was not created successfuly");
                }else{
                    mExpenseTypeAdapter.add(expenseType);
                    mExpenseTypeSpinner.setSelection(mNextItemPosition);
                }
            }
        }.execute();

    }

    public void OnAddNewCanceled() {
        mExpenseTypeSpinner.setSelection(mLastItemPosition);
    }
}
