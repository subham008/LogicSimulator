package com.logic.logicsimulator.parsers;

import android.content.Context;
import android.content.SharedPreferences;

import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

public class RepairAction {
    Context context;
    SharedPreferences sharedPreferences;
    public RepairAction(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(REPAIR_DATA_NAME , Context.MODE_PRIVATE);
    }


    public void RepairDuplicateConnections(File dir){
        if(! sharedPreferences.getBoolean(REPAIR_DUPLICATE_CONNECTIONS , false))
            return;

        //repair job

        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                   Document document=LogicProject.isLogicProject(file);
                    List<GateElement> gateElementList=LogicProject.parseGateElements(document);

                }
            }
        }
        //if success
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(REPAIR_DUPLICATE_CONNECTIONS , true);
        editor.apply();
    }


    private int removeDuplicateConnection(GateElement element){
        int count=0;


        return count;
    }
    public static final  String REPAIR_DATA_NAME="Repair";
    public static final  String REPAIR_DUPLICATE_CONNECTIONS="duplicate_connection";
}
