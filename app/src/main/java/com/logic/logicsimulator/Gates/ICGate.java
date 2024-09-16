package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.BasicTask;
import com.logic.logicsimulator.DummyGates.BasicDummyGate;
import com.logic.logicsimulator.DummyGates.DummyPin;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;
import com.logic.logicsimulator.parsers.ConnectionElement;
import com.logic.logicsimulator.parsers.DummyConnectionMaker;
import com.logic.logicsimulator.parsers.GateElement;
import com.logic.logicsimulator.parsers.IntegratedCircuitParser;
import com.logic.logicsimulator.parsers.LinkPinElement;
import com.logic.logicsimulator.parsers.PinElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ICGate extends BasicGateView {
    public static final String IC_GATE_NAME="ICGate";
    private IntegratedCircuitParser parser; // ic parser instance
    private String projectName; // project name of the ic

    private DummyConnectionMaker connectionMaker;

    private Map<Pin, DummyPin> pinMapping;

   // private List<BasicDummyGate>
    List<BasicDummyGate> inputGates;
    private Map<String , BasicDummyGate> gateIDMap;
    private List<BasicDummyGate> dummyGates; // List to store all Dummygates inside ic project


    //Constructer of ICGate
    public ICGate(Context context, EditorLayout layout  , IntegratedCircuitParser parser){
        super(context,layout,parser.getElementListByOrint(Pin.LEFT_PIN).size(),
                             parser.getElementListByOrint(Pin.RIGHT_PIN).size(),
                             parser.getElementListByOrint(Pin.TOP_PIN).size(),
                             parser.getElementListByOrint(Pin.BOTTOM_PIN).size());

        super.gateName=parser.getName();//setting gateName as the project name
        super.gateType=IC_GATE_NAME;// setting the type og Gate it is  , it is ICGate

        this.parser=parser;   //initializing  IntegratedCircuitParser parser
        projectName=parser.getName();

        //initializing dummy gates arraylist and dummy gate map
        intializeGlobalVarables();
        System.out.println("*************ICgate log : "+projectName+"**************");
        addDummyGates();
        mapAllPins();
        connectionMaker.makeConnections(this);
        System.out.println("inputGates :"+inputGates.size());
        System.out.println("**********IC GATE LOG END : "+projectName+" *********");

    }// end if ICgate constructer


    public String getProjectName(){
        return  projectName;
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
        System.out.println("Total Dummy Elements count:"+elements.size());
        GateParser gateParser=new GateParser();
        System.out.println("GateParser object created starting for loop to add DummyGates");
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

        System.out.println(" for loop  end with adding  DummyGates :"+dummyGates.size());
    } // end of  addDummyGates




    private int  mapPinsByOrient(List<LinkPinElement> pinElements , Pin[] pin_array){
        int count=0;
        for(int i=0; i<pin_array.length; i++){
            Pin pin=pin_array[i];

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

        System.out.println("PIN MAPS:"+count);
        if(count!=pins.length){
            System.out.println("ICGate:"+getProjectName()+" WARNING :"+(pins.length-count)+" pin mapping is failed  , mapped pin not found");
        }
        return count;
    }//end of  mapPinsByOrient


    private void mapAllPins(){
        //mapping pins
        mapPinsByOrient(parser.getLeftPinElements() , pins);
        mapPinsByOrient(parser.getRightPinElements() , opin);
        mapPinsByOrient(parser.getTopPinElements() , tpins);
        mapPinsByOrient(parser.getBottomPinElements() , bpins);

    }



    @Override
    public void drawicon(RectF r , Canvas canvas, Paint p ){
        int width=80;
        int height=90;
        Rect re=new Rect((int)r.left,(int)r.top,(int)r.right,(int)r.bottom);
        int ax=re.left+(re.width()-(width))/2;
        int ay=re.top+(re.height() - height)/2;
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xffffffff);
        p.setStrokeWidth(2);
        p.setTextSize(35);
       canvas.drawText(super.gateName,ax,ay+50,p);
    }

    @Override
    public void Logic(){
        for(BasicDummyGate gate:inputGates)
            gate.Logic();
    }//end of Logic

    public int getGateCount(){
        return  dummyGates.size();
    }


    public static DummyPin getPin(LinkPinElement pinElement , Map<String , BasicDummyGate> gateMap){

         String parentid=pinElement.getParentID();
         BasicDummyGate gate=gateMap.get(parentid);

        int orient=pinElement.getOrient();
        int index=pinElement.getPinIndex();

         if(gate!=null)
             return gate.getDummyPin(orient , index);
         else {
             System.err.println("ICGATE->getPin(..)-> BasicDummyGate retuned NULL DummyPin");
             return null;
         }

    }




    public BasicDummyGate getGateByID(String id){
        return gateIDMap.get(id);
    }


    public DummyConnectionMaker getConnectionMaker(){
        return this.connectionMaker;
    }
    public void addConnection(DummyPin pin , ConnectionElement element){
        connectionMaker.addConnection(pin , element);
    }

}
