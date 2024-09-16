package com.logic.logicsimulator.parsers;;

import androidx.annotation.NonNull;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProjectSetting {
     
    private Attr attributes[];

    public ProjectSetting(Document doc){
         attributes=parseSetting(doc);
    }


    private static String  required_attributes[]={"text_label_visible",
                                       "grid_lines_visible" ,
                                       "wires_visible",
                                       "signal_visible",
                                       "vibration",
                                       "flashlight",
                                       "simulation"
                                       };


  private static Attr[] parseSetting(Document doc){
       
       Attr attr[]=new Attr[required_attributes.length];

        NodeList nList = doc.getDocumentElement().getChildNodes(); //getting all nodes inside root node
        
        Element element=null;

        for(int i=0 ; i<nList.getLength(); i++){
            Node node=nList.item(i);
            if(node.getNodeType()== Node.ELEMENT_NODE && node.getNodeName().compareTo(PROJECT_SETTING_ELEMENT_NAME)==0){
                  element=(Element) node;
                  break;
            }
        }


        
       //aquiriing project setting data  , saving in a boolean array and returning array  
        if( element!=null && element.hasAttributes()){
               for(int i=0; i<required_attributes.length; i++){
                    attr[i]= element.getAttributeNode(required_attributes[i]);
                    //settings[i]=Boolean.parseBoolean(attr.getValue()) ;
               }
        }
        else{
          System.out.println("Invalid settings node");
        }

    return attr;
    }


public  boolean getSetting(int setting_id) {
     return Boolean.parseBoolean(attributes[setting_id].getValue());
}  

public void setSetting(int setting_id , boolean value){
     attributes[setting_id].setValue(String.valueOf(value));
}

public void logData(){
    System.out.println("**************ProjectSetting Start*************");
    System.out.println("TEXT_VISIBLE:"+getSetting(TEXT_VISIBLE));
    System.out.println("GRID_VISIBLE:"+getSetting(GRID_VISIBLE));
    System.out.println("WIRES_VISIBLE:"+getSetting(WIRES_VISIBLE));
    System.out.println("SIGNAL_VISIBLE:"+getSetting(SIGNAL_VISIBLE));
    System.out.println("VIBRATION:"+getSetting(VIBRATION));
    System.out.println("FLASHLIGHT:"+getSetting(FLASHLIGHT));
    System.out.println("SIMULATION:"+getSetting(SIMULATION));
    System.out.println("**************ProjectSetting End*************");
}



public static Element createDefaultProjectSettingElement(@NonNull Document document){
      Element element=null;
      try{
          element=document.createElement(PROJECT_SETTING_ELEMENT_NAME);

          //here eement is created noe we need to add its required attributes with its default values

          String true_text=String.valueOf(true);

          element.setAttribute(ATTRIBUTE_TEXT_LABEL_VISIBLE ,true_text );
          element.setAttribute(ATTRIBUTE_GRID_LINES_VISIBLE ,true_text );
          element.setAttribute(ATTRIBUTE_WIRES_VISIBLE ,true_text );
          element.setAttribute(ATTRIBUTE_SIGNAL_VISIBLE ,true_text );
          element.setAttribute(ATTRIBUTE_VIBRATION_VISIBLE,true_text );
          element.setAttribute(ATTRIBUTE_FLASHLIGHT_VISIBLE ,true_text );
          element.setAttribute(ATTRIBUTE_SIMULATION_VISIBLE ,true_text );


      }catch (Exception e){
           System.out.println("ProjectSeting->createDefaultProjectSettingElement()->ERROR: "+e.toString());
      }


      return element;
}
public static int TEXT_VISIBLE=0;
public static int GRID_VISIBLE=1;
public static int WIRES_VISIBLE=2;
public static int SIGNAL_VISIBLE=3;
public static int VIBRATION=4;
public static int FLASHLIGHT=5;
public static int SIMULATION=6;



public static  String PROJECT_SETTING_ELEMENT_NAME="settings";

    public static String ATTRIBUTE_TEXT_LABEL_VISIBLE="text_label_visible";
    public static String ATTRIBUTE_GRID_LINES_VISIBLE="grid_lines_visible";
    public static String ATTRIBUTE_WIRES_VISIBLE="wires_visible";
    public static String ATTRIBUTE_SIGNAL_VISIBLE="signal_visible";
    public static String ATTRIBUTE_VIBRATION_VISIBLE="vibration";
    public static String ATTRIBUTE_FLASHLIGHT_VISIBLE="flashlight";
    public static String ATTRIBUTE_SIMULATION_VISIBLE="simulation";

}//end of ProjectSetting
