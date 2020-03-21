package com.ravisharma.mydiary.Prefrence;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    private final String PREF_NAME = "DiaryPref";
    private final String KEY_NAME = "name";
    private final String KEY_LOG = "loggedIn";

    public PrefManager(Context c) {
        sp = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        ed = sp.edit();
    }

    public void saveUserName(String name) {
        ed.putString(KEY_NAME, name);
        ed.commit();
    }

    public String getUserName() {
        return sp.getString(KEY_NAME, "");
    }

    public void setLoggedIn(boolean b) {
        ed.putBoolean(KEY_LOG, b);
        ed.commit();
    }

    public boolean isLoggedIn()
    {
        return sp.getBoolean(KEY_LOG, false);
    }
}
