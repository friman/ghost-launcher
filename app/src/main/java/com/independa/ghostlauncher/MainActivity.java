package com.independa.ghostlauncher;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    public Intent androidIntent;
    public static final String TAG = "GhostActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Doing initial build of intent for Ghost Launcher.");

        /** uncomment the following for any debugging to allow ADB **/
        // Turn on ADB in case we have any strange crashes
        String command = "setprop persist.service.adb.enable 1";
        try {
            Process proc = Runtime.getRuntime().exec(new String[] {"su", "-c", command});
            proc.waitFor();
        } catch (Exception e) {
            Log.d(TAG, "Error in setting ADB: " + e.toString());
            e.printStackTrace();
        }
        command = "start adbd";
        try {
            Process proc = Runtime.getRuntime().exec(new String[] {"su", "-c", command});
            proc.waitFor();
        } catch (Exception e) {
            Log.d(TAG, "Error in setting ADB: " + e.toString());
            e.printStackTrace();
        }
        /****/

        /** set up to prevent any future installs from making a Google popup occur **/
        command = "settings put global package_verifier_enable 0";
        try {
            Process proc = Runtime.getRuntime().exec(new String[] {"su", "-c", command});
            proc.waitFor();
        } catch (Exception e) {
            Log.d(TAG, "Error in setting google settings: " + e.toString());
            e.printStackTrace();
        }
        /****/
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "Resuming and launching to Angela application again.");
        // This is set up to
        // (a) re-check if the intent is null (usually only on initial boot of firmware, but needs fixing).
        // (b) only run the intent if we have one to run, otherwise we have a boot-loop that messes up the OOBE of the Firmware.
        // -- this will wait on the async task associated to do the bootup.
        LaunchPackageTask lat = new LaunchPackageTask(MainActivity.this);
        lat.execute("com.independa.angelaandroid");
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