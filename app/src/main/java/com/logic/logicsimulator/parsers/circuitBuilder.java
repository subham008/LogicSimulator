package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.MainActivity;
import com.logic.logicsimulator.Pin;

import org.w3c.dom.*;
import java.io.File;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
//return null on any failure
public class circuitBuilder{
    public static Document createProject(@NonNull File xmlFile){
          Document doc=null;
         try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc= dBuilder.newDocument();
            
            // Create the root element
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);

             //creating setting element
             Element settingsElement = doc.createElement("settings");
             // Set attributes for the settings element
             settingsElement.setAttribute("text_label_visible", "true");
             settingsElement.setAttribute("grid_lines_visible", "true");
             settingsElement.setAttribute("wires_visible", "true");
             settingsElement.setAttribute("signal_visible", "true");
             settingsElement.setAttribute("vibration", "true");
             settingsElement.setAttribute("flashlight", "true");
             settingsElement.setAttribute("simulation", "true");
             rootElement.appendChild(settingsElement);

             // Create a new element for the ProjectData
             Element projectDataElement = doc.createElement("ProjectData");
             // Set attributes for the ProjectData element
             projectDataElement.setAttribute("element_count", "0");
             projectDataElement.setAttribute("connection_count", "0");
             projectDataElement.setAttribute("version", "1");
             projectDataElement.setAttribute("editorx", "-2500");
             projectDataElement.setAttribute("editory", "-2500");
             projectDataElement.setAttribute("created", MainActivity.getPresentDate());
             projectDataElement.setAttribute("modified", MainActivity.getPresentDate());
             projectDataElement.setAttribute("version",String.valueOf(ProjectData.PROJECT_VERSION));
             rootElement.appendChild(projectDataElement);
            // Save the document to a file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

             FileWriter fileWriter = new FileWriter(xmlFile);
            StreamResult result = new StreamResult(fileWriter);

            // Transform and save the document
            transformer.transform(source, result);

            System.out.println("XML file created successfully");
         }
         catch(Exception e){
            System.out.println(e.toString());
         }

     return doc;
    }//end of create build


    public static  Document openProject(File project_file){
      Document doc=null;
      try  {
            
        DocumentBuilderFactory dbfac=  DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbfac.newDocumentBuilder();
        doc=dBuilder.parse(project_file);
        doc.getDocumentElement().normalize();

      } catch (Exception e) {
         System.err.println(e.toString());
         doc=null;
      }

      return doc;
    }

    public static String TEXT_LABEL="TextLabel";
    public static String FONT_SIZE="fontsize";
    public static Element createTextLabelElement(String text , int x ,int y , int fontSize , @NonNull Document document ){
         Element element=document.createElement(TEXT_LABEL);

         try{

             //setting attributes
             element.setAttribute("elementname", text);
             element.setAttribute("xloc",String.valueOf(x));
             element.setAttribute("yloc",String.valueOf(x));
             element.setAttribute(FONT_SIZE , String.valueOf(fontSize));

         }catch (Exception e){
             System.err.println("circuitBuilder->createTextLabelElement:ERROR : "+e.toString());
         }

         return element;
    }

    public static final String IC_GATE="IcGate";
    public static  final String PARENTID_NAME="parentid";
    public static  final String ELEMENT_NAME="elementname";
    public static  final String PROJECT_NAME="projectname";
    public static  final String X_LOCATION="xloc";
    public static  final String Y_LOCATION="yloc";

    public static Element createICelement( String name , String gateID , String project , int x  , int y, Document document){
        Element element=document.createElement(IC_GATE);
         try{

             element.setAttribute(PARENTID_NAME , gateID);
             element.setAttribute(ELEMENT_NAME , name);
             element.setAttribute(PROJECT_NAME, project);
             element.setAttribute(X_LOCATION, String.valueOf(x));
             element.setAttribute(Y_LOCATION, String.valueOf(y));

         }catch (Exception e){
             System.err.println("circuitBuilder->createICelement"+e.toString());
         }

        return  element;
    }


    public static Element createElement(String gate_name , String elementName,String gateid , int leftpin, int rightpin , int toppin , int bottompin ,int x,int y,Document  doc){
        Element gate_Element=doc.createElement(gate_name);
        gate_Element.setAttribute("parentid", gateid);
        gate_Element.setAttribute("elementname", elementName);
        gate_Element.setAttribute("xloc",String.valueOf(x));
        gate_Element.setAttribute("yloc",String.valueOf(x));
        gate_Element.setAttribute("leftpin" , String.valueOf(leftpin));
        gate_Element.setAttribute("rightpin" , String.valueOf(rightpin));
        gate_Element.setAttribute("toppin" , String.valueOf(toppin));
        gate_Element.setAttribute("bottompin" , String.valueOf(bottompin));

        return gate_Element;
    }


    //for pin element

    public static Element createPinElement(@NonNull Document document,@NonNull Pin pin){
        Element pinElement = document.createElement("Pin");
            //setting attributes
            String gateid=pin.parent.getGateID();
            if(gateid==null)
                gateid="NO_ID";
            pinElement.setAttribute(PinElement.ATTRIBUTE_PARENTID,gateid);
            pinElement.setAttribute(PinElement.ATTRIBUTE_PIN_ORIENT ,String.valueOf(pin.pinOreintation));
            pinElement.setAttribute(PinElement.ATTRIBUTE_PIN_INDEX, String.valueOf(pin.pinIndex));
            pinElement.setAttribute(PinElement.ATTRIBUTE_PIN_NAME, pin.getPinName());

     return  pinElement;
    }

    public static  Element configurePinElement(@NonNull Document document,@NonNull Pin pin, Element element){
         if(element==null)
             element=createPinElement(document,pin);
         else{
             if(element.getAttributeNode("parentid")==null){
                 element.setAttribute( "parentid",pin.parent.getGateID());
             }

             if(element.getAttributeNode("pin_orient")==null){
                 element.setAttribute( "pin_orient",String.valueOf(pin.pinOreintation));
             }

             if(element.getAttributeNode("pinindex")==null){
                 element.setAttribute( "pinindex",String.valueOf(pin.pinIndex));
             }

             if(element.getAttributeNode("pinname")==null){
                 element.setAttribute( "pinname",pin.getPinName());
             }
             else
                 pin.setPinName(element.getAttributeNode("pinname").getValue());
         }

         return element;
    }

    public static  Element createConnectioElement(@NonNull Document document, @NonNull Pin from , @NonNull Pin to){
        Element element=document.createElement("Connection");
        try{
            //in connection we just need to store the data of TO  pin  , as we knew from where we reach this Connection
            element.setAttribute("parentid" ,to.parent.getGateID() );
            element.setAttribute("pin_orient", String.valueOf(to.pinOreintation));
            element.setAttribute("pinindex", String.valueOf(to.pinIndex));
        }catch (Exception e){
            System.err.println("createConnectionElement error: "+e.toString());
        }

        return element;
    }


    public static void addGate(String gate_name , String gateid , int leftpin, int rightpin , int toppin , int bottompin ,int x,int y,Document  doc){
             Element gate_Element=doc.createElement(gate_name);
             gate_Element.setAttribute("parentid", gateid);
             gate_Element.setAttribute("xloc",String.valueOf(x));
             gate_Element.setAttribute("yloc",String.valueOf(x));
             gate_Element.setAttribute("leftpin" , String.valueOf(leftpin));
             gate_Element.setAttribute("rightpin" , String.valueOf(rightpin));
             gate_Element.setAttribute("toppin" , String.valueOf(toppin));
             gate_Element.setAttribute("bottompin" , String.valueOf(bottompin));


              Node rootNode=doc.getFirstChild();
              rootNode.appendChild(gate_Element);
                                   
    }



    public static void removeGate(String gate_name , String gateid , Document  doc){

      try {          
           Node parentNode=doc.getFirstChild();//getting all nodes inside root node
           System.out.println(parentNode.getNodeName());
           NodeList gate_nodeList=doc.getElementsByTagName(gate_name);

        for(int i=0 ; i<gate_nodeList.getLength(); i++){
         Node node=gate_nodeList.item(i);
         Element element=(Element)node;
         String id=element.getAttributeNode("id").getValue();
         if(id.compareTo(gateid)==0){
            parentNode.removeChild(element);
             System.out.println("found"+element.getNodeName());            
             break;
         }
        }//end of for
      } catch (Exception e) {
         System.err.println(e.toString());
      }       
   }


    public static String getOrientation(int orientaion){
         String pin_orient="fuck off";
      switch (orientaion) {
          case Pin.LEFT_PIN:
             pin_orient= "LeftPin";
            break;
          case Pin.RIGHT_PIN:
             pin_orient= "RightPin";
            break;
          case Pin.TOP_PIN:
             pin_orient= "TopPin";
            break;
          case Pin.BOTTOM_PIN:
             pin_orient= "BottomPin";
            break;
         default:
            break;
      }
      return pin_orient;
    }
   

    public static  Element getPinElementByid(String pinid,NodeList pinlist){
        Element element=null;

        for(int i=0;i<pinlist.getLength(); i++){
            Element pinelement=(Element) pinlist.item(i);
            String pinid_value=pinelement.getAttribute("pinid");
            if(pinid_value.compareTo(pinid)==0)
                element=pinelement;
        }

        return  element;
    }
    
    public static void addConnection(Element gate_from , Element gate_to , int from_orient , int to_orient ,int from_index, int to_index  ,Document doc){
            NodeList orient_node=gate_from.getElementsByTagName(getOrientation(from_orient));

            try {
                boolean needToAdd=false;
                //getting orientation element
                Element element = null;
                if (orient_node.getLength() > 0)
                    element = (Element) orient_node.item(0);
                else {
                    element = doc.createElement(getOrientation(from_orient));
                    needToAdd=true;
                }
                //appending orientation element in gate
                NodeList pinsList=element.getElementsByTagName("Pin");

                //creating Pin element
                Element pin_Element = getPinElementByid(String.valueOf(from_index),pinsList);
                if(pin_Element==null)
                    doc.createElement("Pin");

                pin_Element.setAttribute("pinid", Integer.toString(from_index));
                pin_Element.setAttribute("parentid", gate_to.getAttributeNode("parentid").getValue());
                pin_Element.setAttribute("pin_orient", Integer.toString(to_orient));
                pin_Element.setAttribute("pinindex", Integer.toString(to_index));

                //adding pin element to orient element
                element.appendChild(pin_Element);
                if(needToAdd)
                  gate_from.appendChild(element);
                System.out.println("connection addition success");
            }
            catch (Exception e){
                System.err.println("addConnection ERROR"+e.toString());
            }

    }
   



    public static boolean update(Document doc , File xmlFile){

      try {
       // Save the updated document to the existing file
       TransformerFactory transformerFactory = TransformerFactory.newInstance();
       Transformer transformer = transformerFactory.newTransformer();
       DOMSource source = new DOMSource(doc);
       StreamResult result = new StreamResult(xmlFile);

       // Transform and save the document
       transformer.transform(source, result);

       return true;
      } catch (Exception e) {
         System.out.println(e.toString());
      }

       return false;
    }//update

    public static void clearElements(Document document) {
        Element rootElement = (Element) document.getFirstChild(); // Get the root element

        if (rootElement != null && rootElement.getNodeName().equals("root")) {
            NodeList childNodes = rootElement.getChildNodes(); // Get all child nodes

            for (int i = childNodes.getLength() - 1; i >= 0; i--) { // Iterate in reverse for safe removal
                Node childNode = childNodes.item(i);

                if (childNode.getNodeType() == Node.ELEMENT_NODE &&
                        !childNode.getNodeName().equals("ProjectData") &&
                        !childNode.getNodeName().equals("settings")) {

                    try {
                        rootElement.removeChild(childNode); // Remove the element
                    } catch (DOMException e) {
                        // Handle potential errors during removal
                        System.err.println("Error removing element: " + e.getMessage());
                    }
                }
            }
        } else {
            System.err.println("Root element not found or has incorrect name.");
        }
    }


}//end of class 