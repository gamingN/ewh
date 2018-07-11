package com.example.veeotech.easywarehouse;

import android.app.Application;

/**
 * Created by VeeoTech on 2018/5/18.
 */

public class MyApplication extends Application {
    private static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
    /**
     * 通过此方法可以获得Appliaction的context
     * @return
     */
    public static Application getApplication(){
        return mApplication;
    }
}
