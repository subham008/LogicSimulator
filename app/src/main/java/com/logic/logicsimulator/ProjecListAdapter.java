package com.logic.logicsimulator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

public class ProjecListAdapter extends ArrayAdapter<ListItem> {
  private Context context;
  private LayoutInflater inflater;
    Intent srcintent;
    Intent editorIntent;
    public ProjecListAdapter(Context con , List<ListItem> items , LayoutInflater in){
        super(con , 0 ,items );
        context=con;
        inflater=in;
        srcintent = new Intent( context, SourceEditorActivity.class);
        editorIntent=new Intent(context , EditorActivity.class);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.project_list_layout, parent, false);
        }

        TextView titleTextView = listItemView.findViewById(R.id.project_name);
        titleTextView.setText(getItem(position).getProjectName());
        titleTextView.setSelected(true);
        ListItem item=getItem(position);
        ImageButton more_buttom=listItemView.findViewById(R.id.more);
        more_buttom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(item.isFileValid())
                showProjectOptionsDialog(position);
                else
                 showInvalidFormatDialog(position);
            }
        });

        LinearLayout layout=listItemView.findViewById(R.id.item_layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                if(item.isFileValid()) {
                    System.out.println("*******Passed project name is :" + getItem(position).getProjectName());
                    editorIntent.putExtra("project_name", getItem(position).getProjectName());
                    context.startActivity(editorIntent);
                }
                else
                    showInvalidFormatDialog(position);
            }
        });


        return listItemView;
    }

private Button delete_back,delete_confirm;
private TextView delete_projectname;

    public void showProjectOptionsDialog(int position){
        View dialogView = inflater.inflate(R.layout.project_option_dlayout, null);
        //View deleteConfirm=inflater.inflate(R.layout.delete_confirm_layout, null);

        ListItem item=super.getItem(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

         item=getItem(position);

        TextView project_name=alertDialog.findViewById(R.id.option_project_name);
        project_name.setText(item.getProjectName());
        project_name.setSelected(true);

        TextView elem_count=alertDialog.findViewById(R.id.option_elements_count);
        elem_count.setText( String.valueOf(item.getElementsClount()));

        TextView conn_count=alertDialog.findViewById(R.id.option_connevtions_count);
        conn_count.setText(String.valueOf(item.getConnectionCount()));

        TextView date_creation=alertDialog.findViewById(R.id.option_creationdate_count);
        date_creation.setText(item.getCreationdate());

        TextView last_modi=alertDialog.findViewById(R.id.option_lastmodified_count);
        last_modi.setText(item.getLastModifiedDate());



        LinearLayout renameLayout=alertDialog.findViewById(R.id.rename_layout);
        ImageButton rename_button=alertDialog.findViewById(R.id.rename_project_buttonj);
        rename_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                renameLayout.setVisibility(View.VISIBLE);
                ListItem item=ProjecListAdapter.super.getItem(position);
                EditText input=alertDialog.findViewById(R.id.rename_edit_text);
                input.setText(item.getProjectName());

                Button save=alertDialog.findViewById(R.id.rename_save_button);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        renameFileInInternalStorage(item.getProjectName(),input.getText().toString());
                        item.setProjectName(input.getText().toString());
                        project_name.setText(item.getProjectName());
                        project_name.setText(item.getProjectName());
                        notifyDataSetChanged();
                        renameLayout.setVisibility(View.GONE);
                    }
                });
            }
        });


        ImageButton delete_button=alertDialog.findViewById(R.id.option_delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setContentView(R.layout.delete_confirm_layout);
                alertDialog.show();

                ListItem item=ProjecListAdapter.super.getItem(position);
                delete_back=alertDialog.findViewById(R.id.delete_back);
                delete_confirm=alertDialog.findViewById(R.id.delete_confirm);

                delete_projectname=alertDialog.findViewById(R.id.delete_project_name);
                delete_projectname.setText(item.getProjectName());
                delete_projectname.setSelected(true);

                delete_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });//end of delete_back listener

                delete_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (deleteFilePermanently(getContext(), item.getProjectName())) {
                                remove(item);
                                alertDialog.dismiss();
                                Toast.makeText(context, item.getProjectName() + " deleted permanently", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(context, item.getProjectName() + "Failed to deleted file", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e){
                            System.err.println(e.toString());
                        }
                    }//end of onclick
                });
            }
        });

        ImageButton source_button=alertDialog.findViewById(R.id.option_source_editor);
        source_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srcintent.putExtra("project_name",ProjecListAdapter.super.getItem(position).getProjectName());
                getContext().startActivity(srcintent);
            }
        });

        ImageButton share_button=alertDialog.findViewById(R.id.option_share);
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareFile(getItem(position).getProjectName());
            }
        });

        //HIDING SOURCE EDITOR AS IT IS FOR DEVELOPER ONLY
        //source_button.setVisibility(View.GONE);

        LinearLayout open=alertDialog.findViewById(R.id.option_open);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorIntent.putExtra("project_name" , getItem(position).getProjectName());
                context.startActivity(editorIntent);
            }
        });


    }// end of show project dailog

public void showInvalidFormatDialog(int position){
    View dialogView = inflater.inflate(R.layout.invalid_file, null);
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setView(dialogView);

    // Create and show the AlertDialog
    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    Button keep=dialogView.findViewById(R.id.keep_button);
    keep.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             alertDialog.dismiss();
        }
    });

    Button delete=dialogView.findViewById(R.id.delete_button);
    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             deleteFilePermanently(getContext(),getItem(position).getProjectName());
             remove(ProjecListAdapter.super.getItem(position));
             notifyDataSetChanged();
             alertDialog.dismiss();
        }
    });
}//end of show  invalid dilog

    public boolean deleteFilePermanently(Context context, String fileName) {
        File fileToDelete = new File(context.getFilesDir(), fileName);

        // Check if the file exists and is a regular file (not a directory)
        if (fileToDelete.exists() && fileToDelete.isFile()) {

            try {
                return fileToDelete.delete();
            }
            catch(Exception e){
                System.err.println(e.toString());
            }
        } else {
            // File doesn't exist or is not a regular file
            return false;
        }

        return false;
    }


    public void shareFile(String filename) {
        // Assuming you have a File object representing the file you want to share
        File fileToShare = new File(getContext().getFilesDir(), filename);

        // Create a new Intent with ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("*/*");

        // For API 24 and above, use FileProvider to get a content URI
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationInfo().packageName, fileToShare);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            contentUri = Uri.fromFile(fileToShare);
        }

        // Set the data for the intent
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);

        // Create a chooser intent
        String title = "Share File";
        Intent chooserIntent = Intent.createChooser(shareIntent, title);

        // Start the chooser activity
        getContext().startActivity(chooserIntent);
    }


    public boolean renameFileInInternalStorage(String oldFileName, String newFileName) {
        boolean success = false;

        File oldFile = new File(getContext().getFilesDir(), oldFileName);
        File newFile = new File(getContext().getFilesDir(), newFileName);

        try {
            if (oldFile.exists()) {
                success = oldFile.renameTo(newFile);
            } else {
                Log.w("FileRename", "Old file does not exist: " + oldFileName);
            }
        } catch (SecurityException e) {
            Log.e("FileRename", "Error renaming file: " + e.getMessage());
            // Handle the security exception appropriately, e.g., notify the user
        }

        return success;
    }

}// end of class
