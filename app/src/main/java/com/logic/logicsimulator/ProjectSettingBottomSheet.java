package com.logic.logicsimulator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.logic.logicsimulator.parsers.ProjectData;
import com.logic.logicsimulator.parsers.ProjectSetting;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ProjectSettingBottomSheet extends BottomSheetDialogFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private ProjectSetting setting;
    private ProjectData projectData;
    EditorLayout main_layout;
    public ProjectSettingBottomSheet(@NonNull EditorLayout editorLayout ,@NonNull ProjectSetting set  , @NonNull ProjectData projectData) {
        main_layout=editorLayout;
        this.setting=set;
        this.projectData=projectData;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    public static void setSetting(@NonNull  EditorLayout layout , @NonNull ProjectSetting set , @NonNull ProjectData projectData){


            EditorLayout.gridLines=set.getSetting(ProjectSetting.GRID_VISIBLE);
            //layout.invalidate();


            EditorLayout.draw_connection=set.getSetting(ProjectSetting.WIRES_VISIBLE);
            //layout.invalidate();

            layout.setSignalColorVisible( set.getSetting(ProjectSetting.SIGNAL_VISIBLE));


            //setting text visiblity
            layout.setTextLabelVisible( set.getSetting(ProjectSetting.TEXT_VISIBLE) );

            layout.invalidate();

    }//end of setSetting

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View navigationView=inflater.inflate(R.layout.fragment_navigation_bottom, container, false);

        Button set_default_setting=navigationView.findViewById(R.id.set_default_sett);
        Button version_save=navigationView.findViewById(R.id.project_setting_version_save);
        EditText version_text=navigationView.findViewById(R.id.project_setting_version_edittext);

        SwitchCompat text_visible=navigationView.findViewById(R.id.text_visible_switch);
        SwitchCompat grid_visible=navigationView.findViewById(R.id.grid_visible_switch);
        SwitchCompat wires_visible=navigationView.findViewById(R.id.wires_visible_switch);
        SwitchCompat signal_visible=navigationView.findViewById(R.id.signal_visible_switch);



       text_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               setting.setSetting(ProjectSetting.TEXT_VISIBLE,isChecked);
               main_layout.setTextLabelVisible(isChecked);
               main_layout.invalidate();

           }
       });

        grid_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  //System.out.println("*******grid switch chnaged*********:value:"+isChecked);
                  EditorLayout.gridLines=isChecked;
                  setting.setSetting(ProjectSetting.GRID_VISIBLE,isChecked);
                  main_layout.invalidate();
            }
        });

        wires_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //System.out.println("*******wires switch chnaged********* value:"+isChecked);
                EditorLayout.draw_connection=isChecked;
                setting.setSetting(ProjectSetting.WIRES_VISIBLE,isChecked);
                main_layout.invalidate();
            }
        });

        signal_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // System.out.println("*******signal switch chnaged*********:value :"+isChecked);
                main_layout.setSignalColorVisible(isChecked);
                setting.setSetting(ProjectSetting.SIGNAL_VISIBLE,isChecked);
                if(signal_visible.isChecked())
                    EditorLayout.signalColor=0xFF1A09FF;
                else
                    EditorLayout.signalColor=0xffffffff;

                main_layout.invalidate();
            }
        });




        set_default_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  text_visible.setChecked(true);
                  grid_visible.setChecked(true);
                  wires_visible.setChecked(true);
                  signal_visible.setChecked(true);

            }
        });

        version_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ver=version_text.getText().toString();
                projectData.setVersion(ver);
            }
        });


        version_text.setText(projectData.getVersion());
        text_visible.setChecked(setting.getSetting(ProjectSetting.TEXT_VISIBLE));
        grid_visible.setChecked(setting.getSetting(ProjectSetting.GRID_VISIBLE));
        wires_visible.setChecked(setting.getSetting(ProjectSetting.WIRES_VISIBLE));
        signal_visible.setChecked(setting.getSetting(ProjectSetting.SIGNAL_VISIBLE));



        // Inflate the layout for this fragment
        return navigationView;
    }


}