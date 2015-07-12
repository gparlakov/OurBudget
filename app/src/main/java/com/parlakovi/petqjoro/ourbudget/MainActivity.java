package com.parlakovi.petqjoro.ourbudget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.parlakovi.petqjoro.ourbudget.UI.Controllers.ExpenseSpinnerWithAddNewController;
import com.parlakovi.petqjoro.ourbudget.UI.Controllers.UserSpinnerWithAddNewController;
import com.parlakovi.petqjoro.ourbudget.UI.Interfaces.ISaveInstanceStateHandler;
import com.parlakovi.petqjoro.ourbudget.activities.BaseActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class MainActivity extends BaseActivity {

    private final static String CHOSEN_USER = "com.parlakovi.petqjoro.ourbuget.CHOSEN_USER";
    public static final int REQUEST_CODE_ADD_EXPENSE_TYPE = 1001;
    public static final String NEWLY_CREATED_EXPENSE_TYPE = "com.parlakovi.petqjoro.ourbuget.NEWLY_CREATED_USER";
    private UserSpinnerWithAddNewController mUserSelectSpinnerController;
    private Collection<ISaveInstanceStateHandler> childrenThatHaveInstanceStateHandlers = new ArrayList<>();

    public static final int REQUEST_CODE_ADD_USER = 1000;
    public final static String NEWLY_CREATED_USER = "com.parlakovi.petqjoro.ourbuget.NEWLY_CREATED_USER";
    private ExpenseSpinnerWithAddNewController mExpenseTypeSpinnerController;

    private UserSpinnerWithAddNewController getUserSelectSpinnerController(){
        return mUserSelectSpinnerController;
    }

    private ExpenseSpinnerWithAddNewController getExpenseTypeSpinnerController(){
        return mExpenseTypeSpinnerController;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            InitUI();
        }
    }

    private void InitUI() {
        if (mUserSelectSpinnerController == null){
            mUserSelectSpinnerController =
                    new UserSpinnerWithAddNewController((Spinner)findViewById(R.id.spinner_payer), this);
            childrenThatHaveInstanceStateHandlers.add(mUserSelectSpinnerController);
        }

        if (mExpenseTypeSpinnerController == null) {
            mExpenseTypeSpinnerController =
                    new ExpenseSpinnerWithAddNewController((Spinner) findViewById(R.id.spinner_expenseType), this);
            childrenThatHaveInstanceStateHandlers.add(mExpenseTypeSpinnerController);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final Activity activity = this;
        switch (requestCode){
            case REQUEST_CODE_ADD_USER:{
                if (resultCode == RESULT_OK){
                    getUserSelectSpinnerController().OnAddNewSuccess(data, activity);
                }
                else {
                    getUserSelectSpinnerController().OnAddNewCanceled();
                }
                break;
            }
            case REQUEST_CODE_ADD_EXPENSE_TYPE:{
                if (resultCode == RESULT_OK){
                    getExpenseTypeSpinnerController().OnAddNewSuccess(data, activity);
                }
                else {
                    getExpenseTypeSpinnerController().OnAddNewCanceled();
                }
                break;
            }
            default:
                Log.e(Global.Log_Tag, "Result returned from an UNKNOWN request");
                finish();
                break;
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
        boolean handled;
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

        for (Iterator<ISaveInstanceStateHandler> i = childrenThatHaveInstanceStateHandlers.iterator(); i.hasNext();) {
            ISaveInstanceStateHandler item = i.next();
            item.onSaveInstanceState(outState);
        }
    }
}
