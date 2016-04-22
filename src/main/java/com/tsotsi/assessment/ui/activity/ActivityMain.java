package com.tsotsi.assessment.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tsotsi.assessment.R;
import com.tsotsi.assessment.ui.custom.CustomAlertDialog;
import com.tsotsi.assessment.util.NetworkConnectivity;

/**
 * Created by TSOTSI on 2016/04/20.
 */
public class ActivityMain extends Activity {
    public static boolean isNotConnected;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNotConnected) {
            CustomAlertDialog.getInstance(this).showAlertDialog(getString(R.string.check_for_internet_access), getString(R.string.no_internet_access_function_one));
            isNotConnected = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isNotConnected = false;
    }

    public void onClickSelectFunction(View view) {
        if (view.getId() == R.id.button_function_one) {

            if (NetworkConnectivity.getInstance(getApplicationContext()).isConnectedToInternet()) {
                intent = new Intent(this, FunctionOne.class);
            } else {
                CustomAlertDialog.getInstance(this).showAlertDialog(getString(R.string.check_for_internet_access), getString(R.string.no_internet_access_function_one));
            }
        } else if (view.getId() == R.id.button_function_two) {
            intent = new Intent(this, FunctionTwo.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}

