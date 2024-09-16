package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;

public class SRFlipFlop extends BasicGateView {

    public static final String GATE_TYPE="SRFlipFlop";

    public SRFlipFlop(Context context , EditorLayout layout){
        super(context , layout , 2, 2, 0,1);
        super.gateName="SR Flip Flop";
        super.gateType=GATE_TYPE;

        pins[0].setPinName("R");
        pins[1].setPinName("S");

        opin[0].setPinName("Q'");
        opin[1].setPinName("Q");

        bpins[0].setPinName("clk");

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
        canvas.drawText("SR",ax,ay+50,p);
        p.setTextSize(20);
        canvas.drawText("Flip Flop",ax,ay+110,p);
    }


    @Override
    public void Logic(){

        //if both S and R  pins become  true then do nothing
        if(pins[0].getValue() && pins[1].getValue()){
            return;
        }

        if(!bpins[0].getValue())
            return;

        boolean changed=false;
        // if R is true
        if(pins[0].getValue()){
            opin[0].setValue(true);
            opin[1].setValue(false);
            changed=true;
        }

        // if S is true
        if(pins[1].getValue()){
            opin[0].setValue(false);
            opin[1].setValue(true);
            changed=true;
        }

        if(changed) {
            opin[0].forwardValue();
            opin[1].forwardValue();
        }

    }//end of Logic
}
