package com.logic.logicsimulator.Gates;
//version 1
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Timer;
import java.util.TimerTask;
import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;
import com.logic.logicsimulator.Customizable;

public class FrequencyGeneratorView extends BasicGateView {

	public static final String GATE_TYPE="FrequencyGenerator";
	Timer timer=new Timer();
	  public int frequency=2;
	  public FrequencyGeneratorView(Context context, EditorLayout editor){
		      super(context,editor,0,1,0,0);
			 super.gateName="Frequency Generator";
			 super.gateType=GATE_TYPE;

			 opin[0].pinSignalType=Pin.OUTPUT;
			 super.customizableList=this.makeCustomizable();
			 timer.scheduleAtFixedRate(new TimerTask(){
				 @Override
				 public void run(){
					    if(opin[0].getValue())
					     opin[0].setValue(false);
						else 
						  opin[0].setValue(true);
						 
						 opin[0].forwardValue();

					 }
				 
				 },0,1000/(2*frequency));
		  }//end of constructer
		  
	 	@Override	 
	public void drawicon( RectF r , Canvas canvas,Paint p ){
		int width=200;
		int height=200;
	    float  ax=r.left+((r.width()-width)/2);
		float ay=r.top+((r.height()-height)/2);	    
	   p.setStyle(Paint.Style.FILL);
	   p.setColor(0xffff2222);
	   canvas.drawRect(ax,ay,90,90,p);
	   	  	   
		}
   
   public void setFrequecy(int fr){
		  if(fr<=0)
			  fr=1;

		  frequency=fr;
   timer.scheduleAtFixedRate(new TimerTask(){
				 @Override
				 public void run(){
					    if(opin[0].getValue())
					     opin[0].setValue(false);
						else 
						  opin[0].setValue(true);
						 
						 opin[0].forwardValue();
						 
						 parent_layout.invalidate();
					 }
				 
				 },0,1000/(fr*2));
	   
	   }
	   
   protected  Customizable[] makeCustomizable(){
	     
		 Customizable customizable[]=new Customizable[1];
		 
		 customizable[0]=new Customizable(Customizable.MAKE_CHANGEABLE , "Frequency"){
			     
				 @Override
				 public void onChangeButton(boolean toggle,boolean increase,int seek, String text){
		             	setFrequecy(seek);
		  		   }
				  
				  @Override
				  public DataGroup retrieveData(){
		                  DataGroup data=new DataGroup();
						  data.number=frequency;
		               return data;
		             }
			 };

			return customizable;
	 }//end of makeCustomizable
	 		
 
}