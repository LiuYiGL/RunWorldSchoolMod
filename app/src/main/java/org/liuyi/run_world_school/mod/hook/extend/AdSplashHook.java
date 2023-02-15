package org.liuyi.run_world_school.mod.hook.extend;

import android.app.Activity;
import android.content.Intent;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class AdSplashHook extends BaseHook {

    public static final BaseHook INSTANCE = new AdSplashHook();

    private static final String targetClazzName = "com.zjwh.android_wh_physicalfitness.activity.SplashActivity";
    private static final String targetMethodName = "onCreate";
    private static final String mainActivityClazzName = "com.zjwh.android_wh_physicalfitness.activity.MainActivity";

    /**
     * 在SplashActivity开始时便打开主要活动，并关闭该SplashActivity防止加载广告
     *
     * @return
     */
    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.after(methodHookParam -> {
                Log.i(methodHookParam.thisObject.toString(), null);
                if (methodHookParam.thisObject instanceof Activity) {
                    Activity activity = (Activity) methodHookParam.thisObject;
                    Intent intent = new Intent();
                    intent.setClassName(EzXHelper.hostPackageName, mainActivityClazzName);
                    activity.startActivity(intent);
                    activity.finish();
                    LogI(activity.getClass().getSimpleName() + "已关闭");
                }
            });
        };
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.AD_SPLASH_HOOK_KEY;
    }

    @Override
    public String getTargetClazzName() {
        return targetClazzName;
    }

    @Override
    public Method getTargetMethod(MethodFinder methodFinder) {
        return methodFinder.filterByName(targetMethodName).first();
    }
}
