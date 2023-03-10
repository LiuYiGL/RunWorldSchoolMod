package org.liuyi.run_world_school.mod.hook.base;

import android.app.Activity;
import android.content.Intent;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.fake.AssistSettingActivityFake;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;

public class AboutUsActivityHook extends BaseHook {

    public static BaseHook INSTANCE = new AboutUsActivityHook();
    private static final String targetClazzName = "com.zjwh.android_wh_physicalfitness.activity.mine.AboutUsActivity";


    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.after(methodHookParam -> {
                if (methodHookParam.thisObject instanceof Activity) {
                    Activity activity = (Activity) methodHookParam.thisObject;
                    Intent intent = activity.getIntent();
                    String custom = intent.getStringExtra("custom");
                    if (Objects.equals(custom, "AssistSetting")) {
                        AssistSettingActivityFake.INSTANCE.fakeActivity(activity);
                    }
                }
            });
        };
    }

    @Override
    public String getPrefKey() {
        return null;
    }

    @Override
    public String getTargetClazzName() {
        return targetClazzName;
    }

    @Override
    public Method getTargetMethod(MethodFinder methodFinder) {
        return methodFinder.filterByName("onCreate").first();
    }
}
