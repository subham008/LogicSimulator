package com.logic.logicsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.logic.logicsimulator.parsers.IntegratedCircuitParser;
import com.logic.logicsimulator.parsers.LinkPinElement;

import java.util.List;

public class MakeIcPinListAdapter extends ArrayAdapter<LinkPinElement> {

    public MakeIcPinListAdapter(Context context , List<LinkPinElement> pins, IntegratedCircuitParser integratedCircuitParser){
        super(context, 0 , pins);
        this.parser=integratedCircuitParser;
    }

    IntegratedCircuitParser parser=null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView=convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.pin_ic_layout, parent, false);
        }

        TextView pin_name=itemView.findViewById(R.id.ic_pin_name);
        pin_name.setText(getItem(position).getPinname());


        LinkPinElement pin=getItem(position);

        ImageButton remove=itemView.findViewById(R.id.ic_pin_remove_button);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeIcPinListAdapter.super.remove(pin);
                onRemove(pin , position);
            }
        });

        ImageButton orient=itemView.findViewById(R.id.pin_orientation);


        MakeIcPinListAdapter.setIcon(orient,getItem(position).getLinkPinOrient(), getContext());

        orient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   int oldOrient=getItem(position).getLinkPinOrient();
                   if( getItem(position).getLinkPinOrient() ==Pin.BOTTOM_PIN)
                       parser.changePinOrient(getItem(position),Pin.LEFT_PIN);
                   else
                       parser.changePinOrient(getItem(position),getItem(position).getLinkPinOrient()+100);
                   setIcon(orient,getItem(position).getLinkPinOrient(),getContext());

                   //orientationList.set(position , )
                   onChangeOrient(pin ,oldOrient, getItem(position).getLinkPinOrient(), position );
            }//end of onClick
        });

       return itemView;
    }





    public int  getOrient(int position){
        return  getItem(position).getLinkPinOrient();
    }

    public void onRemove(LinkPinElement pin , int position){

    }
    public void onChangeOrient(LinkPinElement pin, int Oldorientation ,int NEWorientation,int position){
        //overrid this method

    }

    private static  void  setIcon(ImageButton button , int orient , Context context){
        if(orient==Pin.LEFT_PIN)
            button.setImageDrawable(AppCompatResources.getDrawable( context, R.drawable.arrow_left));
        else if(orient==Pin.RIGHT_PIN)
            button.setImageDrawable(AppCompatResources.getDrawable( context, R.drawable.arrow_right));
        else if(orient==Pin.TOP_PIN)
            button.setImageDrawable(AppCompatResources.getDrawable( context, R.drawable.arrow_up));
        else if(orient==Pin.BOTTOM_PIN)
            button.setImageDrawable(AppCompatResources.getDrawable( context, R.drawable.arrow_down));
    }


}
