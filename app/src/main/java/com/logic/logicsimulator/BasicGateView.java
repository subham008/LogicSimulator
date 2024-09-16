package com.logic.logicsimulator;
//version 3
// initialized customizable array to null,and if both connecting pins have same parent and have different signal type
//changes are committed in onTouchEvent(..)
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.parsers.ConnectionElement;
import com.logic.logicsimulator.parsers.GateElement;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class BasicGateView extends View {
	
	public static int INPUT_GATE = 20;
	public static int OUTPUT_GATE = 21;
	public static int INTEGRATED_CIRCUIT = 22;
	
	public int GATE_TYPE=INTEGRATED_CIRCUIT;
	public String gateType="BasicGate";
    public String gateName="BasicGate";

    public	Pin pins[]; int pins_type;
    public	Pin opin[]; int opin_type;
    public    Pin tpins[]; int tpins_type;
    public	Pin bpins[]; int bpins_type;

	RectF srect;
	Paint paint;
	public final int pinWidth=55;//width if pin
	public final int pinHeight=60;//height of pins//previous 50
	public final int minWidth=480;// minimum width of gate
	public final int minHeight=450;//minimum height of gate
	
public static BasicGateView selected_view=null;
public static Pin selected_pin=null;
public static int selected_pinType;
public EditorLayout parent_layout=null;

public static BasicTask SelecetViewChangeTask;
public static BasicTask SelectPinChangeTask;

private GateElement element;
private Document document;
private String gateID;

public void setGateID(@NonNull String id){
	gateID=id;
}

public String getGateID(){
	return gateID;
}

public void setElement(@NonNull  GateElement el ,@NonNull Document document){
	this.element=el;
    this.document=document;

	this.gateName=el.getName();
	this.setX((float) Integer.parseInt(el.getxLoc()));
	this.setY((float) Integer.parseInt(el.getyLoc()) );

	if(pins.length>0){
			for(int i=0; i<this.element.getLeftPinsCount(); i++){
				pins[i].setElement(this.element.getLeftPinElements().get(i));
			}
	}


	if(opin.length>0){
		for(int i=0; i<this.element.getRightPinsCount(); i++){
			opin[i].setElement(this.element.getRightPinElements().get(i));
		}
	}


	if(tpins.length>0){
		for(int i=0; i<this.element.getTopPinsCount(); i++){
			tpins[i].setElement(this.element.getTopPinElements().get(i));
		}
	}


	if(bpins.length>0){
		for(int i=0; i<this.element.getBottomPinsCount(); i++){
			bpins[i].setElement(this.element.getBottomPinElement().get(i));
		}
	}//end of bpins if

}//end of setElememt

public GateElement getElement(){

	return  this.element;
}


public GateElement updateElement(){
	element.setName(gateName);
    element.setxLoc(String.valueOf((int)getX()));
	element.setyLoc(String.valueOf((int)getY()));
	return element;
}


public Customizable customizableList[]=null;

	public int Squarecolor=0xffffffff;
	public  BasicGateView(Context context,EditorLayout parentLayout, int inputCount,int outputCount,int topCount,int bottomCount){
		  super(context);
		  parent_layout=parentLayout;
		  pins=new Pin[inputCount];
		  for(int i=0; i<inputCount; i++) {
			  pins[i] = new Pin(i,this);
			  pins[i].pinOreintation=Pin.LEFT_PIN;
			  pins[i].pinSignalType=Pin.INPUT;
			  pins[i].pinIndex=i;
		  }

		  opin=new Pin[outputCount];
		  for(int i=0; i<outputCount; i++) {
			  opin[i] = new Pin(i,this);
			  opin[i].pinOreintation=Pin.RIGHT_PIN;
			  opin[i].pinSignalType=Pin.OUTPUT;
			  opin[i].pinIndex=i;
		  }

		  tpins=new Pin[topCount];
		  for(int i=0; i<topCount; i++) {
			  tpins[i] = new Pin(i,this);
			  tpins[i].pinOreintation=Pin.TOP_PIN;
			  tpins[i].pinSignalType=Pin.OUTPUT;
			  tpins[i].pinIndex=i;
		  }

		  bpins=new Pin[bottomCount];
			   for(int i=0; i<bottomCount; i++) {
				   bpins[i] = new Pin(i,this);
				   bpins[i] .pinOreintation=Pin.BOTTOM_PIN;
				   bpins[i].pinSignalType=Pin.INPUT;
				   bpins[i].pinIndex=i;
			   }
		  //
		  int max_vpin= Math.max(inputCount,outputCount);
		  int max_hpin= Math.max(topCount,bottomCount);
		  
		  int vheight = minHeight ,vwidth = minWidth;
		  if(max_vpin > 4)
			  vheight += (max_vpin-4)*Math.max(pinHeight,pinWidth);
		  if(max_hpin >4) 
		      vwidth += (max_hpin-4)*Math.max(pinHeight,pinWidth);
			  	   	 		  
		   setLayoutParams(new ViewGroup.LayoutParams(vwidth,vheight));		  		   	  
				  
		}	

public int getLeftPinsCount(){
		if(pins!=null)
			return pins.length;
		return 0;
}

	public int getRightPinsCount(){
		if(opin!=null)
			return opin.length;
		return 0;
	}
	public int getTopPinsCount(){
		if(tpins!=null)
			return tpins.length;
		return 0;
	}
	public int getBottomPinsCount(){
		if(bpins!=null)
			return bpins.length;
		return 0;
	}


public void GateRotate(){
	   super.animate().rotation( super.getRotation()+90.0f).setDuration(100).start();
	   
	   for(Pin p:pins){
			  p.absX+=super.getRotationX(); p.absY+=super.getRotationY();
		}	  
		for(Pin p:bpins){
			  p.absX+=super.getRotationX(); p.absY+=super.getRotationY();
			 
			}
			
		for(Pin p: opin){
	        p.absX+=super.getRotationX(); p.absY+=super.getRotationY();
			
			}
		
		for(Pin p:tpins){
			 p.absX+=super.getRotationX(); p.absY+=super.getRotationY();
				
			}
	   		 	  	   
	}

			
    int viewWidth=320, viewheight=320;
     
	boolean  isSelected= false;
	  


public void selectGate(){
	   if(selected_view == null  && parent_layout.getMode()==EditorLayout.SELECT_MODE){
					 selected_view=this; 
					 Squarecolor=EditorLayout.selectColor; 
					 }
				 else if(selected_view != this   && parent_layout.getMode()==EditorLayout.SELECT_MODE){
				     selected_view.Squarecolor=0xffffffff;					 
					 selected_view.invalidate();
					 selected_view=this;
					 Squarecolor=EditorLayout.selectColor; 
					 
					 }//end of else if
	   
	}//end of selectGate
	
		 
		 
public void Logic(){
  //override logic
	}
	

	public Pin getPin(int orientation, int index){
		 Pin pin=null;
		 switch (orientation){
			 case Pin.LEFT_PIN:
				 pin=pins[index];
					break;
			 case Pin.RIGHT_PIN:
				 pin=opin[index];
				 break;
			 case Pin.TOP_PIN:
				 pin=tpins[index];
				 break;
			 case Pin.BOTTOM_PIN:
				 pin=bpins[index];
				 break;
		 }

		 return pin;
	}
	
public List<EditorLayout.ConnectionWire> gateConnectionsList=new ArrayList<>();
public  void setConnectionPoints(Pin from , int tf , Pin to , int tt ){
	if(from == null || to == null)
			return;
	
	Pin f=null,t=null;//f for output ,t for input
	
	//assigning output pin to f		
	if(from.pinSignalType==Pin.OUTPUT)
			f=from;
	else if(to.pinSignalType==Pin.OUTPUT)
			f=to;
						
	//assigning input pin to f
	if(from.pinSignalType==Pin.INPUT)
			t=from;
	else if(to.pinSignalType==Pin.INPUT)
			t=to;
	
	if( f == null || t == null)
		return;
    if(!t.ConnectedPin.isEmpty())
		return;
	
	//conecting f pin to t pin				
	
	f.ConnectedPin.add(t);
	t.ConnectedPin.add(f);
	
	f.addParent(t.parent);
   // t.addParent(f.parent);

	ConnectionElement connectionElement=f.element.addConnection(f,t,document);
	//creating connectionWire
	EditorLayout.ConnectionWire cw=new EditorLayout.ConnectionWire(f,t,connectionElement);
	
	//adding connectionWire dara to both Pin parent BasicGateView
	gateConnectionsList.add(cw);
	f.parent.gateConnectionsList.add(cw);
	
	
	f.connections.add(cw);
    t.connections.add(cw);
	
     
  parent_layout. connectionWires.add(cw);
  
 parent_layout.TriggerCircuit(); //replace it with Logic() in futire somehow

 if(parent_layout.changeConnectionTask!=null)
     parent_layout.changeConnectionTask.run();
  	
}


	public  boolean setPreConnection(Pin from , int tf , Pin to , int tt , ConnectionElement connectionElement ){
		if(from == null || to == null)
			return false;

		Pin f=null,t=null;//f for output ,t for input

		//assigning output pin to f
		if(from.pinSignalType==Pin.OUTPUT)
			f=from;
		else if(to.pinSignalType==Pin.OUTPUT)
			f=to;

		//assigning input pin to f
		if(from.pinSignalType==Pin.INPUT)
			t=from;
		else if(to.pinSignalType==Pin.INPUT)
			t=to;

		if(f == null || t == null)
			return false;
		if(!t.ConnectedPin.isEmpty())
			return false;

		//conecting f pin to t pin

		f.ConnectedPin.add(t);
		t.ConnectedPin.add(f);

		f.addParent(t.parent);


		//creating connectionWire
		EditorLayout.ConnectionWire cw=new EditorLayout.ConnectionWire(f,t,connectionElement);

		//adding connectionWire dara to both Pin parent BasicGateView
		gateConnectionsList.add(cw);
		f.parent.gateConnectionsList.add(cw);


		f.connections.add(cw);
		t.connections.add(cw);


		parent_layout. connectionWires.add(cw);

		parent_layout.TriggerCircuit(); //replace it with Logic() in futire somehow

		if(parent_layout.changeConnectionTask!=null)
			parent_layout.changeConnectionTask.run();

		return  true;
	}

	public  void drawicon( RectF r , Canvas canvas,Paint p){
	 p.setStyle(Paint.Style.STROKE);
	    int width=80;
		int height=90;
		Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);				
	    int ax=re.left+(re.width()-(width))/2;
       int ay=re.top+(re.height() - height)/2;
	   p.setStrokeWidth(6);
	     p.setStyle(Paint.Style.FILL);
		 
		canvas.drawCircle(ax, ay, 10, paint);
			 		   
		p.setStrokeWidth(10);
        		
		
	   }//end of drawicon
	   
	   
  protected void drawTitles(Canvas canvas){
	  //rendering left pins titles	  
	  int len=pins.length;
	  paint.setStyle(Paint.Style.FILL);
	  paint.setColor(0xffffffff);
	  paint.setTextSize(12);
	    int dwidth=(pinWidth - 10 )/2;
		
	  for(int i=0; i<len;i++){
		  int slen=pins[i].getPinName().length();
		  if(slen>0 && slen<=7)
		     canvas.drawText(pins[i].getPinName(),pins[i].x+pins[i].w+10, pins[i].y+dwidth,paint);
		  }//end of for 
		  
	  //rendering output pins
	  len = opin.length;
	  for(int i=0; i<len;i++){
		  int slen=opin[i].getPinName().length();
		  if(slen>0 && slen<=5)	  
		     canvas.drawText(opin[i].getPinName() ,opin[i].x-50, opin[i].y+dwidth ,paint);
		  }//end of for 
		
		//rendering top titles 
		len = tpins.length; 
	   for(int i=0; i<len;i++){
		   int slen=tpins[i].getPinName().length();
		  if(slen>0 && slen<=5)	  
		     canvas.drawText(tpins[i].getPinName(),tpins[i].x-20+dwidth, tpins[i].y+pinHeight+20 ,paint);
		  }//end of for 
	  
	  
	  //rendering bottom titles 
		len = bpins.length; 
	   for(int i=0; i<len;i++){
		   int slen= bpins[i].getPinName().length();
		  if(slen>0 && slen<=5)	  
		     canvas.drawText(bpins[i].getPinName(),bpins[i].x+dwidth, bpins[i].y-20 ,paint);
		  }//end of for 
	  	  
	  }	 //end of drawtitles  
	  
	  
	  	
  protected void onDraw(Canvas canvas){	 
	  super.onDraw(canvas);
	 	 
			 
	  if(paint==null)
	   paint= new Paint();
	
	
			
	  	  
	  paint.setStyle(Paint.Style.FILL);
	  //background
	 // paint.setColor(0xffff2222);
	// canvas.drawPaint(paint);
		  	  
	  paint.setColor(Squarecolor);
	  
	  int wpins = Math.max(tpins.length,bpins.length);
	  int hpins = Math.max(pins.length,opin.length);
	  if(wpins>4)
	   viewWidth = 320+ ((wpins - 4)*pinWidth);
	  if(hpins>4)	  	  	  
	   viewheight = 320+ ((hpins- 4)*pinWidth);
	   
	   
	  paint.setStyle(Paint.Style.STROKE);
	  float x= (canvas.getWidth()-viewWidth)/2;
	  float  y= (canvas.getHeight()-viewheight)/2;	   	   	    
	    srect = new RectF(x,y,x+viewWidth, y+viewheight);
		
		paint.setColor(Squarecolor);
	   paint.setStyle(Paint.Style.STROKE);	 	   	   	   	   
	   paint.setStrokeWidth(20);	   
	  canvas.drawRoundRect(srect, 20, 20, paint);
	  
	  
	   paint.setStyle(Paint.Style.FILL);
	   paint.setColor(0xff3d3d3d);
	   canvas.drawRoundRect(srect, 20, 20, paint);
	   	   	   	   	   
	   //rendering gate icon
	   drawicon(srect,canvas,paint);
	   	   	 	  	   
	   paint.setStyle(Paint.Style.FILL);
	   paint.setColor(0xffffffff);
	   paint.setStrokeWidth(10);	  
	  int nInput=pins.length;	  	 		  
	  int nOutput=opin.length;    		  		  
	  //rendering input lines
	  int starty=(canvas.getHeight() -(nInput*pinWidth))/2;
	  int dwidth=(pinWidth - 10 )/2;
	  int constantX=(int)srect.left-pinHeight+5;
	  
	  
	  pins_type=Pin.LEFT_PIN;
	  for(int i=0;i<nInput;i++){
		  Pin pin=pins[i];
		  pin.x=constantX; pin.y=starty+(i*pinWidth);
		  pin.w=pinHeight+10; pin.h=pinWidth;
		  pin.absX=super.getX()+20;	pin.absY=super.getY()+pin.y+dwidth; 	  
		  paint.setColor(0xffffffff);
	      canvas.drawLine( srect.left-pinHeight,pin.y+dwidth, srect.left, pin.y+dwidth, paint);

		  
		  if(pin == selected_pin)
		     paint.setColor(EditorLayout.selectColor);		  		  		  
		  else 
		     paint.setColor(pin.getDefaultColor());
			 
			  
		 if(parent_layout.getMode() == EditorLayout.CONNECTION_DELETE_MODE && pin.ConnectedPin.size()>0){
			 paint.setColor(0xffff2222);			 
			 canvas.drawCircle(srect.left-pinHeight+11 ,pin.y+dwidth ,22 , paint);
			 paint.setColor(0xffffffff);
			 canvas.drawLine( srect.left-pinHeight-4,pin.y+dwidth  , srect.left-pinHeight+26, pin.y+dwidth ,paint);
			 		      
			  }
		 else {
			 canvas.drawCircle(srect.left-pinHeight+5 ,pin.y+dwidth ,10 , paint);
			 
			 }
	  }

	  //rendering output lines
	  constantX=(int)srect.right;
	 starty =(canvas.getHeight()-(nOutput*pinWidth))/2;
	 opin_type=Pin.RIGHT_PIN;	  
	 for(int i=0;i<nOutput;i++){
		 Pin pin=opin[i];
		 pin.x=constantX; pin.y=starty+(i*pinWidth);
		  pin.w=pinHeight+10; pin.h=pinWidth;
		  pin.absX=super.getX()+getWidth()-25;  pin.absY=super.getY()+pin.y+dwidth;
		  paint.setColor(0xffffffff);
	      canvas.drawLine(pin.x, pin.y+dwidth,constantX+pinHeight,pin.y+dwidth, paint);

			 if(pin == selected_pin)
		     paint.setColor(EditorLayout.selectColor);		  		  		  
		  else 
		     paint.setColor(pin.getDefaultColor());
			 
		if(parent_layout.getMode()  == EditorLayout.CONNECTION_DELETE_MODE && pin.connections.size()>0 ){	
			paint.setColor(0xffff2222);			 
			 canvas.drawCircle(constantX+pinHeight-11 , pin.y+dwidth, 22 ,paint);
			 paint.setColor(0xffffffff);
			 canvas.drawLine( constantX+pinHeight-26,pin.y+dwidth  ,constantX+pinHeight+6, pin.y+dwidth ,paint);
			  		    
			}
		 else{
			 canvas.drawCircle(constantX+pinHeight-5 , pin.y+dwidth, 10 ,paint);
			 }
	  }	
	  
	  int nTop=tpins.length;
	  int nbott=bpins.length;
	  
	 int startx= (canvas.getWidth() -(nTop*pinWidth))/2;	 	 
	 //renderng top pins
	 tpins_type=Pin.TOP_PIN;
	 int constantY=(int)srect.top-pinHeight;	 
	 for(int i=0;i<nTop;i++){
		 Pin pin=tpins[i];
		 pin.x=startx+(i*pinWidth); pin.y=constantY;
		  pin.w=pinWidth; pin.h=pinHeight+10;
		  pin.absX=super.getX()+pin.x+dwidth;  pin.absY=super.getY()+5;
		  paint.setColor(0xffffffff);
		  canvas.drawLine(pin.x+dwidth,srect.top, pin.x+dwidth , srect.top-pinHeight,paint );
		 
		  
		  if(pin == selected_pin)
		    paint.setColor(EditorLayout.selectColor);
		  else
		  paint.setColor(pin.getDefaultColor());
		  
		  if(parent_layout.getMode() == EditorLayout.CONNECTION_DELETE_MODE && pin.ConnectedPin.size()>0 ){
			  paint.setColor(0xffff2222);			 
			  canvas.drawCircle(pin.x+dwidth ,srect.top-pinHeight+18,22,paint );
			  paint.setColor(0xffffffff);
			  canvas.drawLine(pin.x+dwidth-12 , srect.top-pinHeight+18 , pin.x+dwidth+12 , srect.top-pinHeight+18, paint); 
		      }
		  else{
			   canvas.drawCircle(pin.x+dwidth ,srect.top-pinHeight+5,10,paint );
			  }
		 }
	
	//rendering bottom pins	 
	 startx= (canvas.getWidth() -(nbott*pinWidth))/2;
	constantY=(int)srect.bottom;
	bpins_type=Pin.BOTTOM_PIN;
	 for(int i=0;i<nbott;i++){
		 Pin pin=bpins[i];
		 pin.x=startx+(i*pinWidth); pin.y=constantY;
		  pin.w=pinWidth; pin.h=pinHeight+10;
		  pin.absX=super.getX()+pin.x+dwidth;  pin.absY=super.getY()+getHeight()-5;
		  paint.setColor(0xffffffff);
		  canvas.drawLine(pin.x+dwidth,pin.y, pin.x+dwidth , pin.y+pinHeight,paint );
		 		
		  
		  if(pin == selected_pin)
		     paint.setColor(EditorLayout.selectColor);
		  else
			  paint.setColor(pin.getDefaultColor());
		  
		  if(parent_layout.getMode() == EditorLayout.CONNECTION_DELETE_MODE  && pin.ConnectedPin.size()>0){
			  paint.setColor(0xffff2222);
			 canvas.drawCircle(pin.x+dwidth ,pin.y+pinHeight-18,22,paint );
			 paint.setColor(0xffffffff);
			 canvas.drawLine(pin.x+dwidth-12,pin.y+pinHeight-18 ,pin.x+dwidth+12, pin.y+pinHeight-18 , paint);
		       
			   }
		 else {
			 canvas.drawCircle(pin.x+dwidth ,pin.y+pinHeight-5,10,paint );
			 }
		 }
	  
 
	  drawTitles(canvas);
	  
	 	  	   		 	  	  
  }// end of onDraw


	public static void setSelectedPin(@NonNull Pin pin){

	}
				
	float dx=0.0f,dy=0.0f; 
	float downx=0.0f , downy=0.0f;
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
	    super.bringToFront();
	    int action=event.getAction();  
				  		  
		 if(action == MotionEvent.ACTION_UP && Math.abs(downx-event.getRawX())<5 ? true : false){
			 
			 float x=event.getX();
			 float y=event.getY();
			 if(x>srect.left && x<srect.right && y>srect.top && y<srect.bottom ){
			     
				 if(selected_view == null  && parent_layout.getMode()==EditorLayout.SELECT_MODE){
					 selected_view=this;
					 Squarecolor=EditorLayout.selectColor;
					 parent_layout.setTextLabelNull();
					 if(BasicGateView.SelecetViewChangeTask!=null)
					    BasicGateView.SelecetViewChangeTask.run();
					 }
				 else if(selected_view != this   && parent_layout.getMode()==EditorLayout.SELECT_MODE){
				     selected_view.Squarecolor=0xffffffff;					 
					 selected_view.invalidate();
					 selected_view=this;
					 Squarecolor=EditorLayout.selectColor;
					 parent_layout.setTextLabelNull();
					 if(BasicGateView.SelecetViewChangeTask!=null)
					    BasicGateView.SelecetViewChangeTask.run();
					 }//end of else if
				
				 }
			else  if(x<srect.left){
				//checking for input lines			
				for(int i=0; i<pins.length; i++){
					Pin p=pins[i];
				       if(x>p.x && x<(p.x+p.w) && y>p.y&& y<(p.y+p.h)){
						    if(parent_layout.getMode() == EditorLayout.CONNECTION_DELETE_MODE){
								   p.deleteAllConnections();

								}
							else  if(selected_pin==null ){
									   selected_pin=p; selected_pinType=Pin.LEFT_PIN; if(BasicGateView.SelectPinChangeTask!=null) BasicGateView.SelectPinChangeTask.run(); }
							   else{
								   BasicGateView tmp=selected_pin.parent;
								   if(selected_pin!=p && selected_pin.parent!=p.parent && Math.abs(selected_pin.pinSignalType-p.pinSignalType)==100){
									   setConnectionPoints(selected_pin, selected_pinType ,p , Pin.LEFT_PIN );
										}
								   else{
									   selected_pin=p; selected_pinType=Pin.LEFT_PIN;
									   if(BasicGateView.SelectPinChangeTask!=null)
									      BasicGateView.SelectPinChangeTask.run();
								   }
								   tmp.invalidate();			   
								 }
								
							      break;}
					}
				
				}
			//cheking for output lines
			else if(x > srect.right) {
				for(int i=0; i<opin.length; i++){
				   Pin p=opin[i];
				   if(x>p.x && x<(p.x+p.w) && y>p.y&& y<(p.y+p.h)){
					   if(parent_layout.getMode() == EditorLayout.CONNECTION_DELETE_MODE){
								   p.deleteAllConnections();
								  // parent_layout.TriggerCircuit();
								}
						 else if(selected_pin==null){
									   selected_pin=p; selected_pinType=Pin.RIGHT_PIN; if(BasicGateView.SelectPinChangeTask!=null)BasicGateView.SelectPinChangeTask.run();}
							   else{
								   BasicGateView tmp=selected_pin.parent;
								   if(selected_pin!=p &&  selected_pin.parent!=p.parent && Math.abs(selected_pin.pinSignalType-p.pinSignalType)==100  ){
									   setConnectionPoints(selected_pin, selected_pinType ,p , Pin.RIGHT_PIN);
										 }
								   else{
									   selected_pin=p; selected_pinType=Pin.LEFT_PIN;  if(BasicGateView.SelectPinChangeTask!=null)BasicGateView.SelectPinChangeTask.run();
								   }
								   tmp.invalidate();			   
								 }
								
						  break;}  }					
						 
				}  
			//checking for top lines
			else if(y<srect.top){
				 for(int i=0; i<tpins.length; i++){
				   Pin p=tpins[i];
				   if(x>p.x && x<(p.x+p.w) && y>p.y&& y<(p.y+p.h)){
					   if(parent_layout.getMode() == EditorLayout.CONNECTION_DELETE_MODE){
						   p.deleteAllConnections();
						  // parent_layout.TriggerCircuit();
					   }
					    else if(selected_pin==null){
						 selected_pin=p; selected_pinType=Pin.TOP_PIN;if(BasicGateView.SelectPinChangeTask!=null) BasicGateView.SelectPinChangeTask.run(); }
						 else{
							 BasicGateView tmp=selected_pin.parent;
							 if(selected_pin!=p &&  selected_pin.parent!=p.parent && Math.abs(selected_pin.pinSignalType-p.pinSignalType)==100  ){
								 setConnectionPoints(selected_pin, selected_pinType ,p , Pin.TOP_PIN );
							   }
							 else{
								 selected_pin=p; selected_pinType=Pin.LEFT_PIN; if(BasicGateView.SelectPinChangeTask!=null)BasicGateView.SelectPinChangeTask.run();
							 }
							 tmp.invalidate();
						 }
						   break;} //end of if
						    }	//end of for										 			 			
				}
			//checking for bottom lines
			else if(y>srect.bottom){
				for(int i=0; i<bpins.length; i++){
					Pin p=bpins[i];
					if(x>p.x && x<(p.x+p.w) && y>p.y&& y<(p.y+p.h)){
						if(parent_layout.getMode() == EditorLayout.CONNECTION_DELETE_MODE){
							p.deleteAllConnections();
							//parent_layout.TriggerCircuit();
						}
						else if(selected_pin==null){
						selected_pin=p; selected_pinType=Pin.BOTTOM_PIN; if(BasicGateView.SelectPinChangeTask!=null) BasicGateView.SelectPinChangeTask.run();  }
						else{
							BasicGateView tmp=selected_pin.parent;
							if(selected_pin!=p &&  selected_pin.parent!=p.parent  && Math.abs(selected_pin.pinSignalType-p.pinSignalType)==100   ){
								setConnectionPoints(selected_pin, selected_pinType ,p , Pin.BOTTOM_PIN );
							  }
							else{
								selected_pin=p; selected_pinType=Pin.LEFT_PIN; if(BasicGateView.SelectPinChangeTask!=null)BasicGateView.SelectPinChangeTask.run();
							}
							tmp.invalidate();
						}
				  break;}  
				}		
				
				}
			 invalidate();			
			 downx=0.0f ; downy=0.0f;

			 if(parent_layout.isSignalVisible())
		        parent_layout.invalidate();
			 			 
		}
		
		else if(action==MotionEvent.ACTION_DOWN){
			 dx=super.getX()- event.getRawX();
			 dy=super.getY() - event.getRawY();
			 			 
			downx=event.getRawX(); downy=event.getRawY();
			}
		else if(action == MotionEvent.ACTION_MOVE){
			 float px=super.getX(); float py=super.getY();						  			 
			  super.animate().x(event.getRawX()+dx)
					         .y(event.getRawY()+dy)
							 .setDuration(0)
							 .start();
					
				updatePins(super.getX()-px, super.getY()-py);					
				parent_layout.invalidate();								
			     						  			 
			}	
				    										
		return true;
		}//end of onTouchEvent			  
	  	 	 		
	
	private void updatePins(float cx,float cy){
		
		for(Pin p:pins){
			  p.absX+=cx; p.absY+=cy;
		}	  
		for(Pin p:bpins){
			  p.absX+=cx; p.absY+=cy;
			 
			}
			
		for(Pin p: opin){
				p.absX+=cx; p.absY+=cy;
			
			}
		
		for(Pin p:tpins){
				p.absX+=cx; p.absY+=cy;
				
			}	
				
	}//end of updatePins

	
	

} 