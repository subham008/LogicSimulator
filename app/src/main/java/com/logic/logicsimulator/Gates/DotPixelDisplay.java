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
import com.logic.logicsimulator.Pin;

public class DotPixelDisplay extends BasicGateView {

    public static final String GATE_TYPE="Dotpixel";
    public DotPixelDisplay(Context context  , EditorLayout layout){
        super(context , layout , 7,0,5,0);
        super.gateName="Dot Pixel Display";
        super.gateType=GATE_TYPE;
        super.GATE_TYPE=BasicGateView.OUTPUT_GATE;

        for (Pin p:tpins)
            p.pinSignalType=Pin.INPUT;

        for(int i=0; i<pins.length; i++){
            pins[i].setPinName("X"+String.valueOf(i));
        }

        for(int i=0; i<tpins.length; i++){
            tpins[i].setPinName("Y"+String.valueOf(i));
        }


        customizableList=makeCustomizable();

    }//end of constructer



    private int onColor=Color.GREEN;

    @Override
    public void drawicon(RectF r, Canvas canvas, Paint p) {
       int unit_width=30;
       int unit_height=30;
        float sx=getLeft()+(getRight()-(unit_width*5f))/2;
        float sy=getTop()+(getBottom()-(unit_height*7f))/2;
        sy=sy-80f; sx=sx-40f;

       for(int i=0; i<pins.length; i++){
           for(int j=0; j<tpins.length; j++){
               if(pins[i].getValue()  && tpins[j].getValue())
                  p.setColor(onColor);
               else
                  p.setColor(Color.WHITE);

               float left= (unit_width*j)+sx+(20f*j);
               float top=(unit_height*i)+sy+(20f*i);

               canvas.drawRect(left ,top,left+unit_width,top+unit_height,p);
           }// end of j loop
       }//end of i loop
    }// end of draw_icon function


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
}//end of class
