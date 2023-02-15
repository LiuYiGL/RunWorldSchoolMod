package org.liuyi.run_world_school.mod.hook.extend;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

public class RunValidTimeHook extends BaseHook {

    public final static BaseHook INSTANCE = new RunValidTimeHook();

    private final static String targetClazzName = "fu2";
    private final static String targetMethodName = "OooO00o";

    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.before(methodHookParam -> {
                LogD("修改前参数", Arrays.toString(methodHookParam.args));
                methodHookParam.args[1] = 4;
            });
        };
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.RUN_VALID_TIME_HOOK_KEY;
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
