package com.logic.logicsimulator.Gates;
//version 1
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

public class PushButton extends BasicGateView {

	public static final String GATE_TYPE="PushGate";
	public PushButton(Context context, EditorLayout editor){
		  super(context , editor , 0,1,0,0);
		  super.gateName="PushButton";
		  super.gateType=GATE_TYPE;

		  super.GATE_TYPE=BasicGateView.INPUT_GATE;
		  opin[0].setValue(false);
		  opin[0].pinSignalType=Pin.OUTPUT;
		}
	
	int circleColor=0xffffffff;
	@Override
	public void drawicon( RectF r , Canvas canvas,Paint p ){
		
		Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);
		int ax=re.left+(re.width()/2);
		int ay=re.top+(re.height()/2);
		p.setStyle(Paint.Style.FILL);
		p.setColor(circleColor);
	
	    canvas.drawCircle(ax,ay,80,p);	
		
	}
	
  @Override
  public boolean onTouchEvent(MotionEvent event){
	  super.onTouchEvent(event);
	     switch (event.getAction()){
			 case MotionEvent.ACTION_DOWN:
			  if(super.parent_layout.getMode()  == EditorLayout.TOUCH_MODE){					  
			  circleColor=0xffff2222;
			  invalidate();
			  opin[0].setValue(true);
			 opin[0].forwardValue();
			 // super.parent_layout.TriggerCircuit();
			  }
			  //parent_layout.invalidate();			 
			 break ;//end of ACTION_DOWN
			 
			 case MotionEvent.ACTION_UP:
			 if(super.parent_layout.getMode() == EditorLayout.TOUCH_MODE){
					 
			   circleColor=0xffffffff;
			  invalidate();
			  opin[0].setValue(false);
			 opin[0].forwardValue();	
			 // super.parent_layout.TriggerCircuit();
			  } 
			 // parent_layout.invalidate();
			 break;//end of ACTION_UP
			 }
	
	return true;
	 } 	
  
}