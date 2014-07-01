package com.gistec.layerdemo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SettingsActivity extends Activity {

    @InjectView(R.id.baseMapInput)
    EditText baseMapEt;

    @InjectView(R.id.featureLayerInput)
    EditText featureLayerEt;

    @InjectView(R.id.userNameInput)
    EditText userNameEt;

    @InjectView(R.id.passwordInput)
    EditText passwordEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        restorePreviousSettings();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    public void saveSettings() {

        if (baseMapEt.getText().length() != 0 && featureLayerEt.getText().length() != 0 && userNameEt.getText().length() != 0 && passwordEt.getText().length() != 0) {

            String baseMapURL = baseMapEt.getText().toString();
            String featureLayerURL = featureLayerEt.getText().toString();

            String userName = userNameEt.getText().toString();
            String password = passwordEt.getText().toString();


            SharedPreferences preferences = getSharedPreferences(Globals.SETTINGS_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Globals.BASEMAP_KEY, baseMapURL);
            editor.putString(Globals.FLAYER_KEY, featureLayerURL);
            editor.putString(Globals.USERNAME_KEY, userName);
            editor.putString(Globals.PASSWORD_KEY, password);

            editor.commit();

            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, getString(R.string.pleaseFillAll_msg), Toast.LENGTH_SHORT).show();
        }

    }

    private void restorePreviousSettings() {

        SharedPreferences preferences = getSharedPreferences(Globals.SETTINGS_FILE, Context.MODE_PRIVATE);

        String baseMapURL = preferences.getString(Globals.BASEMAP_KEY, "");
        String featureLayerURL = preferences.getString(Globals.FLAYER_KEY, "");

        String userName = preferences.getString(Globals.USERNAME_KEY, "");
        String password = preferences.getString(Globals.PASSWORD_KEY, "");


        if (!baseMapURL.equals("") && !featureLayerURL.equals("") && !userName.equals("") && !password.equals("")) {

            baseMapEt.setText(baseMapURL);
            featureLayerEt.setText(featureLayerURL);
            userNameEt.setText(userName);
            passwordEt.setText(password);

        } else {

            baseMapEt.setText(Globals.BASEMAP_URL);
            featureLayerEt.setText(Globals.FLAYER_URL);
            userNameEt.setText(Globals.USERNAME_KEY);
            passwordEt.setText(Globals.PASSWORD_KEY);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.saveItem:
                saveSettings();


                break;

            default:

                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
