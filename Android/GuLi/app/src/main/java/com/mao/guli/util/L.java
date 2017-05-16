package com.mao.guli.util;

import android.util.Log;

/**
 * Created by mao on 2017/2/24.用于调试，debug作为一个调试使用的开关
 */
public class L {
    private static final String Tag="maomao OkHttp";
    private static boolean debug=true;
    public static void e(String msg){
        if(debug){
            Log.e(Tag,msg);
        }
    }
}