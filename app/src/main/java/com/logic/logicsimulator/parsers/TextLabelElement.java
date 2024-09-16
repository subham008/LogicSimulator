package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.Gates.TextLabel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class TextLabelElement {

    private Element textElement;
    public TextLabelElement(@NonNull Element element){
         if(isTextLabel(element)){
             this.textElement=element;
         }
         else {
             System.err.println("TextLabelElement->TextLabelElement(Element)-> PASSED ELEMENT IS NOT A VALID TEXT LABEL ELEMENT");
         }
    }

    public TextLabelElement(@NonNull TextLabel label , @NonNull Document document){
        try{
             Element element=document.createElement(TEXT_LABEL_ELEMENT_NAME);
             this.textElement=element;
             element.setAttribute(ATTRIBUTE_ELEMENT_NAME, label.getLabelText());
            element.setAttribute(ATTRIBUTE_X_LOC,String.valueOf( label.getX()));
            element.setAttribute(ATTRIBUTE_Y_LOC, String.valueOf( label.getY()));
            element.setAttribute(ATTRIBUTE_FONT_SIZE, String.valueOf( label.getFontSize()) );

        }catch (Exception e){
            System.err.println("TextLabelElement-.TextLabelElement(TextLabel , Document)->"+e);
        }
    }



    public Element getElement(){
        return textElement;
    }
    public  static  boolean isTextLabel(@NonNull Element element){
        if(element.getNodeName().compareTo(TEXT_LABEL_ELEMENT_NAME)==0){
            return true;
        }
        return false;
    }


    public boolean isValid(){
        if(textElement!=null)
            return true;

        return false;
    }

    public String getText(){
        if(textElement!=null)
            return textElement.getAttribute(ATTRIBUTE_ELEMENT_NAME);

        System.err.println("TextLabelElement->getText()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
        return null;
    }

    public void setText(String text){
        if(textElement!=null)
             textElement.setAttribute(ATTRIBUTE_ELEMENT_NAME , text);
        else
        System.err.println("TextLabelElement->setText()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
    }

    public String getXloc(){
        if(textElement!=null)
            return textElement.getAttribute(ATTRIBUTE_X_LOC);
        else
          System.err.println("TextLabelElement->getXloc()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
        return null;
    }

    public void setXloc(String text){
        if(textElement!=null)
            textElement.setAttribute(ATTRIBUTE_X_LOC , text);
        else
          System.err.println("TextLabelElement->setXloc()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
    }


    public String getYloc(){
        if(textElement!=null)
            return textElement.getAttribute(ATTRIBUTE_Y_LOC);
        else
        System.err.println("TextLabelElement->getYloc()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
        return null;
    }

    public void setYloc(String text){
        if(textElement!=null)
            textElement.setAttribute(ATTRIBUTE_Y_LOC , text);
        else
        System.err.println("TextLabelElement->setYloc()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
    }


    public String getFontSize(){
        if(textElement!=null)
            return textElement.getAttribute(ATTRIBUTE_FONT_SIZE);

        System.err.println("TextLabelElement->getFontSize()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
        return null;
    }

    public void setFontSize(String text){
        if(textElement!=null)
            textElement.setAttribute(ATTRIBUTE_FONT_SIZE , text);
       else
        System.err.println("TextLabelElement->setFontSize()-> TEXT ELMENT IS NULL , SOMEHOW FAILED TO CREATE OR PARSE ELEMENT  , FOR MORE DETAILS SEE ABOVE ERRORS");
    }



    public static  String TEXT_LABEL_ELEMENT_NAME="TextLabel";

    //attributes
    public static String ATTRIBUTE_ELEMENT_NAME="elementname";
    public static String ATTRIBUTE_X_LOC="xloc";
    public static String ATTRIBUTE_Y_LOC="yloc";
    public static String ATTRIBUTE_FONT_SIZE="fontsize";

}
