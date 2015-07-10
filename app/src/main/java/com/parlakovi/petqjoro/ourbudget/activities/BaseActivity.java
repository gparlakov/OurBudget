package com.parlakovi.petqjoro.ourbudget.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.parlakovi.petqjoro.ourbudget.DBObjects.DataBaseManager;
import com.parlakovi.petqjoro.ourbudget.Global;

/**
 * Created by georgi.parlakov on 10.7.2015
 */
public class BaseActivity extends OrmLiteBaseActivity<DataBaseManager> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitGlobalDBHelper();
    }

    private void InitGlobalDBHelper() {
        Global.DBHelper = Global.DBHelper != null ? Global.DBHelper : this.getHelper();
    }

    public void Finish(String errorMessageForLog, String userMessage){
        Toast.makeText(this, userMessage, Toast.LENGTH_SHORT).show();
        Log.e(Global.Log_Tag, errorMessageForLog);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
