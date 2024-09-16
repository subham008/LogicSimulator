package com.logic.logicsimulator.DummyGates;
//version 1
import androidx.annotation.NonNull;

import com.logic.logicsimulator.BasicTask;
import com.logic.logicsimulator.Pin;
import com.logic.logicsimulator.parsers.ConnectionElement;
import com.logic.logicsimulator.parsers.PinElement;

import java.util.ArrayList;
import java.util.List;

public class DummyPin {
	private boolean value=false;
	public List<DummyPin> ConnectedPin;
	public List<BasicDummyGate> ConnectedParent;
	public BasicDummyGate parent=null;
	public int pinSignalType=0;
	private Pin mappedPin=null;
    private DummyPin dummyMappedPin=null;

	public DummyPin(){
		ConnectedPin=new ArrayList<>();
		ConnectedParent=new ArrayList<>();
	}

	//2041.875;
	private PinElement element;
	public void forwardValue(){
		if(mappedPin==null && dummyMappedPin==null) {
			//System.out.println("DummyPin- forwardvalue called with connPin:"+ConnectedPin.size()+" ConnGate:"+ConnectedParent.size());
			for (DummyPin p : ConnectedPin) {
				p.value = this.value;
			}

			for (BasicDummyGate b : ConnectedParent)
				b.Logic();
		}
		else if(mappedPin!=null){
			//System.out.println("DummyPin output to mappedpin called value :"+this.value);
			mappedPin.setValue(this.value);
			mappedPin.forwardValue();
			//System.out.println("DummyPin->mappedPin called with value:"+this.value);
		    }
		else {
			dummyMappedPin.setValue(this.value);
			mappedPin.forwardValue();
		}
		}


		public void setPin(@NonNull  Pin pin){
		       mappedPin=pin;
		}

	public void setPin(@NonNull  DummyPin pin){
		dummyMappedPin=pin;
	}
		
		public void setElement(@NonNull PinElement element){
		      this.element=element;

			for(ConnectionElement connectionElement:element.getConnections()){
				parent.getConnectionMaker().addConnection(this, connectionElement);
			}
		}


		public boolean getValue(){
		     return value;
		}

		public void setValue(boolean value){
		      this.value=value;
			  if(onValueChangeTask!=null)
				  onValueChangeTask.run();
		}
	public void addParent(BasicDummyGate gateView){

		if(!ConnectedParent.contains(gateView))
			ConnectedParent.add(gateView);

	}


	private BasicTask onValueChangeTask=null;
	public void setOnValueChange(@NonNull BasicTask task){
		this.onValueChangeTask=task;
	}
}