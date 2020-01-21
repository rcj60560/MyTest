package com.demo.updatelib.app;

import android.app.Activity;
import android.content.Context;

import com.demo.updatelib.update.UpdateService;


public final class UpdateHelper {

    public static final String UPDATE_FILE_KEY = "apk";

    public static String UPDATE_ACTION;
    //SDK全局Context, 供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        UPDATE_ACTION = mContext.getPackageName() + ".INSTALL";
    }

    public static Context getContext() {
        return mContext;
    }

    //外部检查更新方法
    public static void checkUpdate(final Activity activity) {
        //1去服务器请求版本对比
        //2然后开启服务后代下载文件更新

        UpdateService.startService(mContext);
    }
}
