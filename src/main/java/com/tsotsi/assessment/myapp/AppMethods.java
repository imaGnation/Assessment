package com.tsotsi.assessment.myapp;

import android.graphics.Bitmap;

/**
 * Created by TSOTSI on 2016/04/19.
 */
public class AppMethods {
    public interface AsyncResponse {
        void processResults(Bitmap[] bitmaps);
    }

    public static interface ReturnToPreviousScreen {
        public void returnToPrevious(boolean bool);
    }
}
