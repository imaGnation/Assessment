package com.tsotsi.assessment.ui.custom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsotsi.assessment.R;
import com.tsotsi.assessment.util.PlayAudio;

/**
 * Created by TSOTSI on 2016/04/19.
 */
public class CustomListView extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] descriptions;
    private final Bitmap[] bitmaps;
    private int pos;

    public CustomListView(Activity context,
                          String[] descriptions, Bitmap[] bitmaps) {
        super(context, R.layout.list_items, descriptions);
        this.context = context;
        this.descriptions = descriptions;
        this.bitmaps = bitmaps;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_items, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(descriptions[position]);
        imageView.setImageBitmap(bitmaps[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog.getInstance(getContext()).showFunctionAlertDialog(context.getString(R.string.message_start_track), descriptions[position]);
                PlayAudio.POSITION = position;
            }
        });

        return rowView;
    }

}
