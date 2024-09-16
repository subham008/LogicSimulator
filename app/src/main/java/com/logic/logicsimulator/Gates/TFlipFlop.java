package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;

public class TFlipFlop extends BasicGateView {
    public static final String GATE_TYPE="TFlipFlop";
    public TFlipFlop(Context context , EditorLayout layout){
        super(context ,layout ,2,2,1,1);
        super.gateName="T Flip Flop";
        super.gateType=GATE_TYPE;


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
        canvas.drawText("T",ax,ay+50,p);
        p.setTextSize(20);
        canvas.drawText("Flip Flop",ax,ay+110,p);
    }


    @Override
    public void Logic(){
        if(!pins[1].getValue())
            return;

        if(pins[0].getValue()){
            opin[0].setValue(!opin[0].getValue());
            opin[1].setValue(!opin[1].getValue());

            opin[0].forwardValue();
            opin[1].forwardValue();
        }
    }
}
