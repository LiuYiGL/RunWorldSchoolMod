package org.liuyi.run_world_school.mod.hook.extend;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class LocationCheckHook extends BaseHook {

    public static final BaseHook INSTANCE = new LocationCheckHook();

    private static final String targetClazzName = "com.zjwh.sw.sport.SportJniUtils";
    private static final String targetMethodName = "isValidPoint";


    /**
     * 第六个参数的值是由一个判断两个点经纬度是否相同的函数返回
     * 如果相同返回true，故系统会怀疑GPS信号较弱，
     * 所以需要将该参数设置为false，让系统认为我们一直在走动
     *
     * @return
     */
    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.before(methodHookParam -> methodHookParam.args[5] = false);
        };
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.LOCATION_CHECK_HOOK_KEY;
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
