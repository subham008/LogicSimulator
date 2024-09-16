package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.Toast;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.Customizable;
import com.logic.logicsimulator.EditorLayout;

public class Segment7Display extends BasicGateView {
    public static final String GATE_TYPE="Segment7Display";
    public Segment7Display(Context context , EditorLayout layout){
        super(context , layout,7,0,0,0);
        super.gateName="Segment 7 Display";
        super.gateType=GATE_TYPE;
        super.GATE_TYPE=BasicGateView.OUTPUT_GATE;

        for(int i=0; i<pins.length; i++){
            pins[i].setPinName( String.valueOf( (char)(65+i) ) );
        }

        customizableList=makeCustomizable();
    }


    private  int onColor=Color.GREEN;
    @Override
    public void drawicon(RectF r, Canvas canvas, Paint p) {

        float segmentWidth = 20f;
        float segmentHeight = 120f;
        float sx = getLeft()+(getWidth()-(segmentHeight+10))/2;
        float sy = getTop()+(getHeight()-((2*segmentHeight)+segmentWidth))/2;

        p.setStyle(Paint.Style.FILL);

        if (pins[0].getValue())
            p.setColor(onColor);
        else
            p.setColor(Color.BLACK);
        //canvas.drawRoundRect(sx + 5, sy, segmentHeight, segmentWidth, 5, 5);
        canvas.drawRoundRect(sx + 5f,sy,sx+segmentHeight,sy+segmentWidth,5,5,p);

        if (pins[1].getValue())
            p.setColor(onColor);
        else
            p.setColor(Color.BLACK);

        //g2d.fillRoundRect(sx + segmentHeight, sy + segmentWidth + 2, segmentWidth, segmentHeight, 5, 5);
        canvas.drawRoundRect(sx + segmentHeight,sy+segmentWidth+2,sx+segmentHeight+segmentWidth,sy+segmentHeight,5,5,p);


        if (pins[2].getValue())
            p.setColor(onColor);
        else
            p.setColor(Color.BLACK);

        // g2d.fillRoundRect(sx + segmentHeight, sy + segmentWidth + segmentHeight + 4 + segmentWidth, segmentWidth,
        //                segmentHeight, 5, 5);
        canvas.drawRoundRect(sx + segmentHeight,sy+segmentWidth+ segmentHeight+4+segmentWidth,sx+segmentHeight+segmentWidth,sy+segmentHeight+segmentHeight+segmentWidth,5,5,p);


        if (pins[3].getValue())
            p.setColor(onColor);
        else
            p.setColor(Color.BLACK);
        //g2d.fillRoundRect(sx + 5, sy + segmentWidth + segmentHeight + segmentHeight + 6 + segmentWidth, segmentHeight,
        //                segmentWidth, 5, 5);

        canvas.drawRoundRect(sx +5,sy+segmentWidth+segmentHeight+segmentWidth+segmentHeight,sx+segmentHeight,sy+segmentWidth+segmentHeight+segmentHeight,5,5,p);

        if (pins[4].getValue())
            p.setColor(onColor);
        else
            p.setColor(Color.BLACK);
        //g2d.fillRoundRect(sx, sy + segmentWidth + segmentHeight + 4 + segmentWidth, segmentWidth,
        //                segmentHeight, 5, 5);
        canvas.drawRoundRect(sx-10f,sy + segmentWidth + segmentHeight +4+ segmentWidth ,sx+segmentWidth-10f,sy+segmentHeight+segmentHeight+segmentWidth,5,5,p);


        if (pins[5].getValue())
            p.setColor(onColor);
        else
            p.setColor(Color.BLACK);
        //g2d.fillRoundRect(sx, sy + segmentWidth + 2, segmentWidth, segmentHeight, 5, 5);
        canvas.drawRoundRect(sx-10f,sy + segmentWidth + 2 ,sx+segmentWidth-10f,sy+segmentHeight,5,5,p);


        if (pins[6].getValue())
            p.setColor(onColor);
        else
            p.setColor(Color.BLACK);

        //g2d.fillRoundRect(sx + 5, sy + segmentWidth + segmentHeight + 2, segmentHeight, segmentWidth, 5, 5);
        canvas.drawRoundRect(sx + 5f,sy+segmentWidth+segmentHeight-10f,sx+segmentHeight,sy+segmentWidth+segmentHeight+segmentWidth-10f,5,5,p);


    }


    @Override
    public void Logic(){
        super.invalidate();
    }

    protected  Customizable[] makeCustomizable(){

        Customizable customizable[]=new Customizable[1];

        customizable[0]=new Customizable(Customizable.MAKE_TEXT_EDITABLE , "Bulb Color"){

            @Override
            public void onChangeButton(boolean toggle,boolean increase,int seek, String text){

                try {
                    onColor= Color.parseColor(text);
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "invalid color", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public DataGroup retrieveData(){
                DataGroup data=new DataGroup();
                data.text= "#"+Integer.toHexString(onColor).substring(2);
                return data;
            }
        };


        return customizable;
    }
}
