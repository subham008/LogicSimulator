package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;

public class SRlatch extends BasicGateView {
    public static final String GATE_TYPE="SRlatch";
    public SRlatch(Context context , EditorLayout editorLayout){
        super(context , editorLayout , 2,2,0,0);
        super.gateType=GATE_TYPE;
        super.gateName="SRlatch";

        pins[0].setPinName("R");
        pins[1].setPinName("S");

        opin[0].setPinName("Q'");
        opin[1].setPinName("Q");

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
        p.setTextSize(35);
        canvas.drawText("latch",ax,ay+110,p);
    }



    @Override
    public void Logic(){

        //if both S and R  pins become  true then do nothing
        if(pins[0].getValue() && pins[1].getValue()){
            return;
        }

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
}// end of SR latch
