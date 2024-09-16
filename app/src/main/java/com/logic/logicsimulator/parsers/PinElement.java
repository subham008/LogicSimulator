package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.Pin;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


public class PinElement {

   private Element pinElement;
   private String error=null;

   private List<ConnectionElement> connections;
    public PinElement(@NonNull Element element){
          if(isPinElement(element)) {
              pinElement = element;
              connections=new ArrayList<>();
              parseAllConnection();
          }
          else {
              error=" Error : passed element is not a PinElement , it does not have required Attribute pinname";
              System.err.println( "PinElement(Element) Constructer ()-> "+error);
          }
    }


    public boolean isElement(){
        if(pinElement!=null)
            return true;

        return false;
    }
    public PinElement(@NonNull Pin pin , @NonNull Document document){
        //creting element from pin
        try {
            Element element = document.createElement(PIN_ELEMNT_NAME);
            if(element!=null){
                String gateid=pin.parent.getGateID();
                if(gateid==null)
                    gateid="NO_ID";
                element.setAttribute(PinElement.ATTRIBUTE_PARENTID,gateid);
                element.setAttribute(PinElement.ATTRIBUTE_PIN_ORIENT ,String.valueOf(pin.pinOreintation));
                element.setAttribute(PinElement.ATTRIBUTE_PIN_INDEX, String.valueOf(pin.pinIndex));
                element.setAttribute(PinElement.ATTRIBUTE_PIN_NAME, pin.getPinName());

                this.pinElement=element;
                connections=new ArrayList<>();
            }
            else{
                System.err.println("PinElement(Pin , Document) -> Error : failed to create Element from passed Document Object , and no any Exception also occured");
            }
        }
        catch (Exception e){
            System.err.println("PinElement(Pin , Document) -> Error : "+e);
        }
    }





    public PinElement(@NonNull String parentid, @NonNull String pin_orient , @NonNull String pin_index , @NonNull String pin_name , @NonNull Document document){
        //creting element from pin
        try {
            Element element = document.createElement(PIN_ELEMNT_NAME);
            if(element!=null){
                pinElement.setAttribute(PinElement.ATTRIBUTE_PARENTID,parentid);
                pinElement.setAttribute(PinElement.ATTRIBUTE_PIN_ORIENT ,pin_orient);
                pinElement.setAttribute(PinElement.ATTRIBUTE_PIN_INDEX, pin_index);
                pinElement.setAttribute(PinElement.ATTRIBUTE_PIN_NAME, pin_name);

                this.pinElement=element;
                connections=new ArrayList<>();
            }
            else{
                System.err.println("PinElement(String , String , String , String ,Document) -> Error : failed to create Element from passed Document Object , and no any Exception also occured");
            }
        }
        catch (Exception e){
              System.err.println("PinElement(String , String , String , String , Document) -> Error : "+e);
        }


    }




    public  ConnectionElement addConnection(@NonNull Pin from , @NonNull Pin to , @NonNull Document document){
       ConnectionElement con=null;
        try{
            con=new ConnectionElement(from , to , document);
            connections.add(con);
            pinElement.appendChild(con.getElement()); //adding to pinElement

        }catch (Exception e){
            System.err.println("addConnection(Pin , Pin , Document)->ERROR : "+e);
        }

        return con;
    }

    public  boolean removeConnection(int index ){
        boolean status=false;
          try{
              ConnectionElement con= connections.get(index);
              pinElement.removeChild(con.getElement());
              connections.remove(index);
              status=true;
          }catch (Exception e){
              System.err.println("removeConnection(int)->ERROR :"+e);
          }
        return status;
    }

    public  boolean removeConnection(ConnectionElement element ){
        boolean status=false;
        try{
            connections.remove(element);
            pinElement.removeChild(element.getElement());
            status=true;
        }catch (Exception e){
            System.err.println("removeConnection(int)->ERROR :"+e);
        }
        return status;
    }



    public  boolean removeAllConnection( ){
        boolean status=false;
        try{

            for(ConnectionElement con:connections){
                pinElement.removeChild(con.getElement());
            }

            connections.clear();
            status=true;
        }catch (Exception e){
            System.err.println("removeConnection(int)->ERROR :"+e);
        }
        return status;
    }




    //return Integer, number of Connection found
    private  int parseAllConnection(){
        int count=0;
        if(pinElement!=null){
            NodeList list=pinElement.getElementsByTagName(ConnectionElement.CONNECTION_ELEMENT_NAME);

            for(int i=0; i<list.getLength(); i++){
                Node node=list.item(i);
                if(  node.getNodeType()==Node.ELEMENT_NODE &&  ConnectionElement.isConnectionElement((Element) node)){
                    connections.add(new ConnectionElement((Element) node));
                    count++;
                }
            }
        }


        return count;
    }



    public Element getElement(){
         return pinElement;
    }

    public List<ConnectionElement> getConnections(){
        return this.connections;
    }

    //returns null if failed
    public  String getParentID(){
          if(pinElement==null){
              if(error!=null)
                  System.err.println("PinElement getParentID()->"+error);
              else
                  System.err.println("PinElement getParentID()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
              return null;
          }

          Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PARENTID);
          if(parentidAttr==null){
              System.err.println("PinElement getParentID()->"+"Attribute parentid not found  , Somehow  pass the  isPinElement() , Source problem ");
              return null;
          }

          return parentidAttr.getValue();
    }

    //returns true on success , false if failed , and print error on console
    public  boolean setParentID(String value){
        if(pinElement==null){
            if(error!=null)
                System.err.println("PinElement setParentID()->"+error);
            else
                System.err.println("PinElement setParentID()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return false;
        }

        Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PARENTID);
        if(parentidAttr==null){
            System.err.println("PinElement setParentID()->"+"Attribute parentid not found  , Somehow  pass the  isPinElement() , Source problem ");
            return false;
        }

        parentidAttr.setValue(value);

        return true;
    }







    //returns null if failed
    public  String getPinOrient(){
        if(pinElement==null){
            if(error!=null)
                System.err.println("PinElement getPinOreint()->"+error);
            else
                System.err.println("PinElement getPinOreint()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return null;
        }

        Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PIN_ORIENT);
        if(parentidAttr==null){
            System.err.println("PinElement getPinOreint()->"+"Attribute pin_orient not found  , Somehow  pass the  isPinElement() , Source problem ");
            return null;
        }

        return parentidAttr.getValue();
    }

    //returns true on success , false if failed , and print error on console
    public  boolean setPinOrient(String value){
        if(pinElement==null){
            if(error!=null)
                System.err.println("PinElement setPinOrient->"+error);
            else
                System.err.println("PinElement setPinOrient->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return false;
        }

        Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PIN_ORIENT);
        if(parentidAttr==null){
            System.err.println("PinElement setPinOrient->"+"Attribute pin_orient not found  , Somehow  pass the  isPinElement() , Source problem ");
            return false;
        }

        parentidAttr.setValue(value);

        return true;
    }







    //returns null if failed
    public  String getPinIndex(){
        if(pinElement==null){
            if(error!=null)
                System.err.println("PinElement getPinIndex()->"+error);
            else
                System.err.println("PinElement getPinIndex()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return null;
        }

        Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PIN_INDEX);
        if(parentidAttr==null){
            System.err.println("PinElement getPinIndex()->"+"Attribute pinindex not found  , Somehow  pass the  isPinElement() , Source problem ");
            return null;
        }

        return parentidAttr.getValue();
    }

    //returns true on success , false if failed , and print error on console
    public  boolean setPinIndex(String value){
        if(pinElement==null){
            if(error!=null)
                System.err.println("PinElement setPinIndex()->"+error);
            else
                System.err.println("PinElement setPinIndex()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return false;
        }

        Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PIN_INDEX);
        if(parentidAttr==null){
            System.err.println("PinElement setPinIndex()->"+"Attribute pinindex not found  , Somehow  pass the  isPinElement() , Source problem ");
            return false;
        }

        parentidAttr.setValue(value);

        return true;
    }







    //returns null if failed
    public  String getPinName(){
        if(pinElement==null){
            if(error!=null)
                System.err.println("PinElement getPinName()->"+error);
            else
                System.err.println("PinElement getPinName()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return null;
        }

        Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PIN_NAME);
        if(parentidAttr==null){
            System.err.println("PinElement getPinName()->"+"Attribute pinname not found  , Somehow  pass the  isPinElement() , Source problem ");
            return null;
        }

        return parentidAttr.getValue();
    }

    //returns true on success , false if failed , and print error on console
    public  boolean setPinName(String value){
        if(pinElement==null){
            if(error!=null)
                System.err.println("PinElement setPinName()->"+error);
            else
                System.err.println("PinElement setPinName()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return false;
        }

        Attr parentidAttr=pinElement.getAttributeNode(ATTRIBUTE_PIN_NAME);
        if(parentidAttr==null){
            System.err.println("PinElement setPinName()->"+"Attribute pinname not found  , Somehow  pass the  isPinElement() , Source problem ");
            return false;
        }

        parentidAttr.setValue(value);

        return true;
    }



    public  static boolean isPinElement(Element element){
        // each pin element have 4 attributes
        // if Element have all 4 Attribute  then we consider it as a valid PinElement
        Attr parentidAttr=element.getAttributeNode(ATTRIBUTE_PARENTID);
        Attr pinorientAttr=element.getAttributeNode(ATTRIBUTE_PIN_ORIENT);
        Attr pinindexAttr=element.getAttributeNode(ATTRIBUTE_PIN_INDEX);
        Attr pinnameAttr=element.getAttributeNode(ATTRIBUTE_PIN_NAME);


        if(parentidAttr!=null && pinorientAttr!=null && pinindexAttr!=null && pinnameAttr!=null && element.getNodeName().compareTo(PIN_ELEMNT_NAME)==0 )
            return  true;

        return  false;
    }

    public static final String PIN_ELEMNT_NAME="Pin";

    public  static  final String ATTRIBUTE_PARENTID="parentid";
    public  static  final String ATTRIBUTE_PIN_ORIENT="pin_orient";

    public  static  final String ATTRIBUTE_PIN_INDEX="pinindex";
    public  static  final String ATTRIBUTE_PIN_NAME="pinname";
}
