package com.android.order.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class LanguageSharedHelper {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    public static void putLanguage(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("lan", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.commit();

    }

    public static String getLanguage(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("lan", Context.MODE_PRIVATE);
        String Value = sharedPreferences.getString(Key, "en");
        return Value;

    }

}