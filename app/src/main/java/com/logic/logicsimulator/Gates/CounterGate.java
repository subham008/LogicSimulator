package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

public class CounterGate extends BasicGateView {
    public static final String GATE_TYPE="Counter";

    private int couner_size;
    private int MAX;
    public CounterGate(Context context , EditorLayout layout , int bits){
        super(context,layout,1, 0 , 0 , bits);
        super.gateName=bits+"-bit Counter";
        super.gateType=GATE_TYPE;

        this.couner_size=bits;
        this.MAX=(int)Math.pow(2,couner_size)-1;
        pins[0].setPinName("clk");

        for(int i=0; i<bpins.length;i++){
            bpins[i].setPinName(String.valueOf(i));
            bpins[i].pinSignalType= Pin.OUTPUT;
        }
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
        canvas.drawText(couner_size+"-bit",ax,ay+50,p);
        p.setTextSize(20);
        canvas.drawText("Counter",ax,ay+110,p);
    }


    int value=0;

    @Override
    public void Logic(){
        if(!pins[0].getValue())
            return;

        value++;
        if(value>MAX)
            value=0;

        boolean[] binary=intToBinary(value);
        for(int i=0;i<bpins.length;i++){
            bpins[i].setValue(binary[i]);
        }

        for(Pin p:bpins)
            p.forwardValue();
    }


    public static boolean[] intToBinary(int number) {
        // Calculate the number of bits needed for the binary representation
        int numBits = Integer.SIZE;

        // Create an array to hold the binary representation
        boolean[] binaryArray = new boolean[numBits];

        // Loop through each bit position
        for (int i = numBits - 1; i >= 0; i--) {
            // Check if the current bit is set
            binaryArray[i] = (number & (1 << i)) != 0;
        }

        return binaryArray;
    }

}
