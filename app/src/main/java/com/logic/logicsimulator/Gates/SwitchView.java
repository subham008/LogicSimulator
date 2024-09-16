package com.logic.logicsimulator.Gates;
//version 1
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

public class SwitchView extends BasicGateView{

	public static final String GATE_TYPE="Switch";
	public SwitchView(Context context, EditorLayout editor){
		  super(context,editor,0,1,0,0);
		  super.gateName="Switch";
		  super.gateType=GATE_TYPE;
		  opin[0].setValue(false);
		  opin[0].pinSignalType=Pin.OUTPUT;
		}
	
	int circleColor=0xffffffff;
		@Override	 
	public void drawicon( RectF r , Canvas canvas,Paint p ){
		int innerRadius=50;
		int outerRadius=60;		
	    float  ax=r.left+r.width()/2;
		float ay=r.top+r.height()/2;
					    
	   p.setStyle(Paint.Style.FILL);
	   p.setColor(circleColor);	     
		  canvas.drawCircle(ax,ay,innerRadius,p);
	   
	   p.setStyle(Paint.Style.STROKE);
	   p.setStrokeWidth(6);
	   p.setColor(0xffffffff);
	   canvas.drawCircle(ax,ay,outerRadius,p); 
			 			 	   
		}
	
	 @Override
  public boolean onTouchEvent(MotionEvent event){
	  super.onTouchEvent(event);
	     switch (event.getAction()){
			 
			 
			 case MotionEvent.ACTION_UP:
			 if(super.parent_layout.getMode() == EditorLayout.TOUCH_MODE){
				 if(circleColor==0xffff2222){					 
			          circleColor=0xffffffff;
					  opin[0].setValue(false);
					  }
				 else{
					 circleColor=0xffff2222;
					  opin[0].setValue(true);
					 }		 
			  
			  opin[0].forwardValue();
			  invalidate();	
			  super.parent_layout.TriggerCircuit();
			  } 
			 // parent_layout.invalidate();
			 break;//end of ACTION_UP
			 }
	
	return true;
	 } 	
	 
	 
		
		
}