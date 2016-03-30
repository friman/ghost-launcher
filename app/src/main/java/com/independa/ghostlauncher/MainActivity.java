package com.independa.ghostlauncher;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public Intent androidIntent;
    public static final String TAG = "GhostActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Doing initial build of intent for Ghost Launcher.");


        androidIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.independa.angelaandroid");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "Resuming and launching to Angela application again.");
        // This is set up to
        // (a) re-check if the intent is null (usually only on initial boot of firmware, but needs fixing).
        // (b) only run the intent if we have one to run, otherwise we have a boot-loop that messes up the OOBE of the Firmware.
        // -- this will wait on the async task associated to do the bootup.
//        if (androidIntent == null) {androidIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.independa.angelaandroid");}
//        if (androidIntent != null) {
//            androidIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//            getApplicationContext().startActivity(androidIntent);
//        }
        LaunchAngelaTask lat = new LaunchAngelaTask(MainActivity.this);
        lat.execute("RUN");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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