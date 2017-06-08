package xiazdong.me.fragmentdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import xiazdong.me.fragmentdemo.config.GlobalContext;

/**
 * Created by xiazdong on 17/6/5.
 */

public final class PrefUtils {
    public static final String PREFS_KEY_SELECTED_MATERIAL = "selected_material";
    public static final String PREFS_KEY_SELECTED_PAGE = "selected_page";
    public static final String PREFS_KEY_SELECTED_TAB = "selected_tab";
    public static final String PREFS_KEY_IS_FIRST_LAUNCH = "is_first_launch";

    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp = GlobalContext.getContext().getSharedPreferences("test",
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }


    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = GlobalContext.getContext().getSharedPreferences("test",
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }


    public static void putString(String key, String value) {
        SharedPreferences sp = GlobalContext.getContext().getSharedPreferences("test",
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }


    public static String getString(String key, String defValue) {
        SharedPreferences sp = GlobalContext.getContext().getSharedPreferences("test",
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }


    public static void putInt(String key, int value) {
        SharedPreferences sp = GlobalContext.getContext().getSharedPreferences("test",
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }


    public static int getInt(String key, int defValue) {
        SharedPreferences sp = GlobalContext.getContext().getSharedPreferences("test",
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }


    public static void remove(String key) {
        SharedPreferences sp = GlobalContext.getContext().getSharedPreferences("test",
                Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
}
