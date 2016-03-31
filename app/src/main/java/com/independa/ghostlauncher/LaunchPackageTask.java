package com.independa.ghostlauncher;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by independadeveloper on 3/29/16.
 */
public class LaunchPackageTask extends AsyncTask<String, Void, String> {
    public MainActivity thisActivity;

    public LaunchPackageTask(MainActivity inActivity) {
        thisActivity = inActivity;
    }

    @Override
    protected String doInBackground(String... in_strings) {
        try {
            String packageToLaunch = in_strings[0];
            String retval = "error";
            Integer testval = 0;
            Log.d(this.thisActivity.TAG, "Now attempting while loop for angelaAndroid intent search");
            // Wait while the package doesn't yet exist in the system.
            this.thisActivity.androidIntent = this.thisActivity.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageToLaunch);
            while (this.thisActivity.androidIntent == null) {
                Thread.sleep(1000);
                this.thisActivity.androidIntent = this.thisActivity.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageToLaunch);
            }

            retval = "success";

            return retval;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(this.thisActivity.TAG, "Error in LaunchPackageTask, probably no package defined.");
        }

        return "error";
    }

    @Override
    protected void onPostExecute(String in_string) {
        if (!in_string.equals("success")) {
            if (in_string.equals("error")) {
                Log.d(this.thisActivity.TAG, "Error in LaunchPackageTask, check trace logs.");
                return;
            }
            LaunchPackageTask lpt = new LaunchPackageTask(this.thisActivity);
            lpt.execute(in_string);
        } else {
            this.thisActivity.androidIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            this.thisActivity.getApplicationContext().startActivity(this.thisActivity.androidIntent);
        }
    }
}