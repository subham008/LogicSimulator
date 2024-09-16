package com.logic.logicsimulator.parsers;;

import org.w3c.dom.*;

public class circuitParser {
    
   public static final int LEFT_ORIENTAION=5 , RIGHT_ORIENTAION=6 , TOP_ORIENTAION=7 , BOTTOM_ORIENTAION=8 ;

   
    public  void  addGate(String type,String elementName , String projectname,String id, int left, int right, int top, int bottom , int x, int y ,int fontsize ,  Element element){
        // gate adding code to EditorLayout
         // System.out.println("gate added:"+name+" id:"+id+" l:"+left+" rihht:"+right+" top:"+top+" bottom:"+bottom);                  
    }

    public  void connectPin(Element frompin, Element connection){
        //System.out.println("from_parent id :"+from_parent+" from:"+index_pin_from+"to_parent id"+to_parent+" to:"+index_pin_to);
  }



  public static int parse(Document doc,circuitParser parser){
      int element_count=0;


        NodeList nList = doc.getDocumentElement().getChildNodes(); //getting all nodes inside root node

          for (int temp = 0; temp < nList.getLength(); temp++) {
              try {
                  Element element = null;
                  Node nNode = nList.item(temp);
                  if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.getNodeName().compareTo("settings") != 0 && nNode.getNodeName().compareTo("ProjectData") != 0) {
                      element_count++;
                      //each gate is parsed
                      element = (Element) nNode;
                      if (element.hasAttributes()) {
                          int l = 0, r = 0, t = 0, b = 0;
                          int x = 2500, y = 2500;
                          String id = "NO_ID";
                          String elementName = "BasiGate";
                          String projectname = "NO_NAME";
                          int fontsize = 30;

                          Attr parentid = element.getAttributeNode("parentid");
                          if (parentid != null)
                              id = parentid.getValue();
                          Attr elName = element.getAttributeNode("elementname");
                          if (elName != null)
                              elementName = elName.getValue();

                          Attr projectName = element.getAttributeNode(circuitBuilder.PROJECT_NAME);
                          if (projectName != null)
                              projectname = projectName.getValue();

                          Attr xattr = element.getAttributeNode("xloc");
                          if (xattr != null)
                              x = Integer.parseInt(xattr.getValue());

                          Attr yattr = element.getAttributeNode("yloc");
                          if (yattr != null)
                              y = Integer.parseInt(yattr.getValue());

                          Attr left = element.getAttributeNode("leftpin");
                          if (left != null)
                              l = Integer.parseInt(left.getValue());

                          Attr right = element.getAttributeNode("rightpin");
                          if (right != null)
                              r = Integer.parseInt(right.getValue());

                          Attr top = element.getAttributeNode("toppin");
                          if (top != null)
                              t = Integer.parseInt(top.getValue());

                          Attr bottom = element.getAttributeNode("bottompin");
                          if (bottom != null)
                              b = Integer.parseInt(bottom.getValue());

                          Attr fontAttr = element.getAttributeNode(circuitBuilder.FONT_SIZE);
                          if (fontAttr != null)
                              fontsize = Integer.parseInt(fontAttr.getValue());

                          parser.addGate(element.getNodeName(), elementName, projectname, id, l, r, t, b, x, y, fontsize, element);
                      }
                  }
                  // addGate(nNode.getNodeName() ,);
              }
              catch (Exception e){
                  System.out.println("circuitParser->parsing connection error:"+e.getMessage());
              }
         }

           //parsing and  making connections
           for(int temp = 0; temp < nList.getLength(); temp++){
            try {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    //validation gate element
                    Attr idNode = element.getAttributeNode("parentid");
                    if (idNode == null)
                        continue;

                    Element leftpinElements = (Element) element.getElementsByTagName("LeftPin").item(0);
                    Element rightpinElement = (Element) element.getElementsByTagName("RightPin").item(0);
                    Element toppinElement = (Element) element.getElementsByTagName("TopPin").item(0);
                    Element bottompinElement = (Element) element.getElementsByTagName("BottomPin").item(0);

                    if (leftpinElements != null) {
                        NodeList pinList = leftpinElements.getElementsByTagName("Pin");
                        for (int i = 0; i < pinList.getLength(); i++) {
                            if (pinList.item(i).getNodeType() == Element.ELEMENT_NODE) {
                                Element pinElement = (Element) pinList.item(i);
                                NodeList connection = pinElement.getElementsByTagName("Connection");
                                for (int j = 0; j < connection.getLength(); j++) {
                                    if (connection.item(j).getNodeType() == Element.ELEMENT_NODE) {
                                        parser.connectPin(pinElement, (Element) connection.item(j));
                                    }
                                }
                            }
                        }//end of for
                    }//end if leftpinEement connection parsing

                    if (rightpinElement != null) {
                        NodeList pinList = rightpinElement.getElementsByTagName("Pin");
                        for (int i = 0; i < pinList.getLength(); i++) {
                            if (pinList.item(i).getNodeType() == Element.ELEMENT_NODE) {
                                Element pinElement = (Element) pinList.item(i);
                                NodeList connection = pinElement.getElementsByTagName("Connection");
                                for (int j = 0; j < connection.getLength(); j++) {
                                    if (connection.item(j).getNodeType() == Element.ELEMENT_NODE) {
                                        parser.connectPin(pinElement, (Element) connection.item(j));
                                    }
                                }
                            }
                        }//end of for
                    }//end if rightpinEement connection parsing

                    if (toppinElement != null) {
                        NodeList pinList = toppinElement.getElementsByTagName("Pin");
                        for (int i = 0; i < pinList.getLength(); i++) {
                            if (pinList.item(i).getNodeType() == Element.ELEMENT_NODE) {
                                Element pinElement = (Element) pinList.item(i);
                                NodeList connection = pinElement.getElementsByTagName("Connection");
                                for (int j = 0; j < connection.getLength(); j++) {
                                    if (connection.item(j).getNodeType() == Element.ELEMENT_NODE) {
                                        parser.connectPin(pinElement, (Element) connection.item(j));
                                    }
                                }
                            }
                        }//end of for
                    }//end if toppinEement connection parsing

                    if (bottompinElement != null) {
                        NodeList pinList = bottompinElement.getElementsByTagName("Pin");
                        for (int i = 0; i < pinList.getLength(); i++) {
                            if (pinList.item(i).getNodeType() == Element.ELEMENT_NODE) {
                                Element pinElement = (Element) pinList.item(i);
                                NodeList connection = pinElement.getElementsByTagName("Connection");
                                for (int j = 0; j < connection.getLength(); j++) {
                                    if (connection.item(j).getNodeType() == Element.ELEMENT_NODE) {
                                        parser.connectPin(pinElement, (Element) connection.item(j));
                                    }
                                }
                            }
                        }//end of for
                    }//end if bottomEement connection parsing
                }
            }
            catch (Exception e){
                 System.out.println("circuitParser->parsing connection error:"+e.getMessage());
            }
        }// end of for



 return element_count;
  }//end of parse


   private static String  required_attributes[]={"text_label_visible",
                                       "grid_lines_visible" ,
                                       "wires_visible",
                                       "signal_visible",
                                       "vibration",
                                       "flashlight",
                                       "simulation"
                                       };

  public static boolean[] parseSetting(Document doc){
    boolean settings[]=new boolean[7];

        NodeList nList = doc.getDocumentElement().getChildNodes(); //getting all nodes inside root node
        
        Element element=null;

        for(int i=0 ; i<nList.getLength(); i++){
            Node node=nList.item(i);
            if(node.getNodeType()== Node.ELEMENT_NODE && node.getNodeName().compareTo("settings")==0){
                  element=(Element) node;
                  break;
            }
        }


        
       //aquiriing project setting data  , saving in a boolean array and returning array  
        if( element!=null && element.hasAttributes()){
               for(int i=0; i<required_attributes.length; i++){
                    Attr attr=element.getAttributeNode(required_attributes[i]);
                    settings[i]=Boolean.parseBoolean(attr.getValue()) ;
               }
        }
        else{
          System.out.println("Invalid settings node");
        }


    return settings;
    }


  public static  String getElementID(Element element){
      Attr attr=element.getAttributeNode("parentid");
      String id="NO_ID";
      if(attr!=null)
           id=attr.getValue();

     return id;
  }

  public static Element getGateElementByid(String id ,Document doc){
    Element elem=null;
      try {
         
         NodeList nList = doc.getDocumentElement().getChildNodes();     
         for(int i=0; i<nList.getLength(); i++){
              Node nNode = nList.item(i);
               
              if (nNode.getNodeType() == Node.ELEMENT_NODE  ){
                      Element element=(Element)nNode; 
                      if( element.hasAttributes() && element.getAttributeNode("id").getValue().compareTo(id)==0){
                         elem=element;
                          break;
                      }
              }
         }

      } catch (Exception e) {
         System.err.println(e.toString());
      }

     return elem;      
  }//end of function




    public static Element getFirstElementByTagName(Element parentElement , String childElement){
      Element element=null;

       NodeList nodeList=parentElement.getChildNodes();
        for(int i=0 ; i<nodeList.getLength(); i++){
            Node node=nodeList.item(i);
            if(node.getNodeType()== Node.ELEMENT_NODE && node.getNodeName().compareTo(childElement)==0){
                element=(Element) node;
                break;
            }
        }

       return element;
    }

}//end of class

