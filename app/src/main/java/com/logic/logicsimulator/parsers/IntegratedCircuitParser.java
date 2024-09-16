package com.logic.logicsimulator.parsers;


import android.graphics.Color;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IntegratedCircuitParser {

    private Document document=null;
    private Element icElement=null;
    //List<Element> gateElements=new ArrayList<>();
    private List<LinkPinElement> LeftPinElements=new ArrayList<>(); Element leftElement=null;
    private List<LinkPinElement> RightPinElements=new ArrayList<>();Element rightElement=null;
    private List<LinkPinElement> TopPinElements=new ArrayList<>();Element topElement=null;
    private List<LinkPinElement> BottomPinElements=new ArrayList<>(); Element bottomElement=null;

    private EditorLayout layout;

    private  Map<Pin , LinkPinElement> pinMap;
    private List<GateElement> elementList=new ArrayList<>();

    private String projectName=null;

    //this constructer is for project want to open its own IntegratedCircuitParser
    public IntegratedCircuitParser(@NonNull Document doc , String proName , @NonNull EditorLayout layout){
        this.document=doc;
        projectName=proName;
        this.layout=layout;
        this.icElement=getICelment(document , projectName);
        pinMap=new HashMap<>();
        parsePinElement();

    }

    //this constructer is for project want to open its as a ICgate
    public IntegratedCircuitParser( String projectName , File dir){
        this.document=LogicProject.isLogicProject(new File(dir , projectName));
        this.projectName=projectName;
        this.icElement=getICelment(document , projectName);
        pinMap=new HashMap<>();
        parsePinElement();
        parseGateElements();
    }


    public static Element getIcelement(@NonNull Document document){
        Element rootElement=(Element) document.getFirstChild();
        NodeList nodeList=rootElement.getElementsByTagName(ICELEMENT_NAME);
        if(nodeList.getLength()>0){
            Element icElement=(Element) nodeList.item(0);
            return icElement;
        }

        return null;
    }
    public static Element getICelment(@NonNull Document document , String projectName ){
        Element rootElement=(Element) document.getFirstChild();
        NodeList nodeList=rootElement.getElementsByTagName(ICELEMENT_NAME);
        if(nodeList.getLength()>0){
            Element icElement=(Element) nodeList.item(0);
            return icElement;
        }
        else {
            Element element=createICelement(document , projectName);
            rootElement.appendChild(element);
            return  element;
        }
    }

    public static Element createICelement( @NonNull Document document , String projectName ){
        Element element=null;
        try{
             element = document.createElement(ICELEMENT_NAME);
            //setting attributes
            element.setAttribute(LEFT_PIN_NAME,"0");
            element.setAttribute(RIGHT_PIN_NAME,"0");
            element.setAttribute(TOP_PIN_NAME,"0");
            element.setAttribute(BOTTOM_PIN_NAME,"0");
            element.setAttribute(IC_NAME,projectName);

            element.appendChild(document.createElement(LEFT_PIN_NAME));
            element.appendChild(document.createElement(RIGHT_PIN_NAME));
            element.appendChild(document.createElement(TOP_PIN_NAME));
            element.appendChild(document.createElement(BOTTOM_PIN_NAME));

        }
        catch (Exception e){
            System.err.println( "IntegratedCircuitParser->createIcelement ERROR"+e.toString());
        }

        return element;
    }


    private void parsePinElement(){
        LeftPinElements=new ArrayList<>();
        RightPinElements=new ArrayList<>();
        TopPinElements=new ArrayList<>();
        BottomPinElements=new ArrayList<>();
        try {
            //parsing Gates
           if(icElement==null)
               return;

           leftElement=(Element) icElement.getElementsByTagName(LEFT_PIN_NAME).item(0);
           rightElement=(Element) icElement.getElementsByTagName(RIGHT_PIN_NAME).item(0);
           topElement=(Element) icElement.getElementsByTagName(TOP_PIN_NAME).item(0);
           bottomElement=(Element) icElement.getElementsByTagName(BOTTOM_PIN_NAME).item(0);


           LeftPinElements=parseLinkPins(leftElement);
           RightPinElements=parseLinkPins(rightElement);
           TopPinElements=parseLinkPins(topElement);
           BottomPinElements=parseLinkPins(bottomElement);

        }
        catch (Exception e){
            System.err.println( "IntegratedCircuitParser->parseElement ERROR"+e.toString());
        }
    }


    private static List<LinkPinElement> parseLinkPins(@NonNull Element element){
        List<LinkPinElement> list=new ArrayList<>();


        NodeList nodeList=element.getChildNodes();
        for(int i=0; i<nodeList.getLength(); i++){
            Node node=nodeList.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE &&  LinkPinElement.isLinkPinElement((Element) node)){
                list.add(new LinkPinElement((Element) node));
            }
        }

        return list;
    }


    private void parseGateElements(){
        elementList=new ArrayList<>();
        elementList= LogicProject.parseGateElements(this.document);
    }

    public List<GateElement> getElementsList(){
        return elementList;
    }
    public boolean isIC(){
        if(icElement!=null && elementList.size()>0)
            return true;

        return  false;
    }


    public Element getElement(){
        return this.icElement;
    }

    public Map<Pin , LinkPinElement> getPinMap(){
        return pinMap;
    }

    public  LinkPinElement createLinkPinElement(Pin pin , int oreint){
       return new LinkPinElement(document , pin , oreint);
    }


    public List<LinkPinElement> getLeftPinElements(){
        return  LeftPinElements;
    }
    public List<LinkPinElement> getRightPinElements(){
        return  RightPinElements;
    }
    public List<LinkPinElement> getTopPinElements(){
        return  TopPinElements;
    }
    public List<LinkPinElement> getBottomPinElements(){
        return  BottomPinElements;
    }



    private void removePinfromMap(LinkPinElement element){
        Pin foundKey = null;
        for (Map.Entry<Pin ,LinkPinElement> entry : pinMap.entrySet()) {
            if (entry.getValue().equals(element)) {
                foundKey = entry.getKey();
                break; // Exit loop once key is found
            }
        }

   pinMap.remove(foundKey);
    }
    public void removeLinkPin(LinkPinElement element){
        int linkPinOrient=element.getLinkPinOrient();
        switch (linkPinOrient)
        {
            case Pin.LEFT_PIN:
                LeftPinElements.remove(element);
                leftElement.removeChild(element.getElement());
                increaseCount(LEFT_PIN_NAME , LeftPinElements.size());
                break;
            case  Pin.RIGHT_PIN:
                RightPinElements.remove(element);
                rightElement.removeChild(element.getElement());
                increaseCount(RIGHT_PIN_NAME , RightPinElements.size());
                break;

            case  Pin.TOP_PIN:
                TopPinElements.remove(element);
                topElement.removeChild(element.getElement());
                increaseCount(TOP_PIN_NAME , TopPinElements.size());
                break;

            case Pin.BOTTOM_PIN:
                BottomPinElements.remove(element);
                bottomElement.removeChild(element.getElement());
                increaseCount(BOTTOM_PIN_NAME , BottomPinElements.size());
                break;
        }

        Pin pin=element.getPin(this.layout);
        if(pin!=null){ pin.setDefaultColor(Color.WHITE);  pin.parent.invalidate();}


        removePinfromMap(element);

    }


    public void changePinOrient(LinkPinElement pinElement  , int orient){
        removeLinkPin(pinElement);
        addPin(pinElement , orient);
    }




    public List<LinkPinElement> getElementListByOrint(int orient){
        List<LinkPinElement> list=null;
        switch (orient){
            case Pin.LEFT_PIN:
                list=LeftPinElements;
                break;
            case Pin.RIGHT_PIN:
                list=RightPinElements;
                break;
            case Pin.TOP_PIN:
                list=TopPinElements;
                break;
            case Pin.BOTTOM_PIN:
                list=BottomPinElements;
                break;
        }

        return list;
    }


    public boolean addPin(Pin pin , int orient){


        LinkPinElement element=createLinkPinElement(pin , orient);

        switch (orient) {
            case Pin.LEFT_PIN:
                this.LeftPinElements.add(element);
                leftElement.appendChild(element.getElement());
                break;
            case Pin.RIGHT_PIN:
                this.RightPinElements.add(element);
                rightElement.appendChild(element.getElement());
                break;
            case Pin.TOP_PIN:
                this.TopPinElements.add(element);
                topElement.appendChild(element.getElement());
                break;
            case Pin.BOTTOM_PIN:
                this.BottomPinElements.add(element);
                bottomElement.appendChild(element.getElement());
                break;
        }


        return true;
    } //end of addPin

    private void increaseCount(String attr , int count){
        try{
             icElement.setAttribute(attr , String.valueOf(count));
        }
        catch (Exception e){
            System.out.println("IntegratedCircuitParser->increaseCount(String  , int ) : ERROR : "+e);
        }
    }
    public boolean addPin(Pin pin , LinkPinElement element , int orient){


        try {

            switch (orient) {
                case Pin.LEFT_PIN:
                    this.LeftPinElements.add(element);
                    leftElement.appendChild(element.getElement());
                    increaseCount(LEFT_PIN_NAME , LeftPinElements.size());
                    break;
                case Pin.RIGHT_PIN:
                    this.RightPinElements.add(element);
                    rightElement.appendChild(element.getElement());
                    increaseCount(RIGHT_PIN_NAME , RightPinElements.size());
                    break;
                case Pin.TOP_PIN:
                    this.TopPinElements.add(element);
                    topElement.appendChild(element.getElement());
                    increaseCount(TOP_PIN_NAME , TopPinElements.size());
                    break;
                case Pin.BOTTOM_PIN:
                    this.BottomPinElements.add(element);
                    bottomElement.appendChild(element.getElement());
                    increaseCount(BOTTOM_PIN_NAME , BottomPinElements.size());
                    break;
            }

            pinMap.put(pin, element);

        }
        catch (Exception e){
            System.err.println("IntegratedCircuitParser->addPin(Element,..):ERROR : "+e.toString());
        }

        return true;
    }//end of addPin

    private boolean addPin( LinkPinElement element , int orient){


        try {

            switch (orient) {
                case Pin.LEFT_PIN:
                    this.LeftPinElements.add(element);
                    leftElement.appendChild(element.getElement());
                    increaseCount(LEFT_PIN_NAME , LeftPinElements.size());
                    break;
                case Pin.RIGHT_PIN:
                    this.RightPinElements.add(element);
                    rightElement.appendChild(element.getElement());
                    increaseCount(RIGHT_PIN_NAME , RightPinElements.size());
                    break;
                case Pin.TOP_PIN:
                    this.TopPinElements.add(element);
                    topElement.appendChild(element.getElement());
                    increaseCount(TOP_PIN_NAME , TopPinElements.size());
                    break;
                case Pin.BOTTOM_PIN:
                    this.BottomPinElements.add(element);
                    bottomElement.appendChild(element.getElement());
                    increaseCount(BOTTOM_PIN_NAME , BottomPinElements.size());
                    break;
            }


        }
        catch (Exception e){
            System.err.println("IntegratedCircuitParser->addPin(Element,..):ERROR : "+e.toString());
        }

        return true;
    }//end of addPin


    public String getName(){
        String name=icElement.getAttribute(IntegratedCircuitParser.IC_NAME);
        if(name==null)
            name="NULL_NAME_ELEMENT";
        if(name.length()==0)
            name="NO_NAME_ELEMENT";

        return  name;
    }//end of getName

 public LinkPinElement getLinkPin(@NonNull  Pin pin){
       return pinMap.get(pin);
 }


    public static boolean isContains(Element parent , Element child){
        NodeList list=parent.getChildNodes();

        if(list.getLength()>0){
            for(int i=0 ; i<list.getLength(); i++){
               Node node=list.item(i);
               if(node.getNodeType()==Node.ELEMENT_NODE &&  ((Element)node)==child )
                   return true;
            }
        }

        return false;
    }


    public void parsePinMap(@NonNull EditorLayout layout){
        try{
            for (LinkPinElement element : getLeftPinElements()) {
                Pin pin=element.getPin(layout);
                if(pin!=null)
                    pinMap.put(pin,element);
            }
            for (LinkPinElement element : getRightPinElements()) {
                Pin pin=element.getPin(layout);
                if(pin!=null)
                    pinMap.put(pin,element);

            }
            for (LinkPinElement element : getTopPinElements()) {
                Pin pin=element.getPin(layout);
                if(pin!=null)
                    pinMap.put(pin,element);

            }
            for (LinkPinElement element : getBottomPinElements()) {
                Pin pin=element.getPin(layout);
                if(pin!=null)
                    pinMap.put(pin,element);

            }

            System.out.println("IntegratedParser PIN MAP PARSED : "+pinMap.size());
        }
        catch (Exception e){
            System.err.println("IntegratedCircuitParser->parsePinMap(EditorLayout) :ERROR : "+e.toString());
        }
    }

    public List<GateElement> getAllGateElement(){
        return elementList;
    }



    public static boolean isIntegrated(@NonNull Document document ){
        boolean status=false;
        try {
            Element icElement = getIcelement(document);
            if(icElement==null)
                System.err.println("IntegratedCircuitParser->isIntegrated(Document) ->ERROR : icElement Element not found in Document");

            int left=Integer.parseInt(icElement.getAttribute(LEFT_PIN_NAME));
            int right=Integer.parseInt(icElement.getAttribute(RIGHT_PIN_NAME));
            int top=Integer.parseInt(icElement.getAttribute(TOP_PIN_NAME));
            int bottom=Integer.parseInt(icElement.getAttribute(BOTTOM_PIN_NAME));

            if(left >0 || right>0 || top>0 || bottom>0)
                status=true;
        }
        catch (Exception e){
            System.err.println( "IntegratedCircuitParser->isIntegrated(Document) ->ERROR :"+e);
        }

        return status;
    }
   //ic element attributes

    public static final String ICELEMENT_NAME="icelement";
    public static final String LEFT_PIN_NAME="LeftPin";
    public static final String RIGHT_PIN_NAME="RightPin";
    public static final String TOP_PIN_NAME="TopPin";
    public static final String BOTTOM_PIN_NAME="BottomPin";
    public static final String IC_NAME="name";



}
