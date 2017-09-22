package com.example.ayselkas.myapplication.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;

import static android.R.attr.defaultValue;

/**
 * Created by artyomkuznetsov on 9/21/17.
 */

public class sharedPreferences {
    private static final String APP_PREFS = "application_preferences";
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public sharedPreferences(Context context) {
        this.context = context;
    }

    public void savePreference(String key, String value) {
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreference(String key) {
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
}
