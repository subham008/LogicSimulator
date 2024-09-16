package com.logic.logicsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

public class CustomListAdapter extends ArrayAdapter<Customizable> {

    private Context context;

    public CustomListAdapter(Context con , Customizable array[] ,  LayoutInflater in){
        super(con , 0 , array);
        context=con;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View CustomItemView = convertView;

        Customizable customizable=super.getItem(position);
        Customizable.DataGroup data=customizable.retrieveData();


        if(CustomItemView == null && customizable.getAnnotType()==Customizable.MAKE_CHANGEABLE){
            CustomItemView = LayoutInflater.from(context).inflate(R.layout.make_changeable_layout, parent, false);

            TextView tag=CustomItemView.findViewById(R.id.make_change_tag);
            tag.setText(customizable.tag);

            TextView preview=CustomItemView.findViewById(R.id.make_change_preview);
            preview.setText(String.valueOf(data.number));

            ImageButton add_but=CustomItemView.findViewById(R.id.make_change_add);
            add_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int after_change=Integer.parseInt(preview.getText().toString())+1;
                    preview.setText(String.valueOf(after_change));
                    customizable.onChangeButton(false,true,after_change,null);
                }
            });

            ImageButton sub_but=CustomItemView.findViewById(R.id.make_change_sub);
            add_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int after_change=Integer.parseInt(preview.getText().toString())-1;
                    preview.setText(String.valueOf(after_change));
                    customizable.onChangeButton(false,false,after_change,null);
                }
            });
        }//end of make chnageable if



        if(CustomItemView == null && customizable.getAnnotType()==Customizable.MAKE_TOGGLEABLE) {
            CustomItemView = LayoutInflater.from(context).inflate(R.layout.make_toggle_layout, parent, false);
            TextView tag=CustomItemView.findViewById(R.id.make_toggle_tag);
            tag.setText(customizable.tag);

            SwitchCompat switch_compat=CustomItemView.findViewById(R.id.make_toggle_switch_compat);
            switch_compat.setChecked(data.state);

            switch_compat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customizable.onChangeButton(switch_compat.isChecked(),false,0,null);
                }
            });
        }//end of make toggle if


        if(CustomItemView == null && customizable.getAnnotType()==Customizable.MAKE_NUMBER_EDITABLE) {
            System.err.println("make editable entered");
            CustomItemView = LayoutInflater.from(context).inflate(R.layout.make_number_editable_layout, parent, false);

            TextView tag=CustomItemView.findViewById(R.id.make_number_tag);
            tag.setText(customizable.tag);

            EditText user_input=CustomItemView.findViewById(R.id.make_number_edit);
            user_input.setText(String.valueOf(data.number));

            Button save=CustomItemView.findViewById(R.id.make_number_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customizable.onChangeButton(false,false,Integer.parseInt(user_input.getText().toString()),null);
                }
            });
        }//end of make toggle if


        if(CustomItemView == null &&  customizable.getAnnotType()==Customizable.MAKE_TEXT_EDITABLE) {
            CustomItemView = LayoutInflater.from(context).inflate(R.layout.make_text_editable_layout, parent, false);


            TextView tag=CustomItemView.findViewById(R.id.make_text_tag);
            tag.setText(customizable.tag);

            EditText user_input=CustomItemView.findViewById(R.id.make_text_edit);
            user_input.setText(data.text);

            Button save=CustomItemView.findViewById(R.id.make_text_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customizable.onChangeButton(false,false,0,user_input.getText().toString());
                }
            });
        }//end of make toggle if

        if(CustomItemView == null &&  customizable.getAnnotType()==Customizable.MAKE_SEEKABLE) {
            CustomItemView = LayoutInflater.from(context).inflate(R.layout.make_seekable_layout, parent, false);


            TextView tag=CustomItemView.findViewById(R.id.make_seek_tag);
            tag.setText(customizable.tag);

            TextView value=CustomItemView.findViewById(R.id.make_seek_state);
            value.setText(String.valueOf(data.number));

            SeekBar seekBar=CustomItemView.findViewById(R.id.make_seek_seekbar);
            seekBar.setMax(data.max);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                      value.setText(String.valueOf(progress));
                      customizable.onChangeButton(false,false,progress,null);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }//end of make toggle if

        if(CustomItemView==null)
            LayoutInflater.from(context).inflate(R.layout.make_text_editable_layout, parent, false);

        return CustomItemView;
    }//end of getView
}//end of class

