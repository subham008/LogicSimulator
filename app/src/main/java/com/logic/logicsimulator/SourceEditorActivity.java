package com.logic.logicsimulator;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class SourceEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_editor);
        getSupportActionBar().hide();

        String projectName=getIntent().getStringExtra("project_name");
        if(projectName==null)
            projectName="Project";

        TextView sourcefilename=findViewById(R.id.source_filename);
        sourcefilename.setText(projectName);

        File projectFile=new File(getFilesDir(),projectName);

        TextView sourceEditor=findViewById(R.id.source_editor);


        //adding file data to EditText
        readFileAndSetToEditText(projectFile,sourceEditor);


        ImageButton save=findViewById(R.id.source_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  saveTextToFile(projectFile,sourceEditor);
            }
        });


    }

    public void readFileAndSetToEditText(File file, TextView editText) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            bufferedReader.close();
            reader.close();

            editText.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the error gracefully, e.g., display a message to the user
        }
    }

    public void saveTextToFile(File file, TextView editText) {
        try {
            // Get the text from the EditText
            String textToSave = editText.getText().toString();

            // Create a FileWriter to write to the file
            FileWriter writer = new FileWriter(file, false); // Set false to overwrite existing content

            // Write the text to the file
            writer.write(textToSave);

            // Close the writer to release resources
            writer.close();

            // Inform the user of successful saving
        } catch (Exception e) {
           System.err.println(e.toString());
        }
    }


}