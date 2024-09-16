package com.logic.logicsimulator.parsers;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.MainActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class LogicProject {
    private Document document;
    private File file;
    private Element rootElement;

    private  String projectName;
    private ProjectData projectData;
    private ProjectSetting projectSetting;

    private EditorLayout layout;
    IntegratedCircuitParser integratedCircuitParser;
    List<GateElement> gateList;
    List<TextLabelElement> textLabelElementList;

    public LogicProject(File file , @NonNull EditorLayout layout){
        document=isLogicProject(file);
        if(document==null)
            System.err.println("LogicProject->LogicProject(File)->THE PASSED FILE IS NOT A VALID LOGIC PROJECT, SEE ABOVE ERROR FOR MORE DETAILS");
        else {
            this.layout=layout;
            this.file=file;
            this.projectName=file.getName();
            this.rootElement = (Element) document.getFirstChild();
            projectData=new ProjectData(document);
            projectSetting=new ProjectSetting(document);
            integratedCircuitParser=new IntegratedCircuitParser(document , file.getName() , layout);
            gateList=new ArrayList<>();
            textLabelElementList=new ArrayList<>();
            LogicProject.parseProject(rootElement , gateList , textLabelElementList);
        }//end of else
    }//end of  LogicProject


    public LogicProject(@NonNull Document doc , String projectName  , @NonNull EditorLayout layout){
        document=doc;
        if(document==null)
            System.err.println("LogicProject->LogicProject(File)->THE PASSED FILE IS NOT A VALID LOGIC PROJECT, SEE ABOVE ERROR FOR MORE DETAILS");
        else {
            this.layout=layout;
            this.projectName=projectName;
            this.rootElement = (Element) document.getFirstChild();
            projectData=new ProjectData(document);
            projectSetting=new ProjectSetting(document);
            integratedCircuitParser=new IntegratedCircuitParser(document , "NO_NAME" , layout);
            gateList=new ArrayList<>();
            textLabelElementList=new ArrayList<>();
            LogicProject.parseProject(rootElement , gateList , textLabelElementList);
        }//end of else
    }//end of  LogicProject

   public void setFile(@NonNull File file){
        this.file=file;
   }

    public static  File createLogicProject(String projectname ,@NonNull File dir){
        File file=null;
         try{
             //creating file
              file=new File(dir , projectname);
             if(file==null) System.out.println("LogicProject->createLogicProject()->ERROR: FAILED TO CRAETE FILE ");

             DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
             DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
             Document doc= dBuilder.newDocument();
             if(doc==null) System.out.println("LogicProject->createLogicProject()->ERROR: FAILED TO CRAETE DOCUMENT ");
             // Create the root element
             Element rootElement = doc.createElement(ROOT_ELMENT_NAME);
             if(rootElement ==null) System.out.println("LogicProject->createLogicProject()->ERROR: FAILED TO CRAETE ROOT ELEMENT ");

             doc.appendChild(rootElement);

             //adding setting element in root element
             rootElement.appendChild(ProjectSetting.createDefaultProjectSettingElement(doc));

             //adding project data element in root element
             rootElement.appendChild(ProjectData.createDefaultProjectDataElement(doc));

             rootElement.appendChild(IntegratedCircuitParser.createICelement(doc , projectname));

             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             Transformer transformer = transformerFactory.newTransformer();
             DOMSource source = new DOMSource(doc);

             FileWriter fileWriter = new FileWriter(file);
             StreamResult result = new StreamResult(fileWriter);

             // Transform and save the document
             transformer.transform(source, result);

         }catch (Exception e){
              System.out.println("LogicProject->createLogicProject():ERROR ->"+e.toString());
         }

         return file;
    }


    private  static void parseProject(@NonNull Element rootElement,@NonNull List<GateElement> gates ,@NonNull List<TextLabelElement> textLabelList ) {
        NodeList elementsList = rootElement.getChildNodes();

        int total_count=0 , gate_added=0, label_added=0;

        for (int i = 0; i < elementsList.getLength(); i++) {

             if(elementsList.item(i).getNodeType()==Node.ELEMENT_NODE) {

                 if (GateElement.isGateElement((Element) elementsList.item(i))) {
                     gates.add(new GateElement((Element) elementsList.item(i)));
                     gate_added++;
                 } else if (TextLabelElement.isTextLabel((Element) elementsList.item(i))) {
                     textLabelList.add(new TextLabelElement((Element) elementsList.item(i)));
                     label_added++;
                 }

                 total_count++;
             }
        }//end of for

        System.out.println("LogicProject : parsed Total Elements :"+total_count+" Gate Eleemnts:"+gate_added+" label added: "+label_added);
    }


    public static List<GateElement> parseGateElements(@NonNull Document document){
        List<GateElement> gateElementList=new ArrayList<>();
        try{
            Element rootElement=(Element) document.getFirstChild();

            NodeList elementsList = rootElement.getChildNodes();
            for (int i = 0; i < elementsList.getLength(); i++) {

                if(elementsList.item(i).getNodeType()==Node.ELEMENT_NODE) {

                    if (GateElement.isGateElement((Element) elementsList.item(i))) {
                        gateElementList.add(new GateElement((Element) elementsList.item(i)));
                    }
                }
            }//end of for
        }
        catch (Exception e){
            System.out.println("LogicProject ->static parseGateElements(Document) :"+e);
        }


        return gateElementList;
    }


    public void addGateElement(@NonNull GateElement element){
        if(element.isValid()) {
            int prevois_size=gateList.size();
            rootElement.appendChild(element.getElement());
            gateList.add(element);
            System.out.println("LogicProject->New Gate Element Added : "+element.getType()+" prevois size:"+prevois_size+" after size:"+gateList.size());
        }
        else {
            System.out.println("LogicProject->Failed to add Gate Element , invalid GateElement , GateElement.isValid() returned false");
        }
    }
    public void removeGateElements(@NonNull GateElement element){
        if(element.isValid()) {
            int prevois_size=gateList.size();
            rootElement.removeChild(element.getElement());
            gateList.remove(element);
            System.out.println("LogicProject->Gate Element Removed : "+element.getType()+" prevois size:"+prevois_size+" after size:"+gateList.size());
        }
        else {
            System.out.println("LogicProject->Failed to add Gate Element , invalid GateElement , GateElement.isValid() returned false");
        }
    }

    public  String getProjectName(){
        return this.projectName;
    }

    public List<GateElement> getGateElements(){
        return this.gateList;
    }

    public List<TextLabelElement> getTextLabelElementList(){
        return  this.textLabelElementList;
    }
    public void addTextLabel(@NonNull TextLabelElement element){
        if(element.isValid()) {
            int prevois_size=textLabelElementList.size();
            rootElement.appendChild(element.getElement());
            textLabelElementList.add(element);
            System.out.println("LogicProject->Text label Element Added with text:"+element.getText()+" prevois size:"+prevois_size+" after addition:"+textLabelElementList.size());
        }
        else {
            System.out.println("LogicProject->Failed to add Text Label Element , invalid TextLabelElement , TextLabelElement.isValid() returned false");
        }
    }

    public void removeTextLabel(@NonNull TextLabelElement element){
        if(element.isValid()) {
            int prevois_size=textLabelElementList.size();
            rootElement.removeChild(element.getElement());
            textLabelElementList.remove(element);
            System.out.println("LogicProject->Text label Element Removed with text:"+element.getText()+"prevois size:"+prevois_size+"after addition:"+textLabelElementList.size());
        }
        else {

        }
    }

    public ProjectData getProjectData(){
        return projectData;
    }

    public ProjectSetting getProjectSetting(){
        return projectSetting;
    }

    public IntegratedCircuitParser getIntegratedCircuitParser(){
        return integratedCircuitParser;
    }

    public Document getDocument(){
        return this.document;
    }
    public boolean isValid(){
        if(rootElement!=null )
            return true;

        return false;
    }



    public static Document isLogicProject(@NonNull File file){
        Document doc=null;
        try {
            DocumentBuilderFactory dbfac=  DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder=dbfac.newDocumentBuilder();
            doc=dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            ProjectData data=new ProjectData(doc);
            data.getCreatedDate();
        }
        catch (Exception e){
            System.err.println( "LogicProject->isLogicProject(File)->ERROR:"+e.toString());
            doc=null;
        }


        return doc;
    }


    public  static boolean update(@NonNull Document document , @NonNull File file){
        boolean status=false;
        try{
            // Save the updated document to the existing file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            // Transform and save the document
            transformer.transform(source, result);
            status=true;
        }catch (Exception e){
             System.err.println("LogicProject ->update()->"+e);
        }
        return status;
    }


    public void save(){
        if(this.file!=null) {
            projectData.setModifiedDate(MainActivity.getPresentDate());
            update(this.document, this.file);
            System.out.println("LogicProject Updated GateElements:"+gateList.size()+" TextLabelElements:"+textLabelElementList.size() );
        }
        else
            System.out.println("LogicProject  not updated as File is null GateElements:"+gateList.size()+" TextLabelElements:"+textLabelElementList.size() );
    }





    public static String ROOT_ELMENT_NAME="root";
}
