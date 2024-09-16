package com.logic.logicsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.parsers.IntegratedCircuitParser;

import java.util.List;

public class IntegratedListAdapter extends ArrayAdapter<IntegratedCircuitParser> {


    public IntegratedListAdapter(@NonNull Context context  , List<IntegratedCircuitParser> items ) {
        super(context ,0 , items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null)
           view= LayoutInflater.from(getContext()).inflate(R.layout.integrated_item_layout , null);

        TextView icname=view.findViewById(R.id.integrated_ic_name);
        icname.setText(getItem(position).getName());


        return view;
    }
}
