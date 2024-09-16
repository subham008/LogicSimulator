package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;

public class JKFlipFlop extends BasicGateView {
    public static final String GATE_TYPE="jkflipflop";

    public JKFlipFlop(Context context , EditorLayout layout){
        super(context , layout , 3,2,1,1);
        super.gateName="JK Flip Flop";
        super.gateType=GATE_TYPE;

        pins[0].setPinName("J");
        pins[1].setPinName("clk");
        pins[2].setPinName("K");

        opin[0].setPinName("Q");
        opin[1].setPinName("Q'");

        tpins[0].setPinName("Preset");
        tpins[0].setPinName("Clear");

    }

    @Override
    public void drawicon(RectF r , Canvas canvas, Paint p ){
        int width=80;
        int height=90;
        Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);
        int ax=re.left+(re.width()-(width))/2;
        int ay=re.top+(re.height() - height)/2;
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xffffffff);
        p.setStrokeWidth(2);
        p.setTextSize(50);
        canvas.drawText("JK",ax,ay+50,p);
        p.setTextSize(20);
        canvas.drawText("Flip Flop",ax,ay+110,p);
    }


    //jk is as same as SR just when both J and K becomes true  , it starts toggles
    @Override
    public void Logic(){

        if(pins[0].getValue() && pins[2].getValue() && pins[1].getValue() ){
            opin[0].setValue(!opin[0].getValue());
            opin[1].setValue(!opin[1].getValue());

            opin[0].forwardValue();
            opin[1].forwardValue();

            return;
        }



        boolean changed=false;
        // if J is true
        if((pins[0].getValue() && pins[1].getValue()) || tpins[0].getValue()){
            opin[0].setValue(true);
            opin[1].setValue(false);
            changed=true;
        }

        // if K is true
        if((pins[2].getValue() && pins[1].getValue()) || bpins[0].getValue()){
            opin[0].setValue(false);
            opin[1].setValue(true);
            changed=true;
        }

        if(changed) {
            opin[0].forwardValue();
            opin[1].forwardValue();
        }


    }
}
