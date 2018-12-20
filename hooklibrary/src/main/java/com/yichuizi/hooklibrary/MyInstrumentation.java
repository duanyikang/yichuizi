package com.yichuizi.hooklibrary;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

/**
 * 作者： duanyikang on 2018/12/20.
 * 描述：
 */
public class MyInstrumentation extends Instrumentation {
    private Instrumentation base;

    public MyInstrumentation(Instrumentation base) {
        this.base = base;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        System.out.println("我要的66666");
       // return super.newActivity(cl, FakeActivity.class.getName(), intent);
        return super.newActivity(cl, className, intent);
    }
}
