package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.Pin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class GateElement {

    private Element gateElement;

    private List<PinElement> leftPinElements=null; Element LeftElement=null;
    private List<PinElement> RightPinElements=null; Element RightElement=null;
    private List<PinElement> TopPinElements=null; Element TopElement=null;
    private List<PinElement> BottomPinElement=null; Element BottomElement=null;




    public GateElement(@NonNull Element element){
        if(isGateElement(element)) {
            this.gateElement = element;
            initialize();
            parsePinElements();
        }
        else
            System.err.println("GateElement->GateElement(Element)->error: PASSED ELEMENT OBJECT IS NOT A GATE ELEMENT");
    }



    public GateElement(@NonNull BasicGateView gate){
           try{
               Document document=gate.parent_layout.getDocument();
               Element gate_Element=document.createElement(gate.gateType);
               gate_Element.setAttribute(ATTRIBUTE_ELEMENT_NAME, gate.gateName);
               gate_Element.setAttribute(ATTRIBUTE_PARENT_ID, gate.getGateID());
               gate_Element.setAttribute(ATTRIBUTE_X_LOC,String.valueOf((int)gate.getX()));
               gate_Element.setAttribute(ATTRIBUTE_Y_LOC,String.valueOf((int)gate.getY()));
               gate_Element.setAttribute(ATTRIBUTE_LEFT_PIN , String.valueOf(gate.getLeftPinsCount()));
               gate_Element.setAttribute(ATTRIBUTE_RIGHT_PIN , String.valueOf(gate.getRightPinsCount()));
               gate_Element.setAttribute(ATTRIBUTE_TOP_PIN, String.valueOf(gate.getTopPinsCount()));
               gate_Element.setAttribute(ATTRIBUTE_BOTTOM_PIN , String.valueOf(gate.getBottomPinsCount()));

               LeftElement=document.createElement(ELEMENT_LEFT_PIN);
               RightElement=document.createElement(ELEMENT_RIGHT_PIN);
               TopElement=document.createElement(ELEMENT_TOP_PIN);
               BottomElement=document.createElement(ELEMENT_BOTTOM_PIN);

               leftPinElements=new ArrayList<>(); createPinElements(leftPinElements , LeftElement , gate.pins, document);
               RightPinElements=new ArrayList<>(); createPinElements(RightPinElements , RightElement , gate.opin, document);
               TopPinElements=new ArrayList<>();    createPinElements(TopPinElements , TopElement, gate.tpins, document);
               BottomPinElement=new ArrayList<>();  createPinElements(BottomPinElement , BottomElement, gate.bpins, document);

               gate_Element.appendChild(LeftElement);
               gate_Element.appendChild(RightElement);
               gate_Element.appendChild(TopElement);
               gate_Element.appendChild(BottomElement);

               this.gateElement=gate_Element;
           }catch (Exception e){
               System.err.println("GateElement(BasicGateView)->error :"+e );
           }
    }


    public Element getElement(){

        return gateElement;

    }



    public boolean isValid(){
        if(gateElement!=null)
            return true;

        return false;
    }


    public static boolean isGateElement(@NonNull  Element element){
        if(element.hasAttribute(ATTRIBUTE_PARENT_ID) && element.hasAttribute(ATTRIBUTE_X_LOC) && element.hasAttribute(ATTRIBUTE_Y_LOC) ){
            return true;
        }
        return false;
    }// end of isGateElement


    private static void createPinElements(List<PinElement> pinElements, Element element , Pin[] pins , Document document){
           for(Pin pin:pins){
               PinElement pinElement=new PinElement(pin , document);
               element.appendChild(pinElement.getElement());
               pinElements.add(pinElement);
           }
    }

    public void initialize(){
        this.leftPinElements=new ArrayList<>();
        this.RightPinElements=new ArrayList<>();
        this.TopPinElements=new ArrayList<>();
        this.BottomPinElement=new ArrayList<>();
    }


    private void parsePinElements(){

         if(getLeftPinsCount()>0){
             Element element=(Element)gateElement.getElementsByTagName(ELEMENT_LEFT_PIN).item(0);
             if(element!=null){
                 LeftElement=element;
                 NodeList pin_elements=element.getElementsByTagName(PinElement.PIN_ELEMNT_NAME);
                 for(int i=0; i<pin_elements.getLength(); i++){
                     if(PinElement.isPinElement((Element) pin_elements.item(i)))
                          this.leftPinElements.add(new PinElement((Element) pin_elements.item(i)));
                 }
             }
         }


        if(getRightPinsCount()>0){
            Element element=(Element)gateElement.getElementsByTagName(ELEMENT_RIGHT_PIN).item(0);
            if(element!=null){
                RightElement=element;
                NodeList pin_elements=element.getElementsByTagName(PinElement.PIN_ELEMNT_NAME);
                for(int i=0; i<pin_elements.getLength(); i++){
                    if(PinElement.isPinElement((Element) pin_elements.item(i)))
                         this.RightPinElements.add(new PinElement((Element) pin_elements.item(i)));
                }
            }
        }


        if(getTopPinsCount()>0){
            Element element=(Element)gateElement.getElementsByTagName(ELEMENT_TOP_PIN).item(0);
            if(element!=null){
                TopElement=element;
                NodeList pin_elements=element.getElementsByTagName(PinElement.PIN_ELEMNT_NAME);
                for(int i=0; i<pin_elements.getLength(); i++){
                    if(PinElement.isPinElement((Element) pin_elements.item(i)))
                        this.TopPinElements.add(new PinElement((Element) pin_elements.item(i)));
                }
            }
        }


        if(getBottomPinsCount()>0){
            Element element=(Element)gateElement.getElementsByTagName(ELEMENT_BOTTOM_PIN).item(0);
            if(element!=null){
                BottomElement=element;
                NodeList pin_elements=element.getElementsByTagName(PinElement.PIN_ELEMNT_NAME);
                for(int i=0; i<pin_elements.getLength(); i++){
                    if(PinElement.isPinElement((Element) pin_elements.item(i)))
                         this.BottomPinElement.add(new PinElement((Element) pin_elements.item(i)));
                }
            }
        }


    }//end if parsePinElements




  public String getType(){
        if(gateElement!=null)
          return gateElement.getNodeName();
        else
            System.out.println("GateElement->getType()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

      return null;
  }


  public void addtoList(int orient , PinElement element){
        switch (orient){
            case Pin.LEFT_PIN:
                if(leftPinElements==null)
                    leftPinElements = new ArrayList<>();
                if(LeftElement!=null)
                    LeftElement.appendChild(element.getElement());
                 leftPinElements.add(element);

                break;
            case Pin.RIGHT_PIN:
                if(RightPinElements==null)
                    RightPinElements=new ArrayList<>();
                if(RightElement!=null)
                    RightElement.appendChild(element.getElement());
                RightPinElements.add(element);
                break;

            case Pin.TOP_PIN:
                if(TopPinElements==null)
                    TopPinElements=new ArrayList<>();
                if(TopElement!=null)
                    TopElement.appendChild(element.getElement());
                TopPinElements.add(element);
                break;

            case Pin.BOTTOM_PIN:
                if(BottomPinElement==null)
                    BottomPinElement=new ArrayList<>();
                if(BottomElement!=null)
                    BottomElement.appendChild(element.getElement());
                BottomPinElement.add(element);
                break;
        }
  }


  public boolean addPinElement(int orient , Pin pin ){
        boolean status=false;

        try{
            PinElement element=new PinElement(pin , pin.parent.parent_layout.getDocument());
            if(element.isElement())
                addtoList(orient , element);

            status=true;
        }
        catch (Exception e){
             System.err.println("GateElement->addPinElement(int , Pin)->ERROR : "+e);
        }

        return status;
  }


    public List<PinElement> getLeftPinElements(){
        return this.leftPinElements;
    }

    public List<PinElement> getRightPinElements(){
        return this.RightPinElements;
    }


    public List<PinElement> getTopPinElements(){
        return this.TopPinElements;
    }

    public List<PinElement> getBottomPinElement(){
        return this.BottomPinElement;
    }

    public int getLeftPinsCount(){
      if(gateElement!=null) {
          String result = gateElement.getAttribute(ATTRIBUTE_LEFT_PIN);
          if(result.length()<=0)
              return 0;
          int l = Integer.parseInt(result);
          return l;
      }
      else
          System.out.println("GateElement->getLeftPins()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

      return -1;
  }


    public int getRightPinsCount(){
      if(gateElement!=null) {
          String result = gateElement.getAttribute(ATTRIBUTE_RIGHT_PIN);
          if(result.length()<=0)
              return 0;
          int l = Integer.parseInt(result);
          return l;
      }
      else
          System.out.println("GateElement->getRightPins()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

      return -1;
    }

    public int getTopPinsCount(){
      if(gateElement!=null) {
          String result = gateElement.getAttribute(ATTRIBUTE_TOP_PIN);
          if(result.length()<=0)
              return 0;
          int l = Integer.parseInt(result);
          return l;
      }
      else
          System.out.println("GateElement->getTopPins()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");
    return -1;
    }


    public int getBottomPinsCount(){
       if(gateElement!=null) {
           String result = gateElement.getAttribute(ATTRIBUTE_BOTTOM_PIN);
           if(result.length()<=0)
               return 0;
           int l = Integer.parseInt(result);
           return l;
       }
       else
           System.out.println("GateElement->getBottom()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

       return -1;
    }



    public  int getID(){
        if(gateElement!=null) {
            String sid = gateElement.getAttribute(ATTRIBUTE_PARENT_ID);

            int id = Integer.parseInt(sid);

            return id;
        }
        else
            System.out.println("GateElement->getID()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

        return -1;
    }


    public  String getStringID(){
        if(gateElement!=null) {
            String sid = gateElement.getAttribute(ATTRIBUTE_PARENT_ID);

            return sid;
        }
        else
            System.out.println("GateElement->getID()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

        return "NO_ID";
    }


    public String getName(){
        if(gateElement!=null){
            String sid = gateElement.getAttribute(ATTRIBUTE_ELEMENT_NAME);
            return sid;
        }
        else
            System.out.println("GateElement->getName()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

        return null;
    }


    public void setName(String name){
        if(gateElement!=null){
            gateElement.setAttribute(ATTRIBUTE_ELEMENT_NAME, name);
        }
        else
            System.out.println("GateElement->setName()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

    }


    public void setProjectName(String projectName){
        if(gateElement!=null){
            gateElement.setAttribute(ATTRIBUTE_PROJECT_NAME, projectName);
        }
        else
            System.out.println("GateElement->setProjectName()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");
    }

    public String getProjectName(){
        if(gateElement!=null){
            if(gateElement.hasAttribute(ATTRIBUTE_PROJECT_NAME))
                return gateElement.getAttribute(ATTRIBUTE_PROJECT_NAME);
            else
            {
                gateElement.setAttribute(ATTRIBUTE_PROJECT_NAME , this.getName());
                return gateElement.getAttribute(ATTRIBUTE_PROJECT_NAME);
            }
        }
        else
            System.out.println("GateElement->getProjectName()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");
        return "";
    }


    public  String getxLoc(){
        if(gateElement!=null)
            return gateElement.getAttribute(ATTRIBUTE_X_LOC);
        else
            System.out.println("GateElement->getXloc()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

     return null;
    }

    public  void setxLoc(String xloc){
        if(gateElement!=null)
            gateElement.setAttribute(ATTRIBUTE_X_LOC , xloc);
        else
            System.out.println("GateElement->setType(String)->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");
    }


    public  String getyLoc(){
        if(gateElement!=null)
           return gateElement.getAttribute(ATTRIBUTE_Y_LOC);
        else
            System.out.println("GateElement->getyLoc()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");

       return null;
    }

    public  void setyLoc(String yloc){
        if(gateElement!=null)
           gateElement.setAttribute(ATTRIBUTE_Y_LOC , yloc);
        else
            System.out.println("GateElement->setyLoc()->ERROR : GATE_ELEMENT IS NULL  , may have failed to create Element, check previous errors");
    }


    public void tostring(){
        System.out.println("*********GateElement Data***********");
        System.out.println("gate type : "+this.getType());
        System.out.println("gate  name : "+this.getName());
        //more is need to  be create
        System.out.println("*********GateElement END***********");
    }
    //Gate Element attributes

    public static final String ATTRIBUTE_ELEMENT_NAME="elementname";
    public static final String ATTRIBUTE_PROJECT_NAME="projectname";
    public static final String ATTRIBUTE_PARENT_ID="parentid";
    public static final String ATTRIBUTE_X_LOC="xloc";
    public static final String ATTRIBUTE_Y_LOC="yloc";
    public static final String ATTRIBUTE_LEFT_PIN="leftpin" ;
    public static final String ATTRIBUTE_RIGHT_PIN="rightpin";
    public static final String ATTRIBUTE_TOP_PIN="toppin";
    public static final String ATTRIBUTE_BOTTOM_PIN="bottompin";



    //orientation elements name
    public static final String ELEMENT_LEFT_PIN="LeftPin";
    public static final String ELEMENT_RIGHT_PIN="RightPin";
    public static final String ELEMENT_TOP_PIN="TopPin";
    public static final String ELEMENT_BOTTOM_PIN="BottomPin";

}
