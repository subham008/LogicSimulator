package com.logic.logicsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;

import org.jetbrains.annotations.NotNull;

public class settings_activity extends AppCompatActivity {

    private static final int TASK_SIGNAL_COLOR=1;
    private static final int TASK_SELECT_COLOR=2;
    private static final int TASK_BACKGROUND_COLOR=3;
    public  void pickColor(View v , AppSetting setting  , int task_code ){

        new ColorPickerDialog
                .Builder(this)
                .setTitle("Pick Theme")
                .setColorShape(ColorShape.CIRCLE)
                .setDefaultColor(R.color.default_select_color)
                .setColorListener(new ColorListener() {
                    @Override
                    public void onColorSelected(int color, @NotNull String colorHex) {
                      v.setBackgroundTintList(ColorStateList.valueOf(color));
                      switch (task_code){
                          case TASK_SIGNAL_COLOR:
                              setting.setSignalColor(color);
                              break;
                          case TASK_SELECT_COLOR:
                              setting.setSelectColor(color);
                              break;
                          case TASK_BACKGROUND_COLOR:
                              setting.setBackgroundColor(color);
                              break;
                      }
                    }
                })
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();


        ImageButton signal_color=findViewById(R.id.signal_color_palette);
        ImageButton select_color=findViewById(R.id.select_color_palette);
        ImageButton background_color=findViewById(R.id.editor_background_color_palette);

        AppSetting setting=new AppSetting(this);
        signal_color.setBackgroundTintList(ColorStateList.valueOf(setting.getSignalColor()));
        select_color.setBackgroundTintList(ColorStateList.valueOf(setting.getSelectColor()));
        background_color.setBackgroundTintList(ColorStateList.valueOf(setting.getBackgroundColor()));

        signal_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickColor(signal_color , setting , TASK_SIGNAL_COLOR);
            }
        });

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickColor(select_color , setting , TASK_SELECT_COLOR);
            }

        });

        background_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickColor(background_color , setting , TASK_BACKGROUND_COLOR);
            }
        });

        Button default_button=findViewById(R.id.default_colors);

        default_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signal_color.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.default_signal_color)));
                select_color.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.default_select_color)));
                background_color.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));

                setting.setSignalColor(getColor(R.color.default_signal_color));
                setting.setSelectColor(getColor(R.color.default_select_color));
                setting.setBackgroundColor(getColor(R.color.dark_grey));
            }
        });

    }
}