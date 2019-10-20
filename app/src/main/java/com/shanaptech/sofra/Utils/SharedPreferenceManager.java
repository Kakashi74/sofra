package com.shanaptech.sofra.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class SharedPreferenceManager {

    private static SharedPreferences sharedPreferences = null;

    public static String USER_API_TOKEN = "USER_API_TOKEN";
    public static String USER = "";
    public static String RESTURANT = "";


    public static void setSharedPreferences(AppCompatActivity activity) {

        if (sharedPreferences == null) {

            sharedPreferences = activity.getSharedPreferences(

                    "data", activity.MODE_PRIVATE);



        }

    }
    public static boolean LoadBooleanClient(Activity activity, String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences((AppCompatActivity) activity);
        }

        return sharedPreferences.getBoolean(data_Key, false);
    }

    public static void SaveData(AppCompatActivity activity, String data_Key, String data_Value) {

        if (sharedPreferences != null) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(data_Key, data_Value);

            editor.commit();

        } else {

            setSharedPreferences(activity);

        }

    }

    public static void SaveData(AppCompatActivity activity, String data_Key, boolean data_Value) {

        if (sharedPreferences != null) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(data_Key, data_Value);

            editor.commit();

        } else {

            setSharedPreferences(activity);

        }

    }


    public static String LoadData(AppCompatActivity activity, String data_Key) {

        if (sharedPreferences != null) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

        } else {

            setSharedPreferences(activity);

        }



        return sharedPreferences.getString(data_Key, null);

    }


    public static boolean LoadBoolean(AppCompatActivity activity, String data_Key) {

        if (sharedPreferences != null) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

        } else {

            setSharedPreferences(activity);

        }



        return sharedPreferences.getBoolean(data_Key, false);

    }



    public static void clean(AppCompatActivity activity) {

        setSharedPreferences(activity);

        if (sharedPreferences != null) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();

            editor.commit();

        }

    }


}
