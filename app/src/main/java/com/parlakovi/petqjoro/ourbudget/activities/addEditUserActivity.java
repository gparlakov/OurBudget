package com.parlakovi.petqjoro.ourbudget.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parlakovi.petqjoro.ourbudget.DBObjects.User;
import com.parlakovi.petqjoro.ourbudget.MainActivity;
import com.parlakovi.petqjoro.ourbudget.R;

public class addEditUserActivity extends ActionBarActivity {

    public static final String USER_ID_EXTRA = "user id extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.title_add_user);

        final Activity self = this;

        Button buttonOK = (Button)this.findViewById(R.id.button_addUser_OK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User();

                EditText editText_addUserName = (EditText) self.findViewById(R.id.editText_addUser_Name);

                newUser.setName(editText_addUserName.getText().toString());

                Intent resultIntent = getIntent();
                resultIntent.putExtra(MainActivity.NEWLY_CREATED_USER, newUser);
                self.setResult(RESULT_OK, resultIntent);

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_user, menu);
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

        onCancel();

        return true;
    }

    private void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
