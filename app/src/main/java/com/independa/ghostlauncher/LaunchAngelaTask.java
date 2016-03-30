package com.independa.ghostlauncher;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by independadeveloper on 3/29/16.
 */
public class LaunchAngelaTask extends AsyncTask<String, String, String> {
    public MainActivity thisActivity;

    public LaunchAngelaTask(MainActivity inActivity) {
        thisActivity = inActivity;
    }

    @Override
    protected String doInBackground(String... in_strings) {
        String retval = "error";
        Integer testval = 0;
        Log.d(this.thisActivity.TAG, "Now attempting while loop for angelaAndroid intent search");
        // Wait while the package doesn't yet exist in the system.
        while ((this.thisActivity.androidIntent = this.thisActivity.getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.independa.angelaandroid")) == null) {
            testval++;
            if ((testval%1000) == 0) {
                Log.d(this.thisActivity.TAG, "Still waiting for angelaandroid package in Ghost Launcher. Testval counter = " + testval.toString());
            }
        }

        if (this.thisActivity.androidIntent != null) {
            retval = "success";
        }

        return retval;
    }

    @Override
    protected void onPostExecute(String in_string) {
        this.thisActivity.androidIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.thisActivity.getApplicationContext().startActivity(this.thisActivity.androidIntent);
    }
}