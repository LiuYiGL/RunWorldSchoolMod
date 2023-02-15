package org.liuyi.run_world_school.mod.hook.extend;

import android.app.Activity;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class AdActivityHook extends BaseHook {

    public static final BaseHook INSTANCE = new AdActivityHook();

    private static final String targetClazzName = "com.zjwh.android_wh_physicalfitness.ui.AdActivity";
    private static final String targetMethodName = "onCreate";


    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.after(methodHookParam -> {
                if (methodHookParam.thisObject instanceof Activity) {
                    Activity activity = (Activity) methodHookParam.thisObject;
                    activity.finish();
                    LogI(activity.getClass().getSimpleName() + "已关闭");
                }
            });
        };
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.AD_ACTIVITY_HOOK_KEY;
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
