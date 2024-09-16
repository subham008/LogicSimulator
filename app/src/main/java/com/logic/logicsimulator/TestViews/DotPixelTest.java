package com.logic.logicsimulator.TestViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DotPixelTest extends View {

    Paint p;
    RectF r;
    public DotPixelTest(Context context){
        super(context);
        p=new Paint();
        r=new RectF();
        r.set(0,0,getWidth(),getHeight());
    }

    public DotPixelTest(Context context , AttributeSet attrs){
        super(context,attrs);
        p=new Paint();
        r=new RectF();
        r.set(0,0,getWidth(),getHeight());
    }

    public DotPixelTest(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        p=new Paint();
        r=new RectF();
        r.set(0,0,getWidth(),getHeight());
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int unit_width=30;
        int unit_height=30;
        float sx=getLeft()+(getRight()-(unit_width*5f))/2;
        float sy=getTop()+(getBottom()-(unit_height*7f))/2;

        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                if(true )
                    p.setColor(Color.GREEN);
                else
                    p.setColor(Color.WHITE);
                float left= (unit_width*j)+sx+(20f*j);
                float top=(unit_height*i)+sy+(20f*i);

                canvas.drawRect(left ,top,left+unit_width,top+unit_height,p);
            }
        }//end of for

    }//end of onDraw

}
