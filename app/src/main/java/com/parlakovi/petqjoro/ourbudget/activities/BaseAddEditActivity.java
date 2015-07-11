package com.parlakovi.petqjoro.ourbudget.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parlakovi.petqjoro.ourbudget.R;

public class BaseAddEditActivity extends ActionBarActivity {

    protected String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onCancel();
        return true;
    }

    private void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
