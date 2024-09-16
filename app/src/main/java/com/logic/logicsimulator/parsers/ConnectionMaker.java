package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

import java.util.ArrayList;
import java.util.List;

public class ConnectionMaker {

    private List<ConnectionElement> connectionElements;
    private List<Pin>  pinList;

    public ConnectionMaker(){
        connectionElements=new ArrayList<>();
        pinList=new ArrayList<>();
    }
    public  void addConnection(@NonNull Pin pin ,@NonNull ConnectionElement element){
        connectionElements.add(element);
        pinList.add(pin);
    }


    public int makeConnections(@NonNull EditorLayout layout){
        int count=0;
        for(int i=0; i<connectionElements.size(); i++) {
            try {
                ConnectionElement element=connectionElements.get(i);
                Pin from=pinList.get(i);
                BasicGateView from_pin_parent=from.parent;
                //finding to pin
                BasicGateView to_pin_parent=layout.getGateByID(element.getParentID());
                int orient= Integer.parseInt(element.getPinOrient());
                int index=Integer.parseInt(element.getPinIndex());

                Pin to=to_pin_parent.getPin(orient , index);

                from_pin_parent.setPreConnection(from ,Pin.LEFT_PIN , to, Pin.RIGHT_PIN , element);
                count++;
            } catch (Exception e) {
                PinElement element=pinList.get(i).element;
                element.removeConnection(connectionElements.get(i));
            }
        }
        return count;
    }




    public int getConnectionCount(){
        return connectionElements.size();
    }
}
