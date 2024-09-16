package com.logic.logicsimulator;
//version 1
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.parsers.ConnectionElement;
import com.logic.logicsimulator.parsers.PinElement;

import java.util.ArrayList;
import java.util.List;

public class Pin {

	public Pin(int index,BasicGateView par){
		this.pinIndex=index;
		this.parent=par;
		element=new PinElement(this , par.parent_layout.getDocument());
	}
	public int pinIndex=0;
	public int x,y,w,h;
	private int defaultColor= Color.WHITE;
	public float absX,absY;
	public boolean selected=false;
	public PinElement element;
	private boolean value=false;
//	public List<BasicGateView> parentslist=new ArrayList<>();
	public List<Pin> ConnectedPin=new ArrayList<>();    
	public List<EditorLayout.ConnectionWire> connections = new ArrayList<>();
	public List<BasicGateView> ConnectedParent=new ArrayList<>();
	private String pinName="pin" ;//value for debugging

	private BasicTask onPinNameChange=null;
	public void setOnPinNameChange(BasicTask task){
		this.onPinNameChange=task;
	}

	public void setPinName(String name){
		if(element!=null){
			element.setPinName(name);
		}
		pinName=name;

		if(onPinNameChange!=null){
			onPinNameChange.run();
		}
	}

	public String getPinName(){
		return pinName;
	}


	private BasicTask onValueChange=null;
	private BasicTask onForwardValue=null;

	public void setOnValueChange(BasicTask task){
		this.onValueChange=task;
	}

	public void setOnForwardValue(BasicTask task){
		this.onForwardValue=task;
	}

	public void setDefaultColor(int color){
		this.defaultColor=color;
	}

	public int getDefaultColor(){
		return this.defaultColor;
	}


	public void setValue(boolean value){
		 this.value=value;
		 if(this.onValueChange!=null)
			 onValueChange.run();
	}



	public boolean getValue(){
		return this.value;
	}

	public void setElement(@NonNull PinElement element){
		this.element=element;
		this.pinName=element.getPinName();
		for(ConnectionElement connectionElement:element.getConnections()){
               parent.parent_layout.addConnection(this, connectionElement);
		}
	}


	public PinElement getElement(){
		return this.element;
	}

	public BasicGateView parent=null;
	public int pinSignalType=0;//it denotes the signal type i.e it input ,output or dynamic and if it is not connected with anyone then its NONE type
	//line data
	public int pinOreintation=LEFT_PIN;


	public void forwardValue(){
		   for(Pin p:ConnectedPin){
				  p.setValue(this.value);
				   }
		   
			for(BasicGateView b:ConnectedParent)
					b.Logic();	  


			if(this.onForwardValue!=null)
				this.onForwardValue.run();
		}

     public void addParent(BasicGateView gateView){
		      boolean found=false;
			  for(BasicGateView gate:ConnectedParent){
				  if(gateView==gate){
				       found=true;
					   break ;}
				  }
				  
				  if(!found)
						  ConnectedParent.add(gateView);
				  
		 }	

public void removeParent(BasicGateView gateView){
	  int count=0;
	     for(Pin p:ConnectedPin)
			   if(p.parent==gateView)
					   count++;
		
		if(count<=1)
				ConnectedParent.remove(gateView);			   
			 
	}
	
		
		
			 
	public void deleteAllConnections(){
		 value=false;
		for(int i=0;i<ConnectedPin.size();i++){
			//removing this pin from connected pins
			    Pin p=ConnectedPin.get(i);
				p.ConnectedPin.remove(this);
				p.value=false;
				p.removeParent(this.parent);

				//removig connection wires from connected pins				
				EditorLayout.ConnectionWire con=this.connections.get(i);
				p.connections.remove(con);
				p.parent.parent_layout.connectionWires.remove(con);
				p.parent.gateConnectionsList.remove(con);				

				con.from.getElement().removeConnection(con.getConnectionElement());
				p.parent.Logic();

			}//end of loop
		
		connections.clear();
		ConnectedPin.clear();
	//	parentslist.clear();

		element.removeAllConnection();

		if(parent.parent_layout.changeConnectionTask!=null)
			parent.parent_layout.changeConnectionTask.run();

	}

		//pin orintation values
    public static final int LEFT_PIN=100;
	public static final int TOP_PIN=200;
    public static final int RIGHT_PIN=300;
    public static final int BOTTOM_PIN=400;

public static final int INPUT=500;    //signal input pin type constant
public static final int OUTPUT=600;   //signal output pin type constant
public static final int DYNAMIC=700;  //it can be both (for making common bus circuits in simulator)
public static final int NONE=0;

}