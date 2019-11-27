package com.xpensetracker.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.xpensetracker.BuildConfig;
import com.xpensetracker.R;

public class BaseActivity extends AppCompatActivity {

    protected String TAG;
    private ProgressDialog mProgressDialog;
    private boolean DEBUG = BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void setTAG(String activityName) {
        TAG = getClass().getName();
    }

    protected void showLogs(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    protected void showLogs(String TAG, String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    public View getView() {
        return getWindow().getDecorView().getRootView();
    }

    protected void showProgress(String msg, Boolean cancellable) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name), msg, false, cancellable);
    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    protected void showsnackBar(String msg, int length) {
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, msg, length).show();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
