package com.yichuizi.hooklibrary;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者： duanyikang on 2018/12/20.
 * 描述：
 */
public class Hooker {

    public static void hookInstrumentation() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class<?> activityThread = Class.forName("android.app.ActivityThread");
        Method sCurrentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
        sCurrentActivityThread.setAccessible(true);
        //获取ActivityThread 对象
        Object activityThreadObject = sCurrentActivityThread.invoke(activityThread);

        //获取 Instrumentation 对象
        Field mInstrumentation = activityThread.getDeclaredField("mInstrumentation");
        mInstrumentation.setAccessible(true);
        Instrumentation instrumentation = (Instrumentation) mInstrumentation.get(activityThreadObject);
        MyInstrumentation customInstrumentation = new MyInstrumentation(instrumentation);
        //将我们的 customInstrumentation 设置进去
        mInstrumentation.set(activityThreadObject, customInstrumentation);
    }
}
