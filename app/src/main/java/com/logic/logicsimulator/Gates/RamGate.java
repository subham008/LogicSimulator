package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

public class RamGate extends BasicGateView {
    public static final String GATE_TYPE="RamGate";

    private int memsize=0;
    private boolean[][] data;
    public RamGate(Context context , EditorLayout layout , int mem_select_size){
        super(context , layout , 8 ,8 , 3 ,mem_select_size );
        this.memsize=(int)Math.pow(2,mem_select_size);
        super.gateName="Ram Gate";
        super.gateType=GATE_TYPE;

        for(Pin p:tpins)
            p.pinSignalType=Pin.INPUT;


        for (int i=0; i<pins.length; i++){
            pins[i].setPinName("I"+i);
        }

        for (int i=0; i<bpins.length; i++){
            bpins[i].setPinName("S"+i);
        }

        for (int i=0; i<opin.length; i++){
            opin[i].setPinName("O"+i);
        }

        tpins[0].setPinName("clr");  tpins[1].setPinName("set");  tpins[2].setPinName("sel");


        data=new boolean[8][memsize];

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
        canvas.drawText(Integer.toString(memsize),ax,ay+50,p);
        p.setTextSize(35);
        canvas.drawText("Byte",ax,ay+110,p);
    }

    @Override
    public void Logic(){
        if(tpins[0].getValue() && tpins[1].getValue())
            return;

        int address=0;
        int count=0;
        for(int i=bpins.length-1 ; i>=0 ; i--){
            if(bpins[i].getValue()){
                address+=(int)(1*Math.pow(2, count));
            }

            count++;
        }

        //if clr is true
        if(tpins[0].getValue())
            for(int i=0; i<8; i++)
                data[i][address]=false;


        //if set is true
        if(tpins[1].getValue())
            for(int i=0; i<8; i++) {
                data[i][address] = pins[i].getValue();
            }

        //if select is true
        if(tpins[2].getValue())
            for(int i=0; i<8; i++) {
                opin[i].setValue(data[i][address]);
                opin[i].forwardValue();
            }
        else
            for(int i=0; i<8; i++) {
                opin[i].setValue(false);
                opin[i].forwardValue();
            }
    }

}
