package com.xpensetracker.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.xpensetracker.BuildConfig;
import com.xpensetracker.R;

public class BaseFragment extends Fragment {

    protected String TAG;
    private ProgressDialog mProgressDialog;
    private boolean DEBUG = BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void startFragment(String fragment) {
        switch (fragment) {
        }
    }

    public void clearBackStack(Activity activity) {
        try {
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();

            if (fragmentManager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
                fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setTAG(String activityName) {
        TAG = getFragmentManager().getClass().getName();
    }

    protected void showLogs(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    protected void showLogs(String TAG,String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    protected void showProgress(String msg, Boolean cancellable) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.app_name), msg, false, cancellable);
    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    protected void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        View view = getActivity().findViewById(android.R.id.content);
        Snackbar.make(view, msg, length).show();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
