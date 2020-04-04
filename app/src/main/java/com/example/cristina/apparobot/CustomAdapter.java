package com.example.cristina.apparobot;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cristina on 28/05/2018.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private int customLayout;
    private ArrayList<Directions> data;

    public CustomAdapter(Context context, int customlayout, ArrayList<Directions> data) {
        this.data = data;
        this.context = context;
        this.customLayout = customlayout;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int i) {

        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(customLayout, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Directions dir = (Directions) getItem(pos);
        viewHolder.direction1.setText(dir.getDirection());
        viewHolder.seg1.setText(dir.getSeconds());

        viewHolder.direction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customListener != null)
                {
                    customListener.onClickListener(pos);
                }
            }
        });

        viewHolder.seg1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                customListener.onChangeSeconds(pos, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    private class ViewHolder
    {
        Button direction1;
        EditText seg1;


        public ViewHolder(View view) {
            direction1 = (Button) view.findViewById(R.id.Direction1);
            seg1 = (EditText) view.findViewById(R.id.seg1);

        }
    }

    CustomListener customListener;

    public interface CustomListener
    {
        public void onClickListener(int position);
        public void onChangeSeconds(int position, String second);
    }

    public void setCustomListener(CustomListener listener)
    {
        this.customListener = listener;
    }

}

