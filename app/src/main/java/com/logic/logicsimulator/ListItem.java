package com.logic.logicsimulator;



import org.w3c.dom.Document;

import com.logic.logicsimulator.parsers.LogicProject;
import com.logic.logicsimulator.parsers.ProjectData;

import java.io.File;


public class ListItem  {
   private String p_name;

   public Document document;
    public ProjectData projectData;

    public ListItem(String project_name , File dir){
        p_name=project_name;

        File projectFile=new File(dir,project_name);

        document=LogicProject.isLogicProject(projectFile);

        try {
            projectData= new ProjectData(document);
        }
        catch (Exception e){
            System.err.println(e.toString());
            document=null;
        }

    }


    public void reload(File dir){

        File projectFile=new File(dir,p_name);

        document= LogicProject.isLogicProject(projectFile);
        try {
            projectData= new ProjectData(document);
        }
        catch (Exception e){
            System.err.println(e.toString());
            document=null;
        }
    }

    public boolean isFileValid(){
        if(document==null)
           return false;
        return true;
    }


    public String getProjectName(){
        return p_name;
    }
    public int getConnectionCount(){
         if(projectData!=null){
             return projectData.getData(ProjectData.CONNECTION_COUNT);
         }

         return 0;
    }

    public int getElementsClount(){
        if(projectData!=null){
            return projectData.getData(ProjectData.ELEMENT_COUNT);
        }

        return 0;
    }
    public String getCreationdate(){
        if(projectData!=null)
        {
            return projectData.getCreatedDate();
        }

        return "NO DATE FOUND";
    }

    public String getLastModifiedDate(){
        if(projectData!=null)
        {
            return projectData.getModifiedDate();
        }

        return "NO DATE FOUND";
    }


    public void setProjectName(String name){
        this.p_name=name;
    }

}
