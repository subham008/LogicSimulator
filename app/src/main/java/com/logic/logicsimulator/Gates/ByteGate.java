package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;


public class ByteGate extends BasicGateView {
    public static final String GATE_TYPE="byte1";
    public ByteGate(Context context , EditorLayout layout){
        super(context,layout,8,8,1,2);
        super.gateName="1-Byte";
        super.gateType=GATE_TYPE;

        tpins[0].pinSignalType= Pin.INPUT;

        for (int i=0; i<pins.length; i++){
            pins[i].setPinName("I"+i);
        }

        for (int i=0; i<opin.length; i++){
            opin[i].setPinName("O"+i);
        }

        tpins[0].setPinName("clear");

        bpins[0].setPinName("set");  bpins[1].setPinName("sel");

    }



    @Override
    public void drawicon(RectF r , Canvas canvas, Paint p){
        int width=80;
        int height=90;
        Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);
        int ax=re.left+(re.width()-(width))/2;
        int ay=re.top+(re.height() - height)/2;
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xffffffff);
        p.setStrokeWidth(2);
        p.setTextSize(50);
        canvas.drawText("1",ax,ay+50,p);
        p.setTextSize(35);
        canvas.drawText("Byte",ax,ay+110,p);
    }


    boolean[] data=new boolean[8];
    @Override
    public void Logic(){
        // if clear and set is true at same time
        if(tpins[0].getValue() && bpins[0].getValue())
            return;
        //if clear is true
        if(tpins[0].getValue())
            for(boolean d:data)
                d=false;

        //if set pin is true
        if(bpins[0].getValue())
            for(int i=0; i<data.length; i++){
                data[i]=pins[i].getValue();
            }

        //if select pin is true
        if(bpins[1].getValue())
            for(int i=0; i<data.length; i++){
               opin[i].setValue(data[i]);
               opin[i].forwardValue();
            }
        else
            for(int i=0; i<data.length; i++){
                opin[i].setValue(false);
                opin[i].forwardValue();
            }
    }
}
