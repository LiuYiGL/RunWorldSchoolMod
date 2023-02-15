package org.liuyi.run_world_school.mod.hook.extend;

import android.content.SharedPreferences;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.HookManager;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class RunModeHook extends BaseHook {

    public static BaseHook INSTANCE = new RunModeHook();

    private static final String targetClazzName = "com.zjwh.android_wh_physicalfitness.entity.RandomConfigBean";
    private static final String targetMethodName = "getPolicy";
    private HookManager hookManager;
    private SharedPreferences sPref;

    @Override
    public BaseHook init() {
        super.init();
        try {
            hookManager = HookManager.getInstance(EzXHelper.getAppContext());
            sPref = hookManager.getsPref();
            setInit(true);
        } catch (Exception e) {
            Log.e(getClassName() + "初始化失败", e);
            setInit(false);
        }
        return this;
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.RUN_MODE_HOOK_KEY;
    }

    /**
     * 设置结果为0会是无序点
     * 1有序
     */
    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.after(methodHookParam -> {
                try {
                    int i = sPref.getInt(Constant.HookPrefKey.RUN_MODE_OPT_KEY, 0);
                    if (i == Constant.RunMode.ORDER.getValue()) {
                        methodHookParam.setResult(1);
                    } else if (i == Constant.RunMode.DISORDER.getValue()) {
                        methodHookParam.setResult(0);
                    }
                } catch (Exception e) {
                    Log.e(e, getClassName());
                }
            });
        };
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
