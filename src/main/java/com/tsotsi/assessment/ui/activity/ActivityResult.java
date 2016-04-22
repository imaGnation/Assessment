package com.tsotsi.assessment.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.tsotsi.assessment.R;
import com.tsotsi.assessment.myapp.AppConstants;

/**
 * Created by TSOTSI on 2016/04/21.
 */
public class ActivityResult extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setWelcomeMessage();
    }

    public void setNavigation() {
        Intent intent = new Intent(this, FunctionTwo.class);
        intent.putExtra(AppConstants.CAN_NAVIGATE, AppConstants.CAN_NAVIGATE);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setWelcomeMessage() {
        TextView txtWelcome = (TextView) findViewById(R.id.text_view_welcome);
        String name = getIntent().getExtras().getString(AppConstants.EXTRAS_NAME);
        String message = getIntent().getExtras().getString(AppConstants.EXTRAS_MESSAGE);

        txtWelcome.setText(getString(R.string.activity_result_message) + " " + name.toUpperCase() + message);
    }

    public void onClickResultScreen(View view) {
        if (view.getId() == R.id.button_return) {
            finish();
            setNavigation();
        } else if (view.getId() == R.id.button_exit) {
            finish();
            moveTaskToBack(true);
        }
    }

}
