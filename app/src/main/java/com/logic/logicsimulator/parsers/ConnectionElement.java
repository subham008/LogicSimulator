package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.Pin;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ConnectionElement {

    private Element conElement;
    static String error=null;
    public ConnectionElement(@NonNull Element element){
        if(isConnectionElement(element))
            this.conElement=element;
        else {
            System.err.println( "ConnectionElement(Element) Constructer ()-> "+error);
        }
    }

    public ConnectionElement(@NonNull Pin from , @NonNull Pin to , @NonNull Document document){
        try{
            Element element=document.createElement(CONNECTION_ELEMENT_NAME);
            if(element!=null){
                element.setAttribute(ATTRIBUTE_PARENTID ,to.parent.getGateID() );
                element.setAttribute(ATTRIBUTE_PIN_ORIENT, String.valueOf(to.pinOreintation));
                element.setAttribute(ATTRIBUTE_PIN_INDEX, String.valueOf(to.pinIndex));

                this.conElement=element;
            }else {
                System.err.println("ConnectionElement(Pin ,Pin , Document) -> Error : failed to create Element from passed Document Object , and no any Exception also occured");
            }
        }
        catch (Exception e){
            System.err.println("ConnectionElement(Pin ,Pin ,  Document) -> Error : "+e);
        }
    }



    public Element getElement(){
        return conElement;
    }


    //returns null if failed
    public  String getParentID(){
        if(conElement==null){
            if(error!=null)
                System.err.println("ConnectionElement getParentID()->"+error);
            else
                System.err.println("ConnectionElement getParentID()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return null;
        }

        Attr parentidAttr=conElement.getAttributeNode(ATTRIBUTE_PARENTID);
        if(parentidAttr==null){
            System.err.println("ConnectionElement getParentID()->"+"Attribute parentid not found  , Somehow  pass the  isPinElement() , Source problem ");
            return null;
        }

        return parentidAttr.getValue();
    }






    public  String getPinOrient(){
        if(conElement==null){
            if(error!=null)
                System.err.println("ConnectionElement getPinOreint()->"+error);
            else
                System.err.println("ConnectionElement getPinOreint()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return null;
        }

        Attr parentidAttr=conElement.getAttributeNode(ATTRIBUTE_PIN_ORIENT);
        if(parentidAttr==null){
            System.err.println("ConnectionElement getPinOreint()->"+"Attribute pin_orient not found  , Somehow  pass the  isPinElement() , Source problem ");
            return null;
        }

        return parentidAttr.getValue();
    }



    public  String getPinIndex(){
        if(conElement==null){
            if(error!=null)
                System.err.println("ConnectionElement getPinIndex()->"+error);
            else
                System.err.println("ConnectionElement getPinIndex()->"+"No Element found  , ERROR not Found Also  , Source Probelm");
            return null;
        }

        Attr parentidAttr=conElement.getAttributeNode(ATTRIBUTE_PIN_INDEX);
        if(parentidAttr==null){
            System.err.println("ConnectionElement getPinIndex()->"+"Attribute pinindex not found  , Somehow  pass the  isPinElement() , Source problem ");
            return null;
        }

        return parentidAttr.getValue();
    }






    public static boolean isConnectionElement(@NonNull Element element){
        // if Element have all 3 Attribute  then we consider it as a valid ConnectionElement
        Attr parentidAttr=element.getAttributeNode(ATTRIBUTE_PARENTID);
        Attr pinorientAttr=element.getAttributeNode(ATTRIBUTE_PIN_ORIENT);
        Attr pinindexAttr=element.getAttributeNode(ATTRIBUTE_PIN_INDEX);

        if(parentidAttr==null)
            error="Attribute parentid not found";
        if(pinorientAttr==null)
            error="Attribute pinorient not found";
        if(pinindexAttr==null)
            error="Attribute pinindex not found";

        if(parentidAttr!=null && pinorientAttr!=null && pinindexAttr!=null && element.getNodeName().compareTo(CONNECTION_ELEMENT_NAME)==0 )
            return  true;

        return false;
    }


    public static final String  CONNECTION_ELEMENT_NAME="Connection";


    //attributes
    public static final String ATTRIBUTE_PARENTID="parentid";
    public static final String ATTRIBUTE_PIN_ORIENT="pin_orient";
    public static final String ATTRIBUTE_PIN_INDEX="pinindex";

}
