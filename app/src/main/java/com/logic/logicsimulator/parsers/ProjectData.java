package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.MainActivity;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProjectData {
     
    Attr attributes[]=null;
    public  static  int PROJECT_VERSION=1;
    public ProjectData(@NonNull Document document){
         attributes= parseProjectData(document);
    }
    


    public static Attr[] parseProjectData(@NonNull  Document doc){
        Attr attr[]=new Attr[required_attributes.length];
         
        NodeList nList = doc.getDocumentElement().getChildNodes(); //getting all nodes inside root node
        
        Element dataelement=null;

        for(int i=0 ; i<nList.getLength(); i++){
            Node node=nList.item(i);
            if(node.getNodeType()== Node.ELEMENT_NODE && node.getNodeName().compareTo("ProjectData")==0){
                  dataelement=(Element) node;
                  break;
            }
        }

         //aquiriing project setting data  , saving in a boolean array and returning array  
         if( dataelement!=null && dataelement.hasAttributes()){
            for(int i=0; i<required_attributes.length; i++){
                 attr[i]= dataelement.getAttributeNode(required_attributes[i]);
                 if(attr[i]==null){
                     Attr tmpattr=doc.createAttribute(required_attributes[i]);
                     tmpattr.setValue(default_values[i]);
                     dataelement.setAttributeNode(tmpattr);
                 }
            }
     }
     else{
       System.err.println("Invalid settings node");
     }
        return attr;
    }




    public int getData( int id){
        if(attributes[id]!=null)
           return Integer.parseInt(attributes[id].getValue());

        return Integer.parseInt(default_values[id]);
    }

    public float getDataFloat( int id){
        if(attributes[id]!=null)
            return Float.parseFloat(attributes[id].getValue());

        return Float.parseFloat(default_values[id]);
    }


    public String getVersion(){
        if(attributes[VERSION]!=null){
            return attributes[VERSION].getValue();
        }

        return "0.0";
    }

    public void setVersion(String version){
        if(attributes[VERSION]!=null){
            attributes[VERSION].setValue(version);
        }
    }


    public void setData(  int id , int value){
        if(attributes[id]!=null)
          attributes[id].setValue(String.valueOf(value));
    }

    public void setData(  int id , float value){
        if(attributes[id]!=null)
            attributes[id].setValue(String.valueOf(value));
    }



    private static String  required_attributes[]={"connection_count",
                                       "element_count" ,
                                       "version",
                                       "editorx",
                                        "editory",
                                        "created",
                                        "modified",
                                         "scalex",
                                        "scaley"
                                       };
    private static String  default_values[]={"0",
            "0" ,
            "1" ,"-2500","-2500",
            "00:00:00",
            "00:00:00",
            "1.0",
            "1.0"
    };

    public String getCreatedDate(){
        if( attributes[5]!=null)
          return attributes[5].getValue();

        return "No Data";
    }
    public String getModifiedDate(){
        if(attributes[6]!=null)
          return attributes[6].getValue();
        return "No Data";
    }


    public boolean setCreatedDate(String date) {
        if(attributes[5]!=null) {
            attributes[5].setValue(date);
            return true;
        }

        return false;
    }

    public boolean setModifiedDate(String date){
        if(attributes[6]!=null) {
            attributes[6].setValue(date);
            return true;
        }
        return false;
    }

    public void logData(){
        System.out.println("**************ProjectData Start*************");
        System.out.println("CONNECTION_COUNT:"+getData(CONNECTION_COUNT));
        System.out.println("ELEMENT_COUNT:"+getData(ELEMENT_COUNT));
        System.out.println("VERSION:"+getData(VERSION));
        System.out.println("EDITOR_X:"+getData(EDITOR_X));
        System.out.println("EDITOR_Y:"+getData(EDITOR_Y));
        System.out.println("SCALE_X:"+getDataFloat(SCALE_X));
        System.out.println("SCALE_Y:"+getDataFloat(SCALE_Y));
        System.out.println("**************ProjectData End*************");
    }






    public static Element createDefaultProjectDataElement(@NonNull Document document){
        Element element=null;
        try{
            element=document.createElement(PROJECT_DATA_ELEMENT_NAME);

            //here eement is created noe we need to add its required attributes with its default values

            element.setAttribute(ATTRIBUTE_CONNECTION_COUNT ,default_values[0] );
            element.setAttribute(ATTRIBUTE_ELEMENT_COUNT ,default_values[1] );
            element.setAttribute(ATTRIBUTE_VERSION ,default_values[2] );
            element.setAttribute(ATTRIBUTE_EDITORX ,default_values[3] );
            element.setAttribute(ATTRIBUTE_EDITORY,default_values[4] );
            element.setAttribute(ATTRIBUTE_CREATED , MainActivity.getPresentDate());
            element.setAttribute(ATTRIBUTE_MODIFIED ,MainActivity.getPresentDate() );
            element.setAttribute(ATTRIBUTE_SCALEX,default_values[7] );
            element.setAttribute(ATTRIBUTE_SCALEY ,default_values[8] );


        }catch (Exception e){
            System.out.println("Projectdata->createDefaultProjectDataElement()->ERROR: "+e.toString());
        }


        return element;
    }
    public static int CONNECTION_COUNT=0;
    public static int ELEMENT_COUNT=1;
    public static int VERSION=2;
    public static int EDITOR_X=3;
    public static int EDITOR_Y=4;
    public static int SCALE_X=7;
    public static int SCALE_Y=8;


    public static String PROJECT_DATA_ELEMENT_NAME="ProjectData";

    public static String ATTRIBUTE_CONNECTION_COUNT="connection_count";
    public static String ATTRIBUTE_ELEMENT_COUNT="element_count";
    public static String ATTRIBUTE_VERSION="version";
    public static String ATTRIBUTE_EDITORX="editorx";
    public static String ATTRIBUTE_EDITORY="editory";
    public static String ATTRIBUTE_CREATED="created";
    public static String ATTRIBUTE_MODIFIED="modified";
    public static String ATTRIBUTE_SCALEX="scalex";
    public static String ATTRIBUTE_SCALEY="scaley";
} 
