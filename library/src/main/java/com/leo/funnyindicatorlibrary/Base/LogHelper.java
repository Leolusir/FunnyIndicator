package com.leo.funnyindicatorlibrary.Base;

import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by littleming on 15/7/29.
 */
public class LogHelper {

    private static final boolean LOG = true;

    public static void i(@Nullable String tag, @Nullable String message){
        if(LOG)
            Log.i(tag, message);
    }

    public static void e(@Nullable String tag, @Nullable String message){
        if(LOG)
            Log.e(tag, message);
    }

    public static void d(@Nullable String tag, @Nullable String message){
        if(LOG)
            Log.d(tag, message);
    }

    public static void v(@Nullable String tag, @Nullable String message){
        if(LOG)
            Log.v(tag, message);
    }
}
