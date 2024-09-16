package com.logic.logicsimulator.Gates;
//version 1
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

public class HighConstantView extends BasicGateView {

	public static final String GATE_TYPE="HighContant";
	public HighConstantView(Context context , EditorLayout editor){
		    super(context,editor,0,1,0,0);
			super.gateName="High Constant";
			super.gateType=GATE_TYPE;

			super.GATE_TYPE=BasicGateView.INPUT_GATE;
		  opin[0].setValue(true);
		  opin[0].pinSignalType=Pin.OUTPUT;
		}
		
    	@Override	 
	public void drawicon( RectF r , Canvas canvas,Paint p ){
		 int width=200;
		int height=200;
	    float  ax=r.left+((r.width()-width)/2);
		float ay=r.top+((r.height()-height)/2);	    
	   p.setStyle(Paint.Style.FILL);
	   p.setColor(0xffff2222);
	     
		  canvas.drawRect(ax,ay,ax+width,ay+height,p);
	   	  	   
		}
		
		
   @Override		
	public void Logic(){
		    opin[0].setValue(true);
			opin[0].forwardValue();
		}
	
}