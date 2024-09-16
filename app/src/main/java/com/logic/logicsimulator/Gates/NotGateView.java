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

public class NotGateView extends BasicGateView  {

	public static final String GATE_TYPE="Notgate";
	public NotGateView(Context context,EditorLayout editor,int inputCount){
		    super(context,editor, 1 ,1,0,0);
			super.gateName="Not";
			super.gateType=GATE_TYPE;
			opin[0].setValue(true);
			pins[0].setValue(true);
			
			opin[0].pinSignalType=Pin.OUTPUT;
			pins[0].pinSignalType=Pin.INPUT;
			
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
		   p.setStyle(Paint.Style.FILL);		
            canvas.drawCircle(ax+width+6,ay+((height-10)/2)+1, 10, p);    
	   
	  	   
		}
	
	@Override
	public void Logic(){
				
		if(pins[0].getValue()){
			opin[0].setValue(false);
			opin[0].forwardValue();
		}
		else {
			opin[0].setValue(true);
			opin[0].forwardValue();
		}
	}//end of logic
		
}