package com.logic.logicsimulator.TestViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class segment14Test extends View {

    Paint p;
    RectF r;
    public segment14Test(Context context){
        super(context);
        p=new Paint();
        r=new RectF();
        r.set(0,0,getWidth(),getHeight());
    }

    public segment14Test(Context context , AttributeSet attrs){
        super(context,attrs);
        p=new Paint();
        r=new RectF();
        r.set(0,0,getWidth(),getHeight());
    }

    public segment14Test(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        p=new Paint();
        r=new RectF();
        r.set(0,0,getWidth(),getHeight());
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float segmentWidth = 20f;
        float segmentHeight = 120f;
        float sx = getLeft()+(getWidth()-(segmentHeight+10))/2;
        float sy = getTop()+(getHeight()-((2*segmentHeight)+segmentWidth))/2;

        p.setStyle(Paint.Style.FILL);

        if (true)
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);
        //canvas.drawRoundRect(sx + 5, sy, segmentHeight, segmentWidth, 5, 5);
        canvas.drawRoundRect(sx + 5f,sy,sx+segmentHeight,sy+segmentWidth,5,5,p);

        if (true)
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);

        //g2d.fillRoundRect(sx + segmentHeight, sy + segmentWidth + 2, segmentWidth, segmentHeight, 5, 5);
        canvas.drawRoundRect(sx + segmentHeight,sy+segmentWidth+2,sx+segmentHeight+segmentWidth,sy+segmentHeight,5,5,p);


        if (true)
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);

        // g2d.fillRoundRect(sx + segmentHeight, sy + segmentWidth + segmentHeight + 4 + segmentWidth, segmentWidth,
        //                segmentHeight, 5, 5);
        canvas.drawRoundRect(sx + segmentHeight,sy+segmentWidth+ segmentHeight+4+segmentWidth,sx+segmentHeight+segmentWidth,sy+segmentHeight+segmentHeight+segmentWidth,5,5,p);


        if (true)
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);
        //g2d.fillRoundRect(sx + 5, sy + segmentWidth + segmentHeight + segmentHeight + 6 + segmentWidth, segmentHeight,
        //                segmentWidth, 5, 5);

        canvas.drawRoundRect(sx +5,sy+segmentWidth+segmentHeight+segmentWidth+segmentHeight,sx+segmentHeight,sy+segmentWidth+segmentHeight+segmentHeight,5,5,p);

        if (true)
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);
        //g2d.fillRoundRect(sx, sy + segmentWidth + segmentHeight + 4 + segmentWidth, segmentWidth,
        //                segmentHeight, 5, 5);
        canvas.drawRoundRect(sx-10f,sy + segmentWidth + segmentHeight +4+ segmentWidth ,sx+segmentWidth-10f,sy+segmentHeight+segmentHeight+segmentWidth,5,5,p);


        if (true)
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);
        //g2d.fillRoundRect(sx, sy + segmentWidth + 2, segmentWidth, segmentHeight, 5, 5);
        canvas.drawRoundRect(sx-10f,sy + segmentWidth + 2 ,sx+segmentWidth-10f,sy+segmentHeight,5,5,p);


        if (true) // 6
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);

        //g2d.fillRoundRect(sx + 5, sy + segmentWidth + segmentHeight + 2, segmentHeight, segmentWidth, 5, 5);
        canvas.drawRoundRect(sx + 5f,sy+segmentWidth+segmentHeight-10f,sx+(segmentHeight/2),sy+segmentWidth+segmentHeight+segmentWidth-10f,5,5,p);


        if (true) // 7
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);

        //g2d.fillRoundRect(sx + 5, sy + segmentWidth + segmentHeight + 2, segmentHeight, segmentWidth, 5, 5);
        canvas.drawRoundRect(sx + 5f+(segmentHeight/2),sy+segmentWidth+segmentHeight-10f,sx+segmentHeight,sy+segmentWidth+segmentHeight+segmentWidth-10f,5,5,p);

        if (true) // bottom pins 0
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);

        canvas.drawRoundRect(sx+(segmentHeight/2)-5f,sy + segmentWidth + 2 ,sx+(segmentHeight/2)+segmentWidth,sy+segmentHeight,5,5,p);

        if (true) // bottom pins 1
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.BLACK);

        canvas.drawRoundRect(sx+(segmentHeight/2)+5f,sy + segmentWidth + segmentHeight +4+ segmentWidth ,sx+(segmentHeight/2)+segmentWidth,sy+segmentHeight+segmentHeight+segmentWidth,5,5,p);

    }//end of onDraw
}
