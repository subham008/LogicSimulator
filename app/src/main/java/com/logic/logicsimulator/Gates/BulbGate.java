package com.logic.logicsimulator.Gates;
//version 1
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Toast;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;
import com.logic.logicsimulator.Customizable;


public class BulbGate extends BasicGateView{
	
public int onColor=0xFFFFE52C;
	public static final String GATE_TYPE="BulbGate";
	public BulbGate(Context context, EditorLayout editor){
		  super(context , editor , 1,0,0,0);
		  super.gateName="Bulb";
		  super.gateType=GATE_TYPE;
		  super.GATE_TYPE=BasicGateView.OUTPUT_GATE;

		  pins[0].setValue(false);
		  pins[0].pinSignalType=Pin.INPUT;

		customizableList=makeCustomizable();
		}
		
	int circleColor=0xffffffff;
	@Override
	public void drawicon( RectF r , Canvas canvas,Paint p ){
		
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
	    circleColor=onColor;//default yellow color
	  else
	    circleColor=0xffffffff;//white color
		
		invalidate();
	  if(parent_layout.DEBUG_MODE)
	      System.out.println("**********Bulb Logic called ,value:"+pins[0].getValue());
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