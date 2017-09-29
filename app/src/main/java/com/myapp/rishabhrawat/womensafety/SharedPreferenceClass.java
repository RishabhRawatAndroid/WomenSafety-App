package com.myapp.rishabhrawat.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rishabh Rawat on 7/1/2017.
 */

public class SharedPreferenceClass {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public SharedPreferenceClass() {
    }

    public SharedPreferenceClass( Context context) {
        preferences =context.getSharedPreferences("saveddata", 0);
        editor=preferences.edit();
        this.context = context;
    }

    public void LoginPreferenceSavedData(final Context context, String name, String email, String phone, String address, String randomstr) {
        //Make a Shared Preference for Storing Data
        preferences = context.getSharedPreferences("saveddata", 0);
        editor = preferences.edit();
        this.context=context;

        editor.putString("NAME", name);
        editor.putString("EMAIL", email);
        editor.putString("PHONE", phone);
        editor.putString("ADDRESS", address);
        editor.putString("RANDOMSTRING", randomstr);
        editor.commit();

    }


    //ALL ARE GETTER
    public String getName() {
        return preferences.getString("NAME", null);
    }

    public String getEmail() {
        return preferences.getString("EMAIL", null);
    }

    public String getPhone() {
        return preferences.getString("PHONE", null);
    }

    public String getAddress() {
        return preferences.getString("ADDRESS", null);
    }

    public String getRandomstring() {
        return preferences.getString("RANDOMSTRING", null);
    }

    public Boolean getFlag() {
        //SharedPreferences preferences =context.getSharedPreferences("saveddata", 0);
        return preferences.getBoolean("FLAG", false);
    }

    public Boolean getupdate()
    {
        return preferences.getBoolean("UPDATE",false);
    }

    public String locationid()
    {
        return preferences.getString("LOCATIONID",null);
    }

    public String getuserpresentlocation(){return preferences.getString("USERNAME",null);}


//ALL ARE SETTER
    public void setName(String name) {
        editor.putString("NAME", name);
        editor.commit();
    }

    public void setEmail(String mail) {
        editor.putString("EMAIL", mail);
        editor.commit();
    }

    public void setPhone(String phone) {
        editor.putString("PHONE", phone);
        editor.commit();
    }

    public void setAddress(String address) {
        editor.putString("ADDRESS", address);
        editor.commit();
    }

    public void setFlag(boolean flag) {
        editor.putBoolean("FLAG", flag);
        editor.commit();
    }

    public void setupdate(boolean flag)
    {
        editor.putBoolean("UPDATE",flag);
        editor.commit();
    }

    public void setlocationid(String id)
    {
        editor.putString("LOCATIONID",id);
        editor.commit();
    }

    public void setuserpresentlocation(String name) {
        editor.putString("USERNAME",name);
        editor.commit();
    }
}



