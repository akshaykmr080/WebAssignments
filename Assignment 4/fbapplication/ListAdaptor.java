package com.example.akshaykumars.fbapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Akshay Kumar S on 4/13/2017.
 */

public class ListAdaptor extends ArrayAdapter<Items> {

    private Context context;
    public static String EXTRA_MESSAGE = "ItemOrViewId";
    public ListAdaptor(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public ListAdaptor(Context context, int resource, List<Items> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row, null);
        }

        Items p = getItem(position);

        if (p != null) {
            ImageView image = (ImageView) v.findViewById(R.id.imageView2);
            TextView tt2 = (TextView) v.findViewById(R.id.textView3);
            if (image != null) {
                Picasso.with(context).load(p.getUrl()).into(image);
            }

            if (tt2 != null) {
                tt2.setText(p.getName());
            }
            ImageView detailsImage = (ImageView) v.findViewById(R.id.imageView4);

            if(detailsImage != null){
                detailsImage.setOnClickListener(new MyListener(this.getContext(),p.getId()));
            }
        }

        return v;
    }

    private class MyListener implements View.OnClickListener{
        private Context mContext;
        private String mValue;
        public MyListener(Context context, String value){
            mContext = context;
            mValue = value;
        }

        @Override
        public void onClick(View v){
            Intent intent = new Intent(mContext, DetailsTab.class);
            intent.putExtra(EXTRA_MESSAGE, mValue);
            mContext.startActivity(intent);
        }
    }

}

