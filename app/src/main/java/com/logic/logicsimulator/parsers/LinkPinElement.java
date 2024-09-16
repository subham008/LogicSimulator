package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.Pin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LinkPinElement {

    Element element;
    Pin pin;
    public LinkPinElement(Element element ){
         this.element=element;
        if( !isLinkPinElement(element))
            this.element=null;
    }

    public LinkPinElement(@NonNull Document document ,   @NonNull  Pin pin ,int orient ){
        this.element=createElement(document , pin , orient);
    }


    public static Element createElement(@NonNull Document document ,Pin pin ,int orient){
        //creating Link Pin Element
        Element element=null;
        try{
            element=document.createElement(LINK_PIN_ELEMENT);

            element.setAttribute(ATTRIBUTE_LINK_PIN_ORIENT , String.valueOf(orient) );
            element.setAttribute(ATTRIBUTE_PIN_INDEX , String.valueOf(pin.pinIndex) );
            element.setAttribute(ATTRIBUTE_PARENT_ID ,pin.parent.getGateID() );
            element.setAttribute(ATTRIBUTE_ORIENT , String.valueOf(pin.pinOreintation));
            element.setAttribute(ATTRIBUTE_PIN_NAME , pin.getPinName());
        }
        catch (Exception e){
            System.err.println("LinkPinElement->createLinkPinElement(Document ,Pin ,int)->ERROR :"+e);
        }

        return element;
    }



  //  get details functions
    public String getPinname(){
        String name="";
        if(element!=null){
            name=element.getAttribute(ATTRIBUTE_PIN_NAME);
        }

        return name;
    }

    public int getLinkPinOrient(){
        int orient=0;
        if(element!=null)
        {
            String sid=element.getAttribute(ATTRIBUTE_LINK_PIN_ORIENT);
            if(sid.length()>0 && sid!=null)
                orient=Integer.parseInt(sid);
        }
        return orient;
    }

    public String getParentID(){
        String id="";
        if(element!=null){
            String sid=element.getAttribute(ATTRIBUTE_PARENT_ID);
            if(sid.length()>0 && sid!=null)
               id=sid;
        }
        return id;
    }

    public int getOrient(){
        int orient=0;
        if(element!=null)
        {
            String sid=element.getAttribute(ATTRIBUTE_ORIENT);
            if(sid.length()>0 && sid!=null)
                orient=Integer.parseInt(sid);
        }
        return orient;
    }

    public int getPinIndex(){
        int index=0;
        if(element!=null)
        {
            String sid=element.getAttribute(ATTRIBUTE_PIN_INDEX);
            if(sid.length()>0 && sid!=null)
                index=Integer.parseInt(sid);

        }
        return index;
    }


// set details functions

    public void setPinname(String name){

        if(element!=null){
            element.setAttribute(ATTRIBUTE_PIN_NAME , name);
        }

    }

    public void setLinkPinOrient(int orient){

        if(element!=null)
        {
              element.setAttribute(ATTRIBUTE_ORIENT , String.valueOf(orient));
        }

    }

    public void setParentID(int id){
        if(element!=null){
            element.setAttribute(ATTRIBUTE_PARENT_ID , String.valueOf(id));
        }

    }

    public void setOrient(int orient){

        if(element!=null)
        {
            element.setAttribute(ATTRIBUTE_ORIENT , String.valueOf(orient));
        }

    }

    public int setPinIndex(int index){

        if(element!=null)
        {

        }
        return index;
    }




    public Element getElement(){
        return element;
    }
    public static boolean isLinkPinElement(Element element){
        if(element.getNodeName().compareTo(LINK_PIN_ELEMENT)==0)
            return true;

        return false;
    }




    public Pin getPin(@NonNull EditorLayout layout){
        String id=getParentID();
        int oreint=getOrient();
        int index=getPinIndex();

        Pin pin=layout.getPin(id , oreint , index);

        return pin;
    }



    public static final String LINK_PIN_ELEMENT="LinkPin";
    public static final String ATTRIBUTE_LINK_PIN_ORIENT="linkpinorient";
    public static final String ATTRIBUTE_PARENT_ID="parentid";
    public static final String ATTRIBUTE_ORIENT="orient";
    public static final String ATTRIBUTE_PIN_INDEX="index";
    public static final String ATTRIBUTE_PIN_NAME="pinname";
}
