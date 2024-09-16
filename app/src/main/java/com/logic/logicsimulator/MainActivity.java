package com.logic.logicsimulator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.logic.logicsimulator.parsers.LogicProject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ListItem> item_list;
    AlertDialog alertDialog;
    ListView listView;
    ProjecListAdapter listAdapter;

    boolean isNameExist(String name){
        for(int i=0 ; i<item_list.size();i++){
            if(item_list.get(i).getProjectName().compareTo(name)==0)
                return true;
        }
         return  false;
    }

    public  void showAddProjectDialog(){
        // Get the layout inflater
        View dialogView = getLayoutInflater().inflate(R.layout.addproject_dialog_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this );
        alertDialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button create_project= dialogView.findViewById(R.id.create_button);
        Button cancel_project= dialogView.findViewById(R.id.cancel_button);
        EditText project_name = dialogView.findViewById(R.id.editText);
        TextView error_view=alertDialog.findViewById(R.id.error_details);

        cancel_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        create_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item_list == null)
                    item_list=new ArrayList<>();
                if(isNameExist(project_name.getText().toString())) {
                    error_view.setText("*Project with same name exist, Try something different");
                    error_view.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    try {
                        File file=LogicProject.createLogicProject(project_name.getText().toString() , getFilesDir());
                        if(file!=null) System.out.println("MainActivity->ERROR : project created successs");
                        else System.out.println("MainActivity->ERROR : File returned is null, Fialed to create Project");
                    }
                    catch (Exception e){
                        System.err.println("**********ERROR OCCURED**********");
                        System.err.println(e.toString());
                    }
                item_list.add( 0 , new ListItem( project_name.getText().toString() , getFilesDir()));
                listAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
                }

            }
        });
        // Build the AlertDialog


        //checking file size
        File file=new File(getFilesDir() , project_name.getText().toString());
        if (file.exists())
            System.err.println("File size is :"+file.length());
        else
            System.err.println("File not found");

    }// end of  showAddProjectDialog



    public void showUnderstandingDialog(){
        // Get the layout inflater
        View dialogView = getLayoutInflater().inflate(R.layout.ad_understanding, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this );
        alertDialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Button understanding_button=dialogView.findViewById(R.id.understand_button);
        understanding_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("user accepted the ad system");
                alertDialog.dismiss();
            }
        });

    }

    public void  ProjectsListInit(Context context , List<ListItem> fileNames){
        File filesDir = context.getFilesDir();

        if (filesDir.exists()) {
            File[] files = filesDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileNames.add(new ListItem(file.getName() ,getFilesDir()));
                }
            }
        }

    }

   public static String getPresentDate(){

       Date currentDate = new Date();

       // Specify the desired date and time format
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       // Format the date and time as a string
       String formattedDateTime = dateFormat.format(currentDate);

       return formattedDateTime;
   }


   private final int IMPORT_FILE_REQUEST_CODE=625373;
   private final int READ_EXTERNEL_PERMISSION_REQUEST=72824824;
    private boolean importFile(){
        String permissions[]={Manifest.permission.READ_EXTERNAL_STORAGE  };
      if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
          if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
          // || ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
          {
              //super.requestPermissions( permissions, requesCode);
              ActivityCompat.requestPermissions(this,permissions,READ_EXTERNEL_PERMISSION_REQUEST);
          }//end of nested if
      }//end of if

        boolean success = false;

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*"); // Adjust for specific file types if needed
        ActivityCompat.startActivityForResult(this,intent, IMPORT_FILE_REQUEST_CODE , null);

        return success; // Placeholder, actual result handled in onActivityResult
    }//end of importFile



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       getSupportActionBar().hide();

       listView=findViewById(R.id.project_list);
      //test list
        item_list =new ArrayList<>();

        ProjectsListInit(getApplicationContext() , item_list);

        listAdapter=new ProjecListAdapter(this , item_list ,getLayoutInflater());

        listView.setAdapter(listAdapter);

        TextView emptyView=findViewById(R.id.no_projects_text);
        listView.setEmptyView(emptyView);

        ImageButton add_button=findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProjectDialog();

            }
        });



      ImageButton import_button=findViewById(R.id.imp_button);
        import_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //importFile();
                //listAdapter.notifyDataSetChanged();
               importFile();
            }
        });


       Intent setting_intent=new Intent(getApplicationContext(),settings_activity.class);
        ImageButton setting_but=findViewById(R.id.setting_button);
         setting_but.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //setting activity
                 startActivity(setting_intent);
             }
         });


         AppSetting setting=new AppSetting(this);
         EditorLayout.signalColor=setting.getSignalColor();
         EditorLayout.selectColor=setting.getSelectColor();
         EditorLayout.backgroundColor=setting.getBackgroundColor();



    }//end of onCraete

    @Override
    public void onRequestPermissionsResult(int rc ,String[] per , int[] gr){
        super.onRequestPermissionsResult(rc ,per , gr);
        if(rc==READ_EXTERNEL_PERMISSION_REQUEST){
            if(gr.length>0 && gr[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == IMPORT_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            String internalFileName = getFilenameFromUri(uri) ;// Adjust filename as needed

            if(isNameExist(internalFileName)) {
                internalFileName = internalFileName + "1";
                Toast.makeText(getApplicationContext(),"FILE NAME IS ALREADY IS TAKEN , RENAME TO "+internalFileName,Toast.LENGTH_LONG);
            }

            try (InputStream in = getContentResolver().openInputStream(uri);
                 OutputStream out = openFileOutput(internalFileName, Context.MODE_PRIVATE)) {
                // Copy file contents from input to output stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }

                Toast.makeText(this, "File imported successfully!", Toast.LENGTH_SHORT).show();
                item_list.add(new ListItem(internalFileName,getFilesDir()));
                listAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                Toast.makeText(this, "File import failed.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            System.err.println("Invalid Request code found in function on onActivityResult(..): "+requestCode);
        }
    }

    public String getFilenameFromUri(Uri uri){
        DocumentFile documentFile = DocumentFile.fromSingleUri(getApplicationContext(), uri);
        if (documentFile != null) {
           return documentFile.getName();
            // Use the extracted filename
        }
        return "NO_NAME_PROJECT";
    }   //end of  getFilenameFromUri

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("****************onResume called***************");
        for(ListItem item:item_list)
            item.reload(getFilesDir());
        listAdapter.notifyDataSetInvalidated();

    }
}//end  of class