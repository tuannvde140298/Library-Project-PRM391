package com.example.library_project.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.library_project.data.constants.Common;
import com.example.library_project.data.models.Category;
import com.example.library_project.utils.ObjectSerializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MySharedPreferences {
    private Context mcontext;

    public MySharedPreferences(Context context) {
        this.mcontext = context;
    }

    public void putBooleanValue(String key, boolean value) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void putIntValue(String key, int value){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public Integer getIntValue(String key){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public void putTokenValue(String token) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Common.AUTHENTICATION_KEY, token);
        editor.apply();
    }

    public String getTokenValue() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Common.AUTHENTICATION_KEY, "");
    }

    public void putCategories(List<Category> categories) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
            editor.putString(Common.CATEGORIES_KEY, ObjectSerializer.serialize((Serializable) categories));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public List<Category> getCategories() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        List<Category> categories = new ArrayList<>();

        try {
            categories = (List<Category>) ObjectSerializer.deserialize(sharedPreferences.getString(Common.CATEGORIES_KEY, ""));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("Categories", categories.toString());
        return categories;
    }

    public void clearPreferences() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(Common.AUTHENTICATION_KEY).commit();
        sharedPreferences.edit().remove(Common.CATEGORIES_KEY).commit();
        sharedPreferences.edit().remove(Common.USER_ID).commit();

        //        sharedPreferences.edit().clear().commit();
    }

    public void putRecentSearchKey(String searchKey) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        List<String> keyList = getRecentSearchKey();
        if (keyList.size() == 3) {
            keyList.remove(0);
        }
        keyList.add(searchKey);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString(Common.RECENT_SEARCH_KEY, ObjectSerializer.serialize((Serializable) keyList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public List<String> getRecentSearchKey() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        List<String> keyList = new ArrayList<>();

        try {
            keyList = (List<String>) ObjectSerializer.deserialize(sharedPreferences.getString(Common.RECENT_SEARCH_KEY, ""));
            if (keyList == null){
                keyList = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return keyList;
    }

    public void removeRecentKeys() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Common.MY_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<String> keyList = new ArrayList<>();
        try {
            editor.putString(Common.RECENT_SEARCH_KEY, ObjectSerializer.serialize((Serializable) keyList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }
}
