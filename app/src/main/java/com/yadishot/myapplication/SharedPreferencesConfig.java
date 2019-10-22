package com.yadishot.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesConfig {

    SharedPreferences sharedPreferences;
    Context context;

    public SharedPreferencesConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_sharedpreferencesApiCode), Context.MODE_PRIVATE);
    }

    public void writeUserLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_status), status);
        editor.commit();
    }
    public void writeUsername(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_username), name);
        editor.commit();
    }
    public void writeUserFullname(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_fullname), name);
        editor.commit();
    }
    public void writeUserPassword(String password){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_password), password);
        editor.commit();
    }

    public void writeUserId(String userId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_id), userId);
        editor.commit();
    }
    public void writeUserPersonWork(String workAtPerson){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_personWork), workAtPerson);
        editor.commit();
    }

    public boolean readUserLoginStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_status), false);
        return status;
    }
    public String readUsername(){
        String status = "";
        status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_username), "ناشناس");
        return status;
    }
    public String readPersonWork(){
        String status = "";
        status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_personWork), "ناشناس");
        return status;
    }
    public String readUserFullname(){
        String status = "";
        status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_fullname), "کاربر ناشناس");
        return status;
    }
    public String readUserPassword(){
        String status = "";
        status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_password), "ندارد");
        return status;
    }
    public String readUserId(){
        String status = "";
        status = sharedPreferences.getString(context.getResources().getString(R.string.app_sharedpreferencesApiCode_user_id), "0");
        return status;
    }


}
