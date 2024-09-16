package com.logic.logicsimulator.DummyGates;
//versiin 1

import com.logic.logicsimulator.BasicTask;
import com.logic.logicsimulator.Gates.CounterGate;
import com.logic.logicsimulator.Gates.GateParser;
import com.logic.logicsimulator.Gates.ICGate;
import com.logic.logicsimulator.Pin;
import com.logic.logicsimulator.parsers.DummyConnectionMaker;
import com.logic.logicsimulator.parsers.GateElement;
import com.logic.logicsimulator.parsers.IntegratedCircuitParser;
import com.logic.logicsimulator.parsers.LinkPinElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DummyGates {
	
	public static class AndGateView extends BasicDummyGate{
		public AndGateView( int inputCount){
		    super( inputCount,1,0,0 );
			super.gateName="And";
			
			for(DummyPin p:pins)
					p.pinSignalType=Pin.INPUT;
			
			opin[0].pinSignalType=Pin.OUTPUT;										
		      }
			  
		 @Override
		 public void Logic(){
			//System.out.println("DUMMY AND LOGIC");
			int total_input_pins=super.pins.length;
		   int count=0;
		   for(DummyPin p:pins){
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
		  
	}//end of AndGateView class
	
		
	public static class BufferGateView extends BasicDummyGate{
		   public BufferGateView( ){
		    super(1,1,0,0 );
			super.gateName="Buffer";
			pins[0].pinSignalType=Pin.INPUT;
			opin[0].pinSignalType=Pin.OUTPUT;
			
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
		   
	}//end of class 
			
		
	public static class FrequencyGeneratorView extends BasicDummyGate{
		   public FrequencyGeneratorView(){
		      super(0,1,0,0);
			 super.gateName="Frequency Generator";
			 opin[0].pinSignalType=Pin.OUTPUT;			 			 	  
		  }//end of constructer


		  @Override  
		  public void Logic(){
			     //need to be done ass soon as possible
			  }
		}
	
	public static class HighConstantView extends BasicDummyGate{
		  public HighConstantView( ){
		    super(0,1,0,0);
			super.gateName="High Constant";
		//	GATE_TYPE=BasicGateView.INPUT_GATE;
		  opin[0].setValue(true);
		  opin[0].pinSignalType=Pin.OUTPUT;
		}
		
		 @Override		
    	public void Logic(){
		    opin[0].setValue(true);
			opin[0].forwardValue();
		}
		
		}//end of class 
	
	public static class LowConstantView extends BasicDummyGate{
		  public LowConstantView( ){
		    super(0,1,0,0);
			super.gateName="Low Constant";
		  //GATE_TYPE=BasicGateView.INPUT_GATE;
		  opin[0].setValue(false);
		  opin[0].pinSignalType=Pin.OUTPUT;
		}
		
	  @Override		
	public void Logic(){
		    opin[0].setValue(false);
			opin[0].forwardValue();
		}
		
	}//end of thr class
	
	public static class NandGateView extends BasicDummyGate{
		public NandGateView(  int inputCount){
		    super(inputCount ,1,0,0);
			super.gateName="Nand";
			for(DummyPin p:pins)
					p.pinSignalType=Pin.INPUT;
			opin[0].pinSignalType=Pin.OUTPUT;
		 }
		 
		 @Override
	public void Logic(){
			 //System.out.println("DUMMY NAND LOGIC");
			int input_count=pins.length;
			int count=0;
			for(DummyPin p:pins)
				if(p.getValue())
						count++;
			  
			if(count == input_count){  
			     opin[0].setValue(false);
				 opin[0].forwardValue();
				 } 
			else{ 
			     opin[0].setValue(true);
				 opin[0].forwardValue();
				 }
			  
		}//end of logic
		
		}//end of class 
	public static class NorGateView extends BasicDummyGate{
		public NorGateView(int inputCount){
		    super( inputCount ,1,0,0);
			super.gateName="Nor";
			for(DummyPin p:pins)
					p.pinSignalType=Pin.INPUT;
			opin[0].pinSignalType=Pin.OUTPUT;
		 }
		 
		   @Override	
	public void Logic(){
			   //System.out.println("DUMMY NOR LOGIC");
		   int input_count=pins.length;
		   
		   int count=0;
		   
		   for(DummyPin p:pins){
			       if(p.getValue())
						   count++;
			   }
			
	       if(count>0){
			     opin[0].setValue(false);
				 opin[0].forwardValue();
			   }
		   else {
			   opin[0].setValue(true);
				 opin[0].forwardValue();
			   }
		  }//end of logic
		}//end of class
	
	public static class NotGateView extends BasicDummyGate{
		 public NotGateView(){
		    super( 1 ,1,0,0);
			super.gateName="Not";
			opin[0].setValue(true);
			pins[0].setValue(false);
			
			opin[0].pinSignalType=Pin.OUTPUT;
			pins[0].pinSignalType=Pin.INPUT;
			
		 }
		 
		 @Override
	public void Logic(){
			// System.out.println("DUMMY: NOT LOGIC");
		if(pins[0].getValue()){
			opin[0].setValue(false);
			opin[0].forwardValue();
		}
		else {
			opin[0].setValue(true);
			opin[0].forwardValue();
		}
	}//end of logic
	
}//end of class
	
	public static class OrGateView extends BasicDummyGate{
		
		  public OrGateView(  int inputCount){
		    super(  inputCount,1,0,0 );
			super.gateName="Or";
			
			for(DummyPin p:pins)
					p.pinSignalType=Pin.INPUT;
			opin[0].pinSignalType=Pin.OUTPUT;
		 }
		 
		 @Override
		public void Logic(){
			int input_count=pins.length;
			int count=0;
			for(DummyPin p:pins){
				if(p.getValue()) {
					opin[0].setValue(true);
					opin[0].forwardValue();
					break;
				}
				count++;
			}
			
			if(count==input_count){
				opin[0].setValue(false);
				opin[0].forwardValue();
			}

		}//end of logic
		
	}//end of class
			
	
	public static class XnorGateView extends BasicDummyGate{
		 public XnorGateView(  ){
		    super( 3 ,1,0,0);
			super.gateName="Xnor";
			
			for(DummyPin p:pins)
					p.pinSignalType=Pin.INPUT;
			opin[0].pinSignalType=Pin.OUTPUT;
			
		 }
		 
		 	
	@Override
	public void Logic(){
		
		
		int count=0;
		
		for(DummyPin p:pins){
			if(p.getValue())
			   count++;
		}
		
			if(count==0 || count==2){
				 opin[0].setValue(true);
			     opin[0].forwardValue();
				}
			else {
				opin[0].setValue(false);
				opin[0].forwardValue();
				}
								
	          }//end of logic
		}//end of class
	
	public static class XorGateView extends BasicDummyGate{
		  public XorGateView(){
		    super(3,1,0,0 );
			super.gateName="Xor";
			for(DummyPin p:pins)
			p.pinSignalType=Pin.INPUT;

			opin[0].pinSignalType=Pin.OUTPUT;
		 }
		 
		 	@Override
		public void Logic(){
						
			int count=0;
			
			for(DummyPin p:pins){
				if(p.getValue())
				   count++;
			}
			
			if(count==1 || count==3){
				 opin[0].setValue(true);
			     opin[0].forwardValue();
				}
			else {
				opin[0].setValue(false);
				opin[0].forwardValue();
				}		
		}//end of logic	
		
		
		}//end of class

	public static  class MultiplexerGateView extends BasicDummyGate{

		public  MultiplexerGateView( int input){
			super((int)Math.pow(2,input),1,0,input );
			super.gateName="Multiplexer";

			for(DummyPin p:pins)
				p.pinSignalType=Pin.INPUT;
			for(DummyPin p:opin)
				p.pinSignalType=Pin.OUTPUT;
			for(DummyPin p:bpins)
				p.pinSignalType=Pin.INPUT;
		}

		@Override
		public void Logic(){

			String binary=null;
			for(DummyPin p:super.bpins)
				binary=binary+String.valueOf(p.getValue());

			int address=Integer.parseInt(binary,2);

			opin[0].setValue(pins[address].getValue());

			opin[0].forwardValue();
		}
	}

	public static  class DemultiplexerGateView extends BasicDummyGate{

		public  DemultiplexerGateView( int input){
			super(1,(int)Math.pow(2,input),0,input );
			super.gateName="Demultiplexer";

			for(DummyPin p:pins)
				p.pinSignalType=Pin.INPUT;
			for(DummyPin p:opin)
				p.pinSignalType=Pin.OUTPUT;
			for(DummyPin p:bpins)
				p.pinSignalType=Pin.INPUT;
		}

		@Override
		public void Logic(){

			String binary=null;
			for(DummyPin p:super.bpins)
				binary=binary+p.getValue();

			int address=Integer.parseInt(binary,2);

			opin[address].setValue(pins[0].getValue());

			opin[address].forwardValue();
		}
	}

	public static  class JKlatchGateView extends BasicDummyGate{

		public  JKlatchGateView(){
			super(2,2,1,1 );
			super.gateName="JKlatch";


		}

		@Override
		public void Logic(){
			if(pins[0].getValue() && pins[1].getValue()){
				opin[0].setValue(!opin[0].getValue());
				opin[1].setValue(!opin[1].getValue());

				opin[0].forwardValue();
				opin[1].forwardValue();

				return;
			}



			boolean changed=false;
			// if J is true
			if(pins[0].getValue()){
				opin[0].setValue(true);
				opin[1].setValue(false);
				changed=true;
			}

			// if K is true
			if(pins[1].getValue()){
				opin[0].setValue(false);
				opin[1].setValue(true);
				changed=true;
			}

			if(changed) {
				opin[0].forwardValue();
				opin[1].forwardValue();
			}
		}
	}//end if jklatch


	public static  class SRlatchGateView extends BasicDummyGate{

		public  SRlatchGateView( ){
			super(2,2,1,1 );
			super.gateName="SRlatch";

		}

		@Override
		public void Logic(){

			//if both S and R  pins become  true then do nothing
			if(pins[0].getValue() && pins[1].getValue()){
				return;
			}

			boolean changed=false;
			// if R is true
			if(pins[0].getValue()){
				opin[0].setValue(true);
				opin[1].setValue(false);
				changed=true;
			}

			// if S is true
			if(pins[1].getValue()){
				opin[0].setValue(false);
				opin[1].setValue(true);
				changed=true;
			}

			if(changed) {
				opin[0].forwardValue();
				opin[1].forwardValue();
			}

		}
	}//end if SRlatch'

	public static  class TlatchGateView extends BasicDummyGate{

		public  TlatchGateView( ){
			super(2,2,0,0);
			super.gateName="Tlatch";

		}

		@Override
		public void Logic(){

			if(!pins[1].getValue())
				return;

			if(pins[0].getValue()){
				opin[0].setValue(!opin[0].getValue());
				opin[1].setValue(!opin[1].getValue());

				opin[0].forwardValue();
				opin[1].forwardValue();
			}

		}
	}//end if Tlatch'


	public static  class DlatchGateView extends BasicDummyGate{

		public  DlatchGateView(){
			super(2,2,1,1);
			super.gateName="Dlatch";

		}

		@Override
		public void Logic(){
			if(!pins[1].getValue())
				return;

			if(pins[0].getValue()){
				opin[0].setValue(true);
				opin[1].setValue(false);
			}
			else {
				opin[0].setValue(false);
				opin[1].setValue(true);
			}

			opin[0].forwardValue();
			opin[1].forwardValue();
		}
	}//end if Dlatch'

    public static class SRFlipFlop extends BasicDummyGate{
		public SRFlipFlop(){
			super(2,2,0,1);
			super.gateName="SRFlipFlop";
		}

		@Override
		public void Logic(){

			//if both S and R  pins become  true then do nothing
			if(pins[0].getValue() && pins[1].getValue()){
				return;
			}

			if(!bpins[0].getValue())
				return;

			boolean changed=false;
			// if R is true
			if(pins[0].getValue()){
				opin[0].setValue(true);
				opin[1].setValue(false);
				changed=true;
			}

			// if S is true
			if(pins[1].getValue()){
				opin[0].setValue(false);
				opin[1].setValue(true);
				changed=true;
			}

			if(changed) {
				opin[0].forwardValue();
				opin[1].forwardValue();
			}

		}//end of Logic
	}


	public static class JKFlipFlop extends BasicDummyGate{

		public JKFlipFlop(){
			 super( 3,2,1,1);
			 super.gateName="JKFlipFlop";
		}


		//jk is as same as SR just when both J and K becomes true  , it starts toggles
		@Override
		public void Logic(){

			if(pins[0].getValue() && pins[2].getValue() && pins[1].getValue() ){
				opin[0].setValue(!opin[0].getValue());
				opin[1].setValue(!opin[1].getValue());

				opin[0].forwardValue();
				opin[1].forwardValue();

				return;
			}



			boolean changed=false;
			// if J is true
			if((pins[0].getValue() && pins[1].getValue()) || tpins[0].getValue()){
				opin[0].setValue(true);
				opin[1].setValue(false);
				changed=true;
			}

			// if K is true
			if((pins[2].getValue() && pins[1].getValue()) || bpins[0].getValue()){
				opin[0].setValue(false);
				opin[1].setValue(true);
				changed=true;
			}

			if(changed) {
				opin[0].forwardValue();
				opin[1].forwardValue();
			}


		}


	}



	public static class TFlipFlop extends BasicDummyGate{

		public TFlipFlop(){
			super(2,2,0,0);
			super.gateName="T Flip Flop";
		}


		@Override
		public void Logic(){
			if(!pins[1].getValue())
				return;

			if(pins[0].getValue()){
				opin[0].setValue(!opin[0].getValue());
				opin[1].setValue(!opin[1].getValue());

				opin[0].forwardValue();
				opin[1].forwardValue();
			}
		}
	}


	public static class DFlipFlop extends BasicDummyGate{

		public DFlipFlop(){
			super( 2,2,0,0);
			super.gateName="DFlipFlop";
		}
		@Override
		public void Logic(){
			if(!pins[1].getValue())
				return;

			if(pins[0].getValue()){
				opin[0].setValue(true);
				opin[1].setValue(false);
			}
			else {
				opin[0].setValue(false);
				opin[1].setValue(true);
			}

			opin[0].forwardValue();
			opin[1].forwardValue();
		}
	}

	public static class ByteGate extends BasicDummyGate{
		public ByteGate(){
			super(8,8,1,2);
			super.gateName="1-Byte";
		}

		boolean[] data=new boolean[8];
		@Override
		public void Logic(){
			if(tpins[0].getValue())
				for(boolean d:data)
					d=false;

			if(bpins[0].getValue())
				for(int i=0; i<data.length; i++){
					data[i]=pins[i].getValue();
				}

			if(bpins[1].getValue())
				for(int i=0; i<data.length; i++){
					opin[i].setValue(data[i]);
					opin[i].forwardValue();
				}


		}
	}

	public static class RamGate extends BasicDummyGate{
		private int memsize=0;
		private boolean[][] data;
		public RamGate( int mem_size){
			super(8,8,3,mem_size );
			super.gateName="Ram Gate";

			this.memsize=mem_size;
			data=new boolean[8][mem_size];
		}

		@Override
		public void Logic(){
			int address=0;
			int count=0;
			for(int i=bpins.length-1 ; i>=0 ; i--){
				if(bpins[i].getValue()){
					address+=(int)(1*Math.pow(2, count));
				}

				count++;
			}

			//if clr is true
			if(tpins[0].getValue())
				for(int i=0; i<8; i++)
					data[i][address]=false;


			//if set is true
			if(tpins[1].getValue())
				for(int i=0; i<8; i++) {
					data[i][address] = pins[i].getValue();
				}

			//if select is true
			if(tpins[2].getValue())
				for(int i=0; i<8; i++) {
					opin[i].setValue(data[i][address]);
					opin[i].forwardValue();
				}
		}
	}


	public static class CouneterDummyGate extends BasicDummyGate{
		public CouneterDummyGate( int bits){
			super(1,0,0,bits);
			super.gateName= CounterGate.GATE_TYPE;

			for(DummyPin p:bpins)
				p.pinSignalType=Pin.OUTPUT;
		}

		int value=0;

		@Override
		public void Logic(){
			if(!pins[0].getValue())
				return;

			value++;
			boolean[] binary=CounterGate.intToBinary(value);
			int pin_loop=bpins.length;
			for(int i=binary.length-1; i>=0; i--){
				bpins[pin_loop-1].setValue(binary[i]);
				pin_loop--;
			}

			for(DummyPin p:bpins)
				p.forwardValue();
		}
	}//end of class


	public static class ICGateDummy extends BasicDummyGate{
		private IntegratedCircuitParser parser; // ic parser instance
		private String projectName; // project name of the i
		private DummyConnectionMaker connectionMaker;

		private Map<DummyPin, DummyPin> pinMapping;

		// private List<BasicDummyGate>
		List<BasicDummyGate> inputGates;
		private Map<String , BasicDummyGate> gateIDMap;
		private List<BasicDummyGate> dummyGates; // List to store all Dummygates inside ic project
		public ICGateDummy( IntegratedCircuitParser parser){
			super( parser.getLeftPinElements().size(),parser.getRightPinElements().size(),parser.getTopPinElements().size(),parser.getBottomPinElements().size());
			super.gateName="ICgateDummy";

			this.parser=parser;
			projectName=parser.getName();

			intializeGlobalVarables();
			addDummyGates();
			mapAllPins();
			connectionMaker.makeConnections(this);
		}

		@Override
		public void Logic(){

		}

		private void intializeGlobalVarables(){
			this.dummyGates=new ArrayList<>();
			this.gateIDMap=new HashMap<>();
			this.pinMapping=new LinkedHashMap<>();
			connectionMaker=new DummyConnectionMaker();
			this.inputGates=new ArrayList<>();
		}

		private  void addDummyGates(){
			List<GateElement> elements=parser.getAllGateElement();
			//System.out.println("Total Dummy Elements count:"+elements.size());
			GateParser gateParser=new GateParser();
			//System.out.println("GateParser object created starting for loop to add DummyGates");
			for(GateElement gateElement:elements){
				BasicDummyGate dummyGate=gateParser.getGate(gateElement.getType() ,
						gateElement.getLeftPinsCount() ,
						gateElement.getRightPinsCount() , gateElement.getTopPinsCount(), gateElement.getBottomPinsCount()  , gateElement.getProjectName());
				if(dummyGate==null)
					continue;
				this.gateIDMap.put(gateElement.getStringID(),  dummyGate);
				dummyGate.setConnetionMaker(connectionMaker);
				dummyGate.setElement(gateElement);
				dummyGates.add(dummyGate);
			}//end of for

			System.out.println("ICGateDummy for loop  end with adding  DummyGates :"+dummyGates.size());
		} // end of  addDummyGates


		private void mapAllPins(){
			//mapping pins
			mapPinsByOrient(parser.getLeftPinElements() , pins);
			mapPinsByOrient(parser.getRightPinElements() , opin);
			mapPinsByOrient(parser.getTopPinElements() , tpins);
			mapPinsByOrient(parser.getBottomPinElements() , bpins);

		}


		private int  mapPinsByOrient(List<LinkPinElement> pinElements , DummyPin[] pin_array){
			int count=0;
			for(int i=0; i<pin_array.length; i++){
				DummyPin pin=pin_array[i];

				DummyPin p=getPin(pinElements.get(i) , gateIDMap);
				if(p==null) {
					continue;
				}

				//System.out.println("PIN DATA : PARENT:"+p.parent);

				pin.pinSignalType=p.pinSignalType;

				if(pin.pinSignalType==Pin.INPUT) {
					pin.setOnValueChange(new BasicTask() {
						@Override
						public void run() {
							p.setValue(pin.getValue());
						}
					});

				}

				if(pin.pinSignalType==Pin.OUTPUT) {
					p.setPin(pin);
					//System.out.println("OUTPUT IS SET FOR PIN PARENT "+p.parent.gateName);
				}

				pinMapping.put(pin , p);

				if(p.pinSignalType==Pin.INPUT && !inputGates.contains(p.parent))
					inputGates.add(p.parent);

				count++;
			}

			if(count!=pins.length){
				System.out.println("ICGateDummy:"+parser.getName()+" WARNING :"+(pins.length-count)+" pin mapping is failed  , mapped pin not found");
			}
			return count;
		}//end of  mapPinsByOrient


		public static DummyPin getPin(LinkPinElement pinElement , Map<String,BasicDummyGate> gateMap){

			String parentid=pinElement.getParentID();
			BasicDummyGate gate=gateMap.get(parentid);

			int orient=pinElement.getOrient();
			int index=pinElement.getPinIndex();

			DummyPin pin=null;

			if(gate==null){
				System.err.println("ICGATEDUMMY->getPin(..)-> BasicDummyGate is NULL , WRONG ID");
				return null;
			}
			else {
				pin=gate.getDummyPin(orient , index);
				if(pin==null)
					System.err.println("ICGATEDUMMY->getPin(..)-> BasicDummyGate retuned NULL DummyPin");
			}
           return pin;
		}

		public BasicDummyGate getGateByID(String id){
			return gateIDMap.get(id);
		}

	}//end of class

	public static class TestGate extends  BasicDummyGate{

		public  TestGate( int input){
			super( input , 1,1 ,1);
		}

		@Override
		public void Logic(){

		}
	}//end of TestGate
}