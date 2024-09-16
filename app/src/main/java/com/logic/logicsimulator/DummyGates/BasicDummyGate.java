package com.logic.logicsimulator.DummyGates;
//version 1

import androidx.annotation.NonNull;

import com.logic.logicsimulator.Gates.ICGate;
import com.logic.logicsimulator.Pin;
import com.logic.logicsimulator.parsers.DummyConnectionMaker;
import com.logic.logicsimulator.parsers.GateElement;
import com.logic.logicsimulator.parsers.PinElement;

import java.util.List;

public class BasicDummyGate {
	
 public   DummyPin pins[]; int pins_type;
 public   DummyPin opin[]; int opin_type;
 public   DummyPin tpins[]; int tpins_type;
 public   DummyPin bpins[]; int bpins_type;
 
 public String gateName;

 private GateElement element;

 private DummyConnectionMaker  maker;
	public void Logic(){
			//override this function to implement your logic
		    System.out.println("void Logic()  not overrided");
		}
		
	
	public  BasicDummyGate( int inputCount,int outputCount,int topCount,int bottomCount){



		  pins=new DummyPin[inputCount];
		  for(int i=0; i<inputCount; i++) {
			  pins[i] = new DummyPin();
			  pins[i].parent=this;
		  }
		  
		  opin=new DummyPin[outputCount];
		  for(int i=0; i<outputCount; i++) {
			  opin[i] = new DummyPin();
			  opin[i].parent=this;
		  }
				 
		  tpins=new DummyPin[topCount];
		  for(int i=0; i<topCount; i++) {
			  tpins[i] = new DummyPin();
			  tpins[i].parent=this;
		  }
			
		  bpins=new DummyPin[bottomCount];
			   for(int i=0; i<bottomCount; i++) {
				   bpins[i] = new DummyPin();
				   bpins[i].parent=this;
			   }
		 		  		  		   	  				  
		}


		public static  void setConnectionPoints(DummyPin from , DummyPin to){
			if(from == null || to == null)
				return;
			DummyPin f=null,t=null;//f for output ,t for input

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
				return;
			if(!t.ConnectedPin.isEmpty())
				return;


			f.ConnectedPin.add(t);
			t.ConnectedPin.add(f);

			f.addParent(t.parent);
		}



     public  DummyPin  getDummyPin(int oreint ,int index){
		DummyPin pin=null;

		switch (oreint){
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


	 public void setElement(GateElement element){
		    this.element=element;
			 setPinElements(pins , element.getLeftPinElements());
		     setPinElements(opin , element.getRightPinElements());
			 setPinElements(tpins , element.getTopPinElements());
			 setPinElements(bpins, element.getBottomPinElement());

	 }


	 public void setConnetionMaker(@NonNull DummyConnectionMaker maker){
		this.maker=maker;
	 }

	 public DummyConnectionMaker getConnectionMaker(){
		return this.maker;
	 }



	 public static void setPinElements(DummyPin[] pin , List<PinElement> pinElementList){
        if(pin.length != pinElementList.size())
		{
			System.out.println("BasicDummyGate:setPinElements(DummyPin[],List<PinElement>): array and list have diffrent size pin[]:"+pin.length+" List<PinElement>:"+pinElementList.size());
			return;
		}

		for(int i=0 ; i<pin.length; i++){
			pin[i].setElement(pinElementList.get(i));
		}
	 }
}