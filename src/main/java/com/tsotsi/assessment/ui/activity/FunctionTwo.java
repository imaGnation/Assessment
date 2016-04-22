package com.tsotsi.assessment.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tsotsi.assessment.R;
import com.tsotsi.assessment.myapp.AppConstants;
import com.tsotsi.assessment.myapp.Names;
import com.tsotsi.assessment.ui.custom.CustomAlertDialog;

import java.sql.SQLException;

/**
 * Created by TSOTSI on 2016/04/20.
 */
public class FunctionTwo extends Activity {

    private EditText txtName;
    private Names dataSource;
    private String name;
    private String canNavigate = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_two);
        txtName = (EditText) findViewById(R.id.edit_text_name);

        dataSource = new Names(this);
        String name = getNameFromDB();

        this.name = name;
        canNavigate = getIntent().getStringExtra(AppConstants.CAN_NAVIGATE);
        setupActivityResult(name, getString(R.string.message_activity_result));
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource = new Names(this);
        String name = getNameFromDB();

        this.name = name;
        setupActivityResult(name, getString(R.string.message_activity_result));
    }

    private void setupActivityResult(String name, String message) {
        Intent intent = new Intent(this, ActivityResult.class);
        intent.putExtra(AppConstants.EXTRAS_NAME, name);
        intent.putExtra(AppConstants.EXTRAS_MESSAGE, message);

        if (name != null && canNavigate == null) {
            startActivity(intent);
        }
    }

    public String getNameFromDB() {
        String name = "";
        try {
            dataSource.open();
            name = dataSource.getName(AppConstants.DEFAULT_ID);
        } catch (Exception ex) {

        } finally {
            dataSource.close();
        }
        return name;
    }

    public void onClickSubmit(View view) {
        String name = txtName.getText().toString();

        if (view.getId() == R.id.button_submit_name) {
            if (isValidName()) {
                this.canNavigate = null;
                txtName.setText("");
                saveName(name);
                setupActivityResult(name, "");
            } else {
                CustomAlertDialog.getInstance(this).showAlertDialog("Validation", "Name is required", null);
                txtName.requestFocus();
            }
        }
    }

    boolean isValidName() {
        boolean valid = false;
        if (txtName.getText().length() > 0) {
            valid = true;
        }
        return valid;
    }

    public void saveName(String name) {
        try {
            dataSource.open();
            dataSource.addName(name);
        } catch (SQLException ex) {
            Log.e(getClass().getName(), ex.getMessage(), ex.getCause());
        } finally {
            dataSource.close();
        }
    }

}
