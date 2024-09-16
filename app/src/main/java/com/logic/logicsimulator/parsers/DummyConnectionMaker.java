package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.DummyGates.BasicDummyGate;
import com.logic.logicsimulator.DummyGates.DummyGates;
import com.logic.logicsimulator.DummyGates.DummyPin;
import com.logic.logicsimulator.Gates.ICGate;

import java.util.ArrayList;
import java.util.List;

public class DummyConnectionMaker {

    private List<ConnectionElement> connectionElements;
    private List<DummyPin>  pinList;


    public DummyConnectionMaker(){
        connectionElements=new ArrayList<>();
        pinList=new ArrayList<>();
    }


    public  void addConnection(@NonNull DummyPin pin , @NonNull ConnectionElement element){
        connectionElements.add(element);
        pinList.add(pin);
    }

    public void removeConnection(@NonNull DummyPin pin , @NonNull ConnectionElement element){
        connectionElements.remove(element);
        pinList.remove(pin);
    }

    public int makeConnections(@NonNull ICGate gate){
        int count=0;
        for(int i=0; i<connectionElements.size(); i++) {
            try {
                ConnectionElement element=connectionElements.get(i);
                DummyPin from=pinList.get(i);

                //finding to pin
                BasicDummyGate to_pin_parent=gate.getGateByID(element.getParentID());
                int orient= Integer.parseInt(element.getPinOrient());
                int index=Integer.parseInt(element.getPinIndex());

                DummyPin to=to_pin_parent.getDummyPin(orient , index);

                BasicDummyGate.setConnectionPoints(from  , to);
               // System.out.println("DunmyConnectionMaker WORK DATA : from :"+from.parent.gateName+" to :"+to.parent.gateName);

                count++;
            } catch (Exception e) {
                System.out.println("DummyConnectionMaker->makeConnection(..)->ERROR  : "+e);
            }
        }

        System.out.println("DummyConnectionMaker->Estimated Connections :"+connectionElements.size()+" Successs : "+count);

        return count;
    }



    public int makeConnections(@NonNull DummyGates.ICGateDummy gate){
        int count=0;
        for(int i=0; i<connectionElements.size(); i++) {
            try {
                ConnectionElement element=connectionElements.get(i);
                DummyPin from=pinList.get(i);

                //finding to pin
                BasicDummyGate to_pin_parent=gate.getGateByID(element.getParentID());
                int orient= Integer.parseInt(element.getPinOrient());
                int index=Integer.parseInt(element.getPinIndex());

                DummyPin to=to_pin_parent.getDummyPin(orient , index);

                BasicDummyGate.setConnectionPoints(from  , to);
                // System.out.println("DunmyConnectionMaker WORK DATA : from :"+from.parent.gateName+" to :"+to.parent.gateName);

                count++;
            } catch (Exception e) {
                System.out.println("DummyConnectionMaker->makeConnection(..)->ERROR  : "+e);
            }
        }

        System.out.println("DummyConnectionMaker->Estimated Connections :"+connectionElements.size()+" Successs : "+count);

        return count;
    }




    public int getConnectionCount(){
        return connectionElements.size();
    }

}
