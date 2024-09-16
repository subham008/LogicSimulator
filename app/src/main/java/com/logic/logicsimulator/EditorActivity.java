package com.logic.logicsimulator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.logic.logicsimulator.Gates.GateParser;
import com.logic.logicsimulator.Gates.ICGate;
import com.logic.logicsimulator.Gates.TextLabel;
import com.logic.logicsimulator.parsers.IntegratedCircuitParser;
import com.logic.logicsimulator.parsers.LinkPinElement;
import com.logic.logicsimulator.parsers.LogicProject;
import com.logic.logicsimulator.parsers.ProjectData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class EditorActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    public   void callPinLabelDailog(Pin pin){
         //pin label change code
        View dialogView = getLayoutInflater().inflate(R.layout.change_pin_name, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this );
        alertDialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText pin_name=dialogView.findViewById(R.id.pin_name_editor);
        pin_name.setText(pin.getPinName());

        Button save_name=dialogView.findViewById(R.id.pin_name_save);
        Button cancel_name=dialogView.findViewById(R.id.pin_name_cancel);

        save_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pin_name=pin_name.getText().toString();
                pin.setPinName(new_pin_name);
                pin.parent.invalidate();
                makeIntegratedBottomSheet.invadateItems();
                alertDialog.dismiss();
            }
        });//end of save name button listener

        cancel_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });//en  of cancel name listener
    }



    private void showChangeLabelDailog(EditorLayout frameLayout) {
        TextLabel label=frameLayout.getTextLabelSelected();
         if(label!=null){
             float x=label.getX();
             float y=label.getY();
             String text=label.getLabelText();
             int fontsize=label.getFontSize();

             //pin label change code
             View dialogView = getLayoutInflater().inflate(R.layout.text_label_add_layout, null);
             AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this );
             alertDialogBuilder.setView(dialogView);
             // Create and show the AlertDialog
             AlertDialog dialog = alertDialogBuilder.create();
             dialog.show();
             dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

             TextView textSizePreview=dialogView.findViewById(R.id.text_size_preview);
             SeekBar textSize=dialogView.findViewById(R.id.text_size_seekbar);
             EditText editText=dialogView.findViewById(R.id.text_label_text_editor);
             Button save=dialogView.findViewById(R.id.text_label_add);

             textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                 @Override
                 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                     textSizePreview.setText(String.valueOf(progress));
                 }

                 @Override
                 public void onStartTrackingTouch(SeekBar seekBar) {

                 }

                 @Override
                 public void onStopTrackingTouch(SeekBar seekBar) {

                 }
             });


             textSize.setProgress(fontsize);

             editText.setText(text);

             save.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     frameLayout.removeTextLabel(label);
                     TextLabel label=new TextLabel(getApplicationContext() ,editText.getText().toString() , textSize.getProgress() , 0xffffffff);
                     label.setX(x);
                     label.setY(y);
                     label.setClickable(true);
                     label.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             frameLayout.setTextLabelFocus(label);
                         }
                     });
                     frameLayout.addTextLabel(label);
                     frameLayout.setTextLabelFocus(label);
                     dialog.dismiss();
                 }
             });
         }
         else {
             Toast.makeText(getApplicationContext(),"NO LABEL IS SELECTED" , Toast.LENGTH_SHORT ).show();
         }
    }

   public void showPinWarningDialog(){
       View dialogView = getLayoutInflater().inflate(R.layout.pin_warning_dialog, null);
       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this );
       alertDialogBuilder.setView(dialogView);

       // Create and show the AlertDialog
       alertDialog = alertDialogBuilder.create();
       alertDialog.show();

       alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

       Button wok=dialogView.findViewById(R.id.pin_warning_ok);
       wok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertDialog.dismiss();
           }
       });
   }



   private make_integrated_bottom_sheet makeIntegratedBottomSheet=null;
    EditorLayout mainrame;
    LogicProject logicProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().hide();

        Intent intent=getIntent();

        TextView projectNameBar=findViewById(R.id.project_name_bar);
        projectNameBar.setText(intent.getStringExtra("project_name"));

        LinearLayout recenter=findViewById(R.id.recenter);
        LinearLayout touch_mode=findViewById(R.id.touch_mode);
        LinearLayout edit_mode=findViewById(R.id.edit_mode);
        LinearLayout delete=findViewById(R.id.delete_button);
        LinearLayout addPin=findViewById(R.id.add_pin_button);
        LinearLayout makeIC=findViewById(R.id.make_ic_button);
        LinearLayout disconnect=findViewById(R.id.disconnect_button);
        LinearLayout pin_label=findViewById(R.id.pin_name);
        LinearLayout customize_gate=findViewById(R.id.customize_gate);
        LinearLayout sourceEditor=findViewById(R.id.source_layout);
        LinearLayout changeLabel=findViewById(R.id.text_label_change);
        LinearLayout removePin=findViewById(R.id.remove_pin_button);

        ImageButton project_option=findViewById(R.id.project_options);
        ImageButton save_button=findViewById(R.id.project_save);
        FloatingActionButton  addButton = findViewById(R.id.Add_gate);

        TextView elementsCount=findViewById(R.id.editor_elements_count);
        TextView connectionCount=findViewById(R.id.editor_connections_count);

        String filename = intent.getStringExtra("project_name");

        // ************ IMPORTATNT HIDING SOME FAETURES DUE TO INCOMEPLETE IMPLEMENTATIONS*************
        // HIDING IC FEATURE
        //addPin.setVisibility(View.GONE);
        //makeIC.setVisibility(View.GONE);
        //MORE HIDE OPERATIONS IN FILE: GateBottomFragment

        //HIDING SOURCE EDITOR BUTTON AS IT IS FOR DEVELOPER ONLY
        //sourceEditor.setVisibility(View.GONE);

        mainrame=findViewById(R.id.main_frame);

             logicProject = new LogicProject(new File(getFilesDir(), filename) , mainrame);
            if (logicProject.isValid() && logicProject.getProjectData() != null && logicProject.getProjectSetting() != null) {
                System.out.println("*********LogicProject success : ProjectData & ProjectSetting*******");
            }




        mainrame.setDocument(logicProject.getDocument());
        mainrame.setX(logicProject.getProjectData().getData(ProjectData.EDITOR_X));
        mainrame.setY(logicProject.getProjectData().getData(ProjectData.EDITOR_Y));
        mainrame.setLogicProject(logicProject);
        if(logicProject.getProjectData().getDataFloat(ProjectData.SCALE_X) > 0.5)
           mainrame.setScaleX(logicProject.getProjectData().getDataFloat(ProjectData.SCALE_X));
        if(logicProject.getProjectData().getDataFloat(ProjectData.SCALE_Y) > 0.5)
           mainrame.setScaleY(logicProject.getProjectData().getDataFloat(ProjectData.SCALE_Y));



        mainrame.DEBUG_MODE=false;

        ProjectSettingBottomSheet.setSetting(mainrame,logicProject.getProjectSetting() , logicProject.getProjectData());//setting up last saves setting of the project
        GateParser gateParser=new GateParser();

        //addition of elemnets started
        gateParser.addGates(logicProject ,mainrame , getApplicationContext() );


        elementsCount.setText(String.valueOf(mainrame.getElementCount()));
        String connection_count=String.valueOf(mainrame.connectionWires.size());
        connectionCount.setText( connection_count );
        logicProject.getProjectData().setData(ProjectData.ELEMENT_COUNT , mainrame.getElementCount());
        logicProject.getProjectData().setData(ProjectData.CONNECTION_COUNT , mainrame.connectionWires.size());

        mainrame.changeGateTask=new BasicTask(){
            @Override
            public void run(){
                elementsCount.setText(String.valueOf(mainrame.getElementCount()));
                logicProject.getProjectData().setData(ProjectData.ELEMENT_COUNT , mainrame.getElementCount());
            }
        };

        mainrame.changeConnectionTask=new BasicTask(){
            @Override
            public void run(){
                String connection_count=String.valueOf(mainrame.connectionWires.size());
                connectionCount.setText( connection_count );
                logicProject.getProjectData().setData(ProjectData.CONNECTION_COUNT , mainrame.connectionWires.size());
            }
        };
       // elementsCount.setText(String.valueOf(mainrame.getElementCount()));

        logicProject.getIntegratedCircuitParser().parsePinMap(mainrame);


      LinearLayout exportLayout=findViewById(R.id.export_layout);
      exportLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
               exportProject(intent.getStringExtra("project_name"));
          }
      });


        GateBottomFragment GateOptionSheet=new GateBottomFragment(mainrame ,filename);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GateOptionSheet.show(getSupportFragmentManager() , GateOptionSheet.getTag());
            }
        });


        recenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainrame.animate().x(-2500).y(-2500).setDuration(500).start();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProjectState();
            }
        });

       //ColorStateList select_tint=ColorStateList.valueOf(0x00838f);
       //ColorStateList default_background=ColorStateList.valueOf(getResources().getColor(R.color.dark_grey));

      touch_mode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              edit_mode.setBackgroundColor(getResources().getColor(R.color.dark_grey));
              disconnect.setBackgroundColor(getResources().getColor(R.color.dark_grey));
              touch_mode.setBackgroundColor(getResources().getColor(com.github.dhaval2404.colorpicker.R.color.cyan_800));

               mainrame.setTouchMode();
          }
      });



      edit_mode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              touch_mode.setBackgroundColor(getResources().getColor(R.color.dark_grey));
              disconnect.setBackgroundColor(getResources().getColor(R.color.dark_grey));
              edit_mode.setBackgroundColor(getResources().getColor(com.github.dhaval2404.colorpicker.R.color.cyan_800));

              mainrame.setSelectMode();
          }
      });

      disconnect.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              touch_mode.setBackgroundColor(getResources().getColor(R.color.dark_grey));
              edit_mode.setBackgroundColor(getResources().getColor(R.color.dark_grey));
              disconnect.setBackgroundColor(getResources().getColor(com.github.dhaval2404.colorpicker.R.color.cyan_800));

              mainrame.setDeleteMode();
          }
      });

      delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if(BasicGateView.selected_view!=null) {
                int count=makeIntegratedBottomSheet.removeAllPinFromGate(BasicGateView.selected_view , logicProject);
                System.out.println("LINK PIN DELETED FROM BELONG TO GATE DELETED :"+ count);
                mainrame.removeGate(BasicGateView.selected_view);
            }
            else if(mainrame.getTextLabelSelected()!=null){
                mainrame.removeTextLabel(mainrame.getTextLabelSelected());
            }
            else
                Toast.makeText(EditorActivity.this, "NO ANY ELEMENT IS SELECTED", Toast.LENGTH_SHORT).show();
          }
      });

      pin_label.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(BasicGateView.selected_pin!=null)
               callPinLabelDailog(BasicGateView.selected_pin);
              else
                  Toast.makeText(EditorActivity.this, "NO ANY PIN IS SELECTED", Toast.LENGTH_SHORT).show();
          }
      });


      CustomGateBottomsheet customGateBottomsheet=new CustomGateBottomsheet();
      customize_gate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(BasicGateView.selected_view!=null) {
                  customGateBottomsheet.element = BasicGateView.selected_view;
                  customGateBottomsheet.show(getSupportFragmentManager(), customGateBottomsheet.getTag());
              }
              else
                  Toast.makeText(EditorActivity.this, "NO ANY ELEMENT IS SELECTED", Toast.LENGTH_SHORT).show();
          }
      });

       makeIntegratedBottomSheet=new make_integrated_bottom_sheet(mainrame , logicProject.getIntegratedCircuitParser());

        addPinsToIClist(makeIntegratedBottomSheet , logicProject.getIntegratedCircuitParser());

      addPin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(BasicGateView.selected_pin!=null){
                  if(BasicGateView.selected_pin.parent.gateType == ICGate.IC_GATE_NAME)
                     showPinWarningDialog();
                  else  if( !gateParser.isInputGate(BasicGateView.selected_pin.parent.gateType) && !gateParser.isOutputGate(BasicGateView.selected_pin.parent.gateType) )
                    if( makeIntegratedBottomSheet.addPintoIC( new LinkPinElement(logicProject.getDocument() ,BasicGateView.selected_pin , BasicGateView.selected_pin.pinOreintation) , BasicGateView.selected_pin)) {
                     BasicGateView.selected_pin.setDefaultColor(Color.YELLOW);
                     Toast.makeText(getApplicationContext(), "Pin added", Toast.LENGTH_SHORT).show();
                     }
                    else {
                    Toast.makeText(getApplicationContext(), "Pin Already added", Toast.LENGTH_SHORT).show();

                     }
                 else
                      Toast.makeText(getApplicationContext(), "INPUT or OUTPUT Component pins can not be added in IC", Toast.LENGTH_LONG).show();
              }else{
                  Toast.makeText(getApplicationContext(), "NO PIN SELECTED", Toast.LENGTH_SHORT).show();
              }
          }
      });


      removePin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(BasicGateView.selected_pin!=null){
                  if(makeIntegratedBottomSheet.isPinBelongToIC(BasicGateView.selected_pin)){
                      makeIntegratedBottomSheet.removePin(BasicGateView.selected_pin,logicProject.getIntegratedCircuitParser());
                      Toast.makeText(getApplicationContext(),"PIN REMOVED FROM IC",Toast.LENGTH_SHORT).show();
                  }
                  else
                      Toast.makeText(getApplicationContext(),"SELECTED PIN DOES NOT BELONG TO IC" , Toast.LENGTH_LONG).show();
              }else {
                  Toast.makeText(getApplicationContext(),"NO ANY PIN IS SELECTED" , Toast.LENGTH_LONG).show();
              }
          }
      });

      makeIC.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              makeIntegratedBottomSheet.show(getSupportFragmentManager(),makeIntegratedBottomSheet.getTag());
          }
      });

      ProjectSettingBottomSheet navigationBottomFragment=new ProjectSettingBottomSheet(mainrame , logicProject.getProjectSetting(), logicProject.getProjectData());
      project_option.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
          navigationBottomFragment.show(getSupportFragmentManager(),navigationBottomFragment.getTag());
    }

});

      changeLabel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              showChangeLabelDailog(mainrame);
          }
      });


      Intent srcEditorIntent=new Intent(getApplicationContext(),SourceEditorActivity.class);
      sourceEditor.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              srcEditorIntent.putExtra("project_name",filename);
              startActivity(srcEditorIntent);
          }
      });

    }//end of onCreate


    public static int CREATE_FILE_REQUEST_CODE=98647;
    public  void exportProject(String project){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/xml"); // Adjust for your file type
        intent.putExtra(Intent.EXTRA_TITLE, project); // Suggest
        intent.putExtra("PROJECT_NAME", project);
        ActivityCompat.startActivityForResult(this,intent, CREATE_FILE_REQUEST_CODE, null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                //FileInputStream in = new FileInputStream(file);
               // System.err.println("**********export project name: "+data.getStringExtra("PROJECT_NAME"));
                FileInputStream  in=openFileInput("Project");
                byte[] buffer = new
                        byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                Toast.makeText(this, "File exported successfully!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "File export failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }// end of onActivityResult


  private void addPinsToIClist(make_integrated_bottom_sheet mk , IntegratedCircuitParser parser){

      System.out.println("PIN MAP SIZE IS :"+parser.getPinMap().size());
      for(Map.Entry<Pin , LinkPinElement> entry : parser.getPinMap().entrySet()){
          mk.addPinToList(entry.getValue());
      }
  }



   private void saveProjectState(){
       mainrame.updateAll();
       logicProject.save();
   } // saveProjectState


    @Override
    protected void onDestroy() {
        saveProjectState();
        super.onDestroy();
    }
}//end of class