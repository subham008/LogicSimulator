package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.Customizable;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;
import com.logic.logicsimulator.R;

public class TestGate extends  BasicGateView{

    public static final String GATE_TYPE="TestGate";
    public TestGate(Context context,EditorLayout editorLayout ){
         super(context,editorLayout,3,3,1,5);
         super.gateName="TestGate";
         super.gateType=GATE_TYPE;

         for(Pin p:pins)
             p.pinSignalType=Pin.INPUT;
         for(Pin p:opin)
             p.pinSignalType=Pin.INPUT;
         for(Pin p:bpins)
             p.pinSignalType=Pin.OUTPUT;

         super.customizableList=makeCustomizable();

         icon=getResources().getDrawable(R.drawable.delete_icon,null);
         icon.setTint(0xffffffff);
     }

     private static Drawable icon;
    @Override
    public void drawicon(RectF r , Canvas canvas, Paint p ){
        Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);
        int ax=re.left+((re.width()-50)/2); //center x
        int ay=re.top+((re.height()-70) /2);//center y

          icon.setBounds(ax,ay,50,70);
          icon.draw(canvas);
    }

    int testdata=90;
    public Customizable[] makeCustomizable(){
         Customizable customizable[]=new Customizable[3];

         customizable[0]=new Customizable(Customizable.MAKE_NUMBER_EDITABLE , "Input pins"){
             @Override
             public void onChangeButton(boolean toggle,boolean increase,int seek, String text){
                  testdata=seek;
                  System.err.println("TestGate data received : "+seek);
             }

             @Override
             public DataGroup retrieveData(){
                 DataGroup data=new DataGroup();
                 data.number=testdata;
                 return data;
             }
         };

        customizable[1]=new Customizable(Customizable.MAKE_TOGGLEABLE, "running"){
            @Override
            public void onChangeButton(boolean toggle,boolean increase,int seek, String text){
                System.err.println("TestGate toggle received : "+toggle);
            }

            @Override
            public DataGroup retrieveData(){
                DataGroup data=new DataGroup();
                data.state=true;
                return data;
            }
        };

        customizable[2]=new Customizable(Customizable.MAKE_SEEKABLE, "speed"){
             @Override
             public void onChangeButton(boolean toggle,boolean increase,int seek, String text){
                 System.err.println("TestGate seeekable received : "+seek);
             }

             @Override
             public DataGroup retrieveData(){
                 DataGroup data=new DataGroup();
                 data.number=testdata;
                 data.max=100;
                 return data;
             }
         };


        return customizable;
    }

}
