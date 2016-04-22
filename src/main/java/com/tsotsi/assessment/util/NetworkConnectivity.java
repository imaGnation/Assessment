package com.tsotsi.assessment.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by TSOTSI on 2016/04/17.
 */
public class NetworkConnectivity {

    private static NetworkConnectivity networkConnectivity;
    public boolean isFailOver;
    private Context context;

    private NetworkConnectivity(Context context) {
        this.context = context;
    }

    public static synchronized NetworkConnectivity getInstance(Context context) {
        if (networkConnectivity == null) {
            networkConnectivity = new NetworkConnectivity(context);
        }
        return networkConnectivity;
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService((Context.CONNECTIVITY_SERVICE));
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                isFailOver = networkInfo.isFailover();
                return true;
            }
        }
        return false;
    }

    public boolean getIsFailOver() {
        return isFailOver;
    }

}
