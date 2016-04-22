package com.tsotsi.assessment.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tsotsi.assessment.myapp.AppConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by TSOTSI on 2016/04/18.
 */
public class HttpGetImage {

    private static final String TAG = "HTTP_GET_POST";
    private static HttpGetImage httpGetImage;
    private static Context context;

    private HttpGetImage(Context context) {
        this.context = context;
    }

    public static synchronized HttpGetImage getInstance(Context context) {
        if (httpGetImage == null) {
            httpGetImage = new HttpGetImage(context);
        }
        return httpGetImage;
    }

    private static Bitmap readImageFromStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bmp = null;
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = byteArrayOutputStream.toByteArray();
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bmp = Bitmap.createScaledBitmap(bmp, AppConstants.WIDTH, AppConstants.HEIGHT, false);
        } catch (Exception ex) {
            Log.e(HttpGetImage.class.getName(), ex.getMessage());
        }

        return bmp;
    }

    public Bitmap getBitmap(String url) {
        Bitmap bmp = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                bmp = readImageFromStream(content);
                content.close();
            } else {
                Log.e(TAG, " Failed to download file");
            }
        } catch (ClientProtocolException e) {
            Log.e(TAG + " " + ClientProtocolException.class.getName(), e.getMessage());
        } catch (IOException e) {
            Log.e(TAG + " " + IOException.class.getName(), e.getMessage());
        }
        return bmp;
    }
}
