package com.logic.logicsimulator.Gates;
//version 1
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

public class BufferGateView extends BasicGateView  {
	public static final String GATE_TYPE="BufferGate";
	public BufferGateView(Context context, EditorLayout editor){
		    super(context,editor,1,1,0,0 );
			super.gateName="Buffer";
			super.gateType=GATE_TYPE;

			pins[0].pinSignalType=Pin.INPUT;
			opin[0].pinSignalType=Pin.OUTPUT;
			
		 }
	@Override	 
	public void drawicon( RectF r , Canvas canvas,Paint p ){
		   int width=80;
		int height=90;
		Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);				
	    int ax=re.left+(re.width()-(width))/2;
       int ay=re.top+(re.height() - height)/2;
	   
	   p.setStrokeWidth(6);
	   p.setColor(0xffffffff);
	   p.setStyle(Paint.Style.STROKE);	     
		  canvas.drawLine(ax, ay, ax, ay+height,p);
           canvas.drawLine(ax, ay, ax+width, ay+(height/2),p);
           canvas.drawLine(ax, ay+height, ax+width, ay+(height/2),p);
	   
	  	   
		}
   
  boolean BufferValue=false;	
  @Override	
	public void Logic(){
		
		if(pins[0].getValue())
				BufferValue=true;
		else 
		   BufferValue=false;
		   
		 opin[0].setValue(BufferValue);
		 opin[0].forwardValue();
		
		}	
		
}