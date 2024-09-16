package com.logic.logicsimulator;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSetting {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    //default_colors

    private static final int DEFAULT_SIGNAL_COLOR=0xFF1A09FF;
    private static final int DEFAULT_SELECT_COLOR=0xff39ff72;
    private static final int DEFAULT_BACKGROUND_COLOR=0xFF3D3D3D;

    public AppSetting(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(APP_SETTING_DATA_NAME , Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }


    public void setSignalColor(int color){
        EditorLayout.signalColor=color;
        editor.putInt(APP_SETTING_SIGNAL_COLOR , color);
        editor.commit();
    }
    public int getSignalColor(){
       return sharedPreferences.getInt(APP_SETTING_SIGNAL_COLOR , DEFAULT_SIGNAL_COLOR);
    }

    public void setSelectColor(int color){
        EditorLayout.selectColor=color;
        editor.putInt(APP_SETTING_SELECT_COLOR , color);
        editor.commit();
    }

    public int getSelectColor(){
        return sharedPreferences.getInt(APP_SETTING_SELECT_COLOR , DEFAULT_SELECT_COLOR);
    }

    public void setBackgroundColor(int color){
        EditorLayout.backgroundColor=color;
        editor.putInt(APP_SETTING_BACKGROUND_COLOR , color);
        editor.commit();
    }

    public int getBackgroundColor(){
        return sharedPreferences.getInt(APP_SETTING_BACKGROUND_COLOR , DEFAULT_BACKGROUND_COLOR);
    }

    public boolean isAdUnderstandingShown(){
        return sharedPreferences.getBoolean(APP_AD_UNDERSTANDING_SHOWN,false);
    }

    public void setAppAdUnderstandingShown(boolean state){
        editor.putBoolean(APP_AD_UNDERSTANDING_SHOWN,state);
        editor.commit();
    }

    public static final  String APP_SETTING_DATA_NAME="app_settings";

    public static final  String APP_SETTING_SIGNAL_COLOR="editor_signal_color";
    public static final  String APP_SETTING_SELECT_COLOR="editor_select_color";
    public static final  String APP_SETTING_BACKGROUND_COLOR="editor_background_color";

    public static final String APP_AD_UNDERSTANDING_SHOWN="ad_understanding_dailog_shown";
}
