package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;

public class RGBlight extends BasicGateView {

    public static final String GATE_TYPE="rgblight";
    public RGBlight(Context context , EditorLayout layout){
        super(context , layout , 3, 0,0,0 );
        super.gateName=GATE_TYPE;
        super.gateType=GATE_TYPE;

        pins[0].setPinName("R"); pins[1].setPinName("G"); pins[2].setPinName("B");
    }

   private int circleColor=0xff000000;


    @Override
    public void drawicon(RectF r , Canvas canvas, Paint p){
        Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);
        int ax=re.left+(re.width()/2);
        int ay=re.top+(re.height()/2);
        p.setStyle(Paint.Style.FILL);

        p.setColor(circleColor);
        canvas.drawCircle(ax,ay,90,p);
    }

    @Override
    public void Logic(){

        if(pins[0].getValue())
            this.circleColor=circleColor | 0xffff0000;
        else
            this.circleColor=circleColor & 0xff00ffff;


        if(pins[1].getValue())
            this.circleColor=circleColor | 0xff00ff00;
        else
            this.circleColor=circleColor & 0xffff00ff;


        if(pins[2].getValue())
            this.circleColor=circleColor | 0xff0000ff;
        else
            this.circleColor=circleColor & 0xffffff00;


        this.invalidate();
    }
}
