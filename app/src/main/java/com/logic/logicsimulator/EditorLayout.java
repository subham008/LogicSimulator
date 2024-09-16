package com.logic.logicsimulator;
//version 2
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.Gates.ICGate;
import com.logic.logicsimulator.Gates.TextLabel;
import com.logic.logicsimulator.parsers.ConnectionElement;
import com.logic.logicsimulator.parsers.ConnectionMaker;
import com.logic.logicsimulator.parsers.GateElement;
import com.logic.logicsimulator.parsers.IdentityManager;
import com.logic.logicsimulator.parsers.LogicProject;
import com.logic.logicsimulator.parsers.TextLabelElement;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EditorLayout extends FrameLayout {

public boolean DEBUG_MODE=true;

public static final int TOUCH_MODE=90;
public static final int SELECT_MODE=91;
public static final int CONNECTION_DELETE_MODE=92;


private float scaleFactor=1.0f,scale=1.0f;



private int Mode=SELECT_MODE;
private ScaleGestureDetector  ScaleListener;
private List<BasicGateView> Gates;
private List<BasicGateView> InputGates;
private Map<String,BasicGateView> GateWithID;

private List<TextLabel> TextLabelList;
private Document document;
IdentityManager identityManager;
private Paint paint = null;
boolean  isScaled=false;
public static int signalColor=0xFF1A09FF;
public static int selectColor=0xff39ff72;
public static int backgroundColor=0xFF3D3D3D;

public void setSignalColorVisible(boolean state){
	signal_visible=state;
}

public boolean isSignalVisible(){
	return signal_visible;
}

private static boolean signal_visible=true;

private TextLabel TextLabelSelected=null;

private LogicProject logicProject=null;
public void addTextLabel(@NonNull  TextLabel label){
	TextLabelElement textLabelElement=new TextLabelElement(label , document);
	if(textLabelElement.isValid()) {
		super.addView(label);
		TextLabelList.add(label);
		label.setElement(textLabelElement);
		logicProject.addTextLabel(label.getElement());
	}
}

public void addTextLabelOnLayout(@NonNull  TextLabel label , @NonNull TextLabelElement textLabelElement){
	super.addView(label);
	TextLabelList.add(label);
	label.setElement(textLabelElement);
}

public List<TextLabel> getTextLabelList(){
	return TextLabelList;
}
public void setTextLabelFocus(@NonNull TextLabel view){
	//for previous lavel
	if(TextLabelSelected!=null){
		//changing color to white of prevois
		TextLabelSelected.setTextColor(0xffffffff);
	}

	if(Gates.size()>0 && BasicGateView.selected_view!=null){
		BasicGateView.selected_view.Squarecolor=0xffffffff;
		BasicGateView.selected_view.invalidate();
		BasicGateView.selected_view=null;
		if(BasicGateView.SelecetViewChangeTask!=null)
			BasicGateView.SelecetViewChangeTask.run();
	}

	view.setTextColor(selectColor);
	TextLabelSelected=view;

	if(changeTextLabelTask!=null)
		changeTextLabelTask.run();
}

public void setTextLabelNull(){
	if(TextLabelSelected!=null){
		TextLabelSelected.setTextColor(0xffffffff);
		TextLabelSelected=null;
		if(changeTextLabelTask!=null)
			changeTextLabelTask.run();
	}
}

public void removeTextLabel(TextLabel label){
	if(TextLabelSelected==label){
		setTextLabelNull();
	}

	TextLabelList.remove(label);
	logicProject.removeTextLabel(label.getElement());
	if(changeTextLabelTask!=null)
		changeTextLabelTask.run();
	super.removeView(label);
}

public TextLabel getTextLabelSelected(){
	return TextLabelSelected;
}


public void  setTextLabelVisible(boolean visible ){
	for (TextLabel label:TextLabelList) {
		if(!visible)
		    label.setVisibility(View.GONE);
		else
			label.setVisibility(View.VISIBLE);
	}
}//end of  setTextLabelVisible
private void SetListener(Context context){
	     ScaleListener=new ScaleGestureDetector(context ,new ScaleGestureDetector.SimpleOnScaleGestureListener(){
			    @Override
				public boolean onScale(ScaleGestureDetector detector){
					
					scaleFactor = detector.getScaleFactor();

					 scale *= scaleFactor;
                    scale = Math.max(0.3f, Math.min(scale, 5.0f)); 					
					 animate().scaleX(scale).scaleY(scale).setDuration(0).start();				  					 				     
						 isScaled=true;										   		   	
					return  true;
					}				
					
				@Override
                public boolean onScaleBegin(ScaleGestureDetector detector) {
                 // Set the pivot point to the center of the gesture
                   setPivotX(detector.getFocusX());
                   setPivotY(detector.getFocusY());
                    return true;
					}					              
					
			 } );			 			
	  		 
		
	}
	public EditorLayout(Context context){
		  super(context);
		  SetListener(context);
		  Gates=new ArrayList<BasicGateView>();
		  InputGates=new ArrayList<BasicGateView>();
		  GateWithID=new HashMap<>();
		  TextLabelList=new ArrayList<>();
		  //ElementWithID=new HashMap<>();
		  identityManager=new IdentityManager();
		  connectionMaker=new ConnectionMaker();
		  paint=new Paint();
		  this.setBackgroundColor(backgroundColor);
		  
		}
	public EditorLayout (Context context, AttributeSet attrs){
		super(context,attrs);
		SetListener(context);
		Gates=new ArrayList<BasicGateView>();
		InputGates=new ArrayList<BasicGateView>();
		GateWithID=new HashMap<>();
		TextLabelList=new ArrayList<>();
		//ElementWithID=new HashMap<>();
		identityManager=new IdentityManager();
		connectionMaker=new ConnectionMaker();
		paint=new Paint();
		this.setBackgroundColor(backgroundColor);
		}
  
   public EditorLayout (Context context, AttributeSet attrs, int defStyle){
		super(context,attrs, defStyle);
		SetListener(context);
		Gates=new ArrayList<BasicGateView>();
		InputGates=new ArrayList<BasicGateView>();
	    GateWithID=new HashMap<>();
	   TextLabelList=new ArrayList<>();
	   //ElementWithID=new HashMap<>();
	   identityManager=new IdentityManager();
	   connectionMaker=new ConnectionMaker();
		paint=new Paint();
	   this.setBackgroundColor(backgroundColor);
		}



private  BasicGateView  selected_view=null;

public void setDocument(@NonNull Document doc){
	this.document=doc;
}
public Document getDocument(){
	return this.document;
}

public void setLogicProject(@NonNull LogicProject logicProject){
	 this.logicProject=logicProject;
}

BasicTask changeGateTask=null;
BasicTask changeConnectionTask=null;
BasicTask changeTextLabelTask=null;

public void addGate(BasicGateView gateview){
	Gates.add(gateview);
	String newID=identityManager.createNewIdfor();
	GateWithID.put(newID,gateview);
	gateview.setGateID(newID);

	//gateview.setElement(circuitBuilder.createElement(gateview.gateType,gateview.gateName,newID,gateview.getLeftPinsCount(),gateview.getRightPinsCount(),gateview.getTopPinsCount(),gateview.getBottomPinsCount(),(int)gateview.getX(),(int)gateview.getY(),document),document);
	gateview.setElement(new GateElement(gateview), document);
	addView(gateview);
	logicProject.addGateElement(gateview.getElement());
	if(gateview.GATE_TYPE==BasicGateView.INPUT_GATE)
		InputGates.add(gateview);
	if (changeGateTask!=null)
        changeGateTask.run();

}//end of addGate

	public void addGate(BasicGateView gateview , GateElement element){
		Gates.add(gateview);
		String gateID=String.valueOf(element.getID());
		GateWithID.put(gateID,gateview);
		gateview.setGateID( gateID );
		addView(gateview);
		identityManager.addID(gateID);
		gateview.setElement(element,document);
		if(gateview.GATE_TYPE==BasicGateView.INPUT_GATE)
			InputGates.add(gateview);
		if (changeGateTask!=null)
			changeGateTask.run();
	}//end of addGate


  public void addIC(ICGate gate ){
	  Gates.add(gate);
	  String newID=identityManager.createNewIdfor();
	  GateWithID.put(newID,gate);
	  gate.setGateID(newID);
      //Element element=circuitBuilder.createICelement(gate.gateName , gate.getGateID() ,gate.getProjectName() , (int)gate.getX() ,(int) gate.getY() , document );
	  gate.setElement(  new GateElement(gate), document);
	  gate.getElement().setProjectName(gate.getProjectName());
	  addView(gate);
      logicProject.addGateElement(gate.getElement());
	  if(gate.GATE_TYPE==BasicGateView.INPUT_GATE)
		  InputGates.add(gate);
	  if (changeGateTask!=null)
		  changeGateTask.run();
  }


  private ConnectionMaker connectionMaker=null;
  public void addConnection(Pin pin , ConnectionElement connectionElement){
	if(pin!=null && connectionElement!=null)
	    connectionMaker.addConnection(pin, connectionElement);
  }

  public int estimatedConnection(){
	    if(connectionMaker==null)
			return 0;
	  return connectionMaker.getConnectionCount();
  }

  public  int startMakingConnection(){
	  if(connectionMaker!=null)
		  return connectionMaker.makeConnections(this);

	  return 0;
  }
  public void updateAll(){
	for(BasicGateView gateView:Gates){
		gateView.updateElement();
	}
	for(TextLabel label:TextLabelList){
		label.updateElementLocation();
	}
  }
	public BasicGateView getGateByID(String id){
				BasicGateView gate= GateWithID.get(id);
				if(gate==null)
				{
					System.err.println("EditorLayout->getGateByID(String)->ID NOT FOUND:"+id);
					return null;
				}
			return gate	;
	}

	public Pin getPin(String parentid , int orient  , int index){


		BasicGateView gate=this.getGateByID(parentid);
		if(gate!=null)
			System.err.println("EditorLayout->getPin->ERROR : BasiGateView with ID "+parentid+" not found");
		Pin pin=gate.getPin(orient,index);
		if(pin!=null)
			System.err.println("EditorLayout->getPin->ERROR : Pin with orient "+orient+" index "+orient+" not found");
		return pin;
	}

	public int getElementCount(){
	    return  Gates.size();
	}

	public  List<BasicGateView> getGatesList(){
	return Gates;
	}

public void deleteAllConnections(BasicGateView gate){
	    
		for(Pin p:gate.pins)
			  p.deleteAllConnections();
		 
		for(Pin p:gate.opin)
			  p.deleteAllConnections();
		
		for(Pin p:gate.tpins)
				 p.deleteAllConnections();
				 
		for(Pin p:gate.bpins)
				 p.deleteAllConnections();
		if(changeConnectionTask!=null)
			changeConnectionTask.run();
		 
	}
	
	
public void removeGate(BasicGateView gate){
	   if(gate==null)
		 return;
			   
	   Gates.remove(gate);	   
	   InputGates.remove(gate);	   
	   super.removeView(gate);
	   deleteAllConnections(gate );	   
	   BasicGateView.selected_view=null;
	   TriggerCircuit();//replace it with Logic() somehow
	   logicProject.removeGateElements(gate.getElement());
	if(changeGateTask!=null)
		 changeGateTask.run();


	}


	

public void setTouchMode(){
	int tmpmode=Mode;
	    Mode=TOUCH_MODE;
		if(BasicGateView.selected_view!=null){				
		BasicGateView.selected_view.Squarecolor=0xffffffff;
		BasicGateView.selected_view.invalidate();
		BasicGateView.selected_view=null;
		}
	if(tmpmode==CONNECTION_DELETE_MODE){
		for(BasicGateView gates:Gates)
			   gates.invalidate();
		}	
		
	}
public void setSelectMode(){
	int tmpmode=Mode;
	    Mode=SELECT_MODE;
	
		if(tmpmode==CONNECTION_DELETE_MODE){
		for(BasicGateView gates:Gates)
			   gates.invalidate();
		}
	}

public void setDeleteMode(){
	if(Mode==CONNECTION_DELETE_MODE)
		return;
	   Mode=CONNECTION_DELETE_MODE;
	   if(BasicGateView.selected_view!=null){			
	      BasicGateView.selected_view.Squarecolor=0xffffffff;
	      BasicGateView.selected_view.invalidate();
	      BasicGateView.selected_view=null;
	   }
	   
	   for(BasicGateView gates:Gates)
			   gates.invalidate();

	}

public int getMode(){
	   return Mode;
	}
	
public void drawConnection(Canvas canvas){	
	
   if(Gates.size()<=0)
		   return;
	paint.setStyle(Paint.Style.STROKE);
	paint.setStrokeWidth(10);	 
    paint.setColor(0xffffffff);
	
    for(ConnectionWire c:connectionWires)
			c.drawConnectionWire(canvas,paint);
			
}//end of drawConnection



private void drawGrid(Canvas canvas){	 	    			
			int width=canvas.getWidth();
	        int height=canvas.getHeight();
	  
      	  int area=200;
	  int dw=width/area;
	  int dh=height/area;
	  	  
	  paint.setStyle(Paint.Style.FILL);
	 paint.setColor(0xFF626262);
	// paint.setColor(0xffffffff);
	  paint.setStrokeWidth(2);	  	  
	    //rendering vertical lines
		int initial_x=area;		
		for(int i=0;i<dw;i++){
				canvas.drawLine(initial_x,0,initial_x,height, paint);
				initial_x += area; 
				 }
		
		//rendering horizontal lines
		int initial_y=area;		
		for(int i=0;i<dh;i++){
				canvas.drawLine(0,initial_y,width,initial_y, paint);
				initial_y += area; 
				 }												
		  
}//end of drawGrid


float canvasScale=1.0f;

public static boolean gridLines=true;
public static boolean draw_connection=true;
@Override
protected void onDraw(Canvas canvas){
	if(gridLines)
	  drawGrid(canvas);

	if(draw_connection)
	   drawConnection(canvas);

	super.onDraw(canvas);

}
				

float dx = 0.0f , dy = 0.0f;
float downx=0.0f , downy=0.0f;

  @Override
  public boolean onTouchEvent(MotionEvent event) {
	  ScaleListener.onTouchEvent(event);
	  int action=event.getAction();
	  	  
	  switch (action){
		  case MotionEvent.ACTION_DOWN:	  
		     dx=super.getX()- event.getRawX();
			 dy=super.getY() - event.getRawY();  
			 
			 downx=event.getRawX(); downy=event.getRawY();
		  break ;
		  case MotionEvent.ACTION_MOVE:
		    float animX=event.getRawX()+dx;
			float animY=event.getRawY()+dy;		    
			if(animX<0.0 || animY<0.0 ){
					
				super.animate().x(animX)
					     .y(animY)
						 .setDuration(0)
						.start();
		    }
			else 
			  System.out.println("*****limit reched******");
			  
		  break ;	  
		 case MotionEvent.ACTION_UP:
		           
		           boolean isClick= Math.abs(downx-event.getRawX())<5 ? true : false;			 
		 	      				
	            if(getChildCount()>0 && BasicGateView.selected_view != null &&  isClick ){
					  BasicGateView.selected_view.Squarecolor=0xffffffff;
					  BasicGateView.selected_view.invalidate();
					  BasicGateView.selected_view=null;
					  if(BasicGateView.SelecetViewChangeTask!=null)
					    BasicGateView.SelecetViewChangeTask.run();
					}
				if(BasicGateView.selected_pin!=null &&  isClick ){
					  BasicGateView tmp=BasicGateView.selected_pin.parent;
					  BasicGateView.selected_pin=null;
					  if(BasicGateView.SelectPinChangeTask!=null)
					     BasicGateView.SelectPinChangeTask.run();
					  tmp.invalidate();
					}
			    if(TextLabelSelected!=null && isClick){
					setTextLabelNull();
				}
				 isScaled=false; 
				downx=0.0f ; downy=0.0f;				
	       break ;    
		  }	  
	  	  			  
	
	  return true;
  }
     
public   ArrayList<ConnectionWire> connectionWires = new ArrayList<>(); 
	 
public static class ConnectionWire{
	   Pin from,to;
	   ConnectionElement connectionElement;
	    ConnectionWire(Pin f , Pin t , ConnectionElement element ) {
			  from=f;
			  to=t;
			  this.connectionElement=element;
			}
	   public ConnectionElement getConnectionElement(){
			return this.connectionElement;
	   }
	   public void drawConnectionWire(Canvas canvas , Paint paint){
		      if(from.parent.parent_layout.isSignalVisible() &&  from.getValue() && to.getValue())
					  paint.setColor(EditorLayout.signalColor);
			  else
			       paint.setColor(0xffffffff);  

		      canvas.drawLine(from.absX,from.absY, to.absX,to.absY,paint);

		   }	//end of    drawConnectionWire

	}//end of ConnectionWire



	
int time_stamp=0;

public void setTimeStamp(int t){
	   if(time_stamp>t)
			time_stamp=t;
	}
	
		
  public void TriggerCircuit(){
	 if(DEBUG_MODE)
	  System.out.println("*****LOGIC TRIGGERED CIRCUIT******");
	  
	  if(InputGates.size()==0)
			  return;
			  
	  for(BasicGateView gate:Gates){
		  if(gate.gateConnectionsList.size()>0)
			  gate.Logic();
	    }
  }//end of TriggerCircuit
  


}//end of class