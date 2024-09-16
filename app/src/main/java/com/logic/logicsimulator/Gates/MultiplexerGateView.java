package com.logic.logicsimulator.Gates;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

public class MultiplexerGateView extends BasicGateView {

    public static final String GATE_TYPE="Multiplexer";
    public MultiplexerGateView(Context context, EditorLayout layout,int selectPinCount){
        super(context,layout,(int)Math.pow(2,selectPinCount),1,0,selectPinCount);

        super.gateName="Multiplexer";
        super.gateType=GATE_TYPE;

        for(Pin p:pins)
            p.pinSignalType=Pin.INPUT;
        for(Pin p:opin)
            p.pinSignalType=Pin.OUTPUT;
        for(Pin p:bpins)
            p.pinSignalType=Pin.INPUT;

        for(int i=0; i<pins.length; i++){
            pins[i].setPinName("I"+String.valueOf(i));
        }

        for(int i=0; i<bpins.length; i++){
            bpins[i].setPinName("S"+String.valueOf(i));
        }

        opin[0].setPinName("out");

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
        canvas.drawText(pins.length+"X"+opin.length,ax,ay+50,p);
        p.setTextSize(36);
        canvas.drawText("MUX",ax,ay+110,p);
    }



    @Override
    public void Logic(){
        int address=0;
        int count=0;
        for(int i=bpins.length-1 ; i>=0 ; i--){
            if(bpins[i].getValue()){
                address+=(int)(1*Math.pow(2, count));
            }

            count++;
        }

        if(address>=0 &&address<pins.length)
             opin[0].setValue(pins[address].getValue());

        opin[0].forwardValue();
    }
}
