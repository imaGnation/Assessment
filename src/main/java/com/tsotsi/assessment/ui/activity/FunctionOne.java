package com.tsotsi.assessment.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.tsotsi.assessment.R;
import com.tsotsi.assessment.myapp.AppConstants;
import com.tsotsi.assessment.myapp.AppMethods;
import com.tsotsi.assessment.ui.custom.CustomAlertDialog;
import com.tsotsi.assessment.ui.custom.CustomListView;
import com.tsotsi.assessment.util.HttpGetImage;
import com.tsotsi.assessment.util.NetworkConnectivity;

/**
 * Created by TSOTSI on 2016/04/17.
 */
public class FunctionOne extends Activity implements AppMethods.AsyncResponse {

    private ProgressDialog dialog;
    private ListView listView;
    private RetrieveImages asyncTask = new RetrieveImages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_one);

        if (NetworkConnectivity.getInstance(this).isConnectedToInternet()) {
            if (!NetworkConnectivity.getInstance(this).isFailOver) {
                asyncTask.execute();
                asyncTask.delegate = this;
            } else {
                CustomAlertDialog.getInstance(FunctionOne.this).showAlertDialog(getString(R.string.check_for_internet_access), getString(R.string.no_internet_access_function_one), null);
            }

        }
    }

    public void createListView(Bitmap[] bitmaps) {
        CustomListView adapter = new CustomListView(FunctionOne.this, AppConstants.DESCRIPTION, bitmaps);
        listView = (ListView) findViewById(R.id.function_one_list_view);
        listView.setAdapter(adapter);

    }

    @Override
    public void processResults(Bitmap[] bitmaps) {
        if (bitmaps != null) {
            createListView(bitmaps);
        }

    }

    private class RetrieveImages extends AsyncTask<String[], String, Bitmap[]> {

        public AppMethods.AsyncResponse delegate = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FunctionOne.this);
            dialog.setMessage(FunctionOne.this.getApplicationContext().getString(R.string.app_loader));
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Bitmap[] doInBackground(String[]... params) {
            Bitmap[] bitmaps = new Bitmap[AppConstants.IMAGE_URL.length];

            for (int x = 0; x < AppConstants.IMAGE_URL.length; x++) {
                Bitmap bitmap = HttpGetImage.getInstance(FunctionOne.this).getBitmap(AppConstants.IMAGE_URL[x]);
                if (bitmap != null) {
                    bitmaps[x] = bitmap;
                } else {
                    ActivityMain.isNotConnected = true;
                    dialog.dismiss();
                    finish();
                }
            }
            return bitmaps;
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmaps) {
            super.onPostExecute(bitmaps);
            delegate.processResults(bitmaps);
            dialog.dismiss();
        }
    }
}
