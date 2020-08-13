package com.ta.sdsofa.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences preferences;
    public static final String KEY_USERID ="user_id";
    public static final String KEY_ROLE = "user_role";
    public static final String KELAS = "kelas";

    public SessionManager(Context context){
        preferences = context.getSharedPreferences("prefSessionManager",
                context.MODE_PRIVATE);
    }
    public void setUserID(String userID){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERID, userID);
        editor.apply();
    }
    public void clearEditor(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
    public boolean isLoggedIn(){
        if(preferences.getString(KEY_USERID, null) == null){
            return false;
        }
        else {
            return true;
        }
    }
    public void setUserRole(String userRole) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ROLE, userRole);
        editor.apply();
    }
    public void setKelas(String kelas){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KELAS, kelas);
        editor.apply();
    }
    public String getKelas(){
        return preferences.getString(KELAS, null);
    }
    public String getUserId(){
        return preferences.getString(KEY_USERID, null);
    }
    public String getRole(){
        return preferences.getString(KEY_ROLE, null);
    }
}
