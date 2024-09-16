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

public class AndGateView extends BasicGateView  {
	 
	 
	public static final String GATE_TYPE="AndGate";
	 public AndGateView(Context context,EditorLayout editor,  int inputCount){
		    super(context,editor, inputCount,1,0,0 );
			super.gateName="And";
			super.gateType=GATE_TYPE;

			for(int i=0;i<pins.length;i++){
				pins[i].pinSignalType=Pin.INPUT;
				pins[i].setPinName('I'+String.valueOf(i));
			}
			
			opin[0].pinSignalType=Pin.OUTPUT;
					
		    
		 }
		 
		 
	@Override	 
	public void drawicon( RectF r , Canvas canvas,Paint p ){
		   int width=80;
		int height=90;
		Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);				
	    int ax=re.left+(re.width()-(width))/2;
       int ay=re.top+(re.height() - height)/2;
	   p.setStyle(Paint.Style.STROKE);
	   p.setColor(0xffffffff);
	   p.setStrokeWidth(6);
	     
		    canvas.drawArc(ax,ay,ax+width,ay+height,90,-180,false,p);
			 canvas.drawLine(ax,ay,ax+(width/2),ay, p);		
			 canvas.drawLine(ax,ay+height,ax+(width/2),ay+height, p);  
			 canvas.drawLine(ax,ay,ax,ay+height, p);
	   	  	   
		}
		
		
  @Override	
	public void Logic(){
		   int total_input_pins=super.pins.length;
		   int count=0;
		   for(Pin p:pins){
			     if(p.getValue())
						 count++;
			   }
			   
		    if(count==total_input_pins){
					opin[0].setValue(true);
					opin[0].forwardValue();
					}
			else{
			      opin[0].setValue(false);
				  opin[0].forwardValue();
				  }
				  
		}	
		
}