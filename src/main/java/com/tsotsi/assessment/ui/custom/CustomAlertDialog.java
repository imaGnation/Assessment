package com.tsotsi.assessment.ui.custom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.tsotsi.assessment.R;
import com.tsotsi.assessment.util.PlayAudio;

/**
 * Created by TSOTSI on 2016/04/19.
 */
public class CustomAlertDialog {
    private static CustomAlertDialog alertDialog;
    private static Context context;
    private static Intent playAudioIntent;
    private String description;

    private CustomAlertDialog(Context context) {
        this.context = context;
        playAudioIntent = new Intent(context, PlayAudio.class);
    }

    public static synchronized CustomAlertDialog getInstance(Context context) {
        if (alertDialog == null) {
            alertDialog = new CustomAlertDialog(context);
        }
        return alertDialog;
    }

    public void stopAudio() {
        context.stopService(playAudioIntent);
    }

    public void playAudio() {
        context.startService(playAudioIntent);
        showAlertDialog(context.getString(R.string.message_stop_track), description, context.getString(R.string.dialog_play));
    }

    public void showFunctionAlertDialog(String title, String message) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        description = message;

        alertDialog.setButton(context.getString(R.string.dialog_play), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playAudio();
            }
        });

        alertDialog.setButton2(context.getString(R.string.dialog_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopAudio();
            }
        });
        alertDialog.show();
    }


    public void showAlertDialog(final String title, String message, final String tag) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton(context.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (tag == context.getString(R.string.dialog_play)) {
                    stopAudio();
                } else if(tag == "STOP"){
                    System.exit(0);
                }else if (title == context.getString(R.string.no_internet_access_function_one)) {

                }
            }
        });
        alertDialog.show();
    }

    public void showAlertDialog(final String title, String message) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton(context.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
