package org.liuyi.run_world_school.mod.hook.extend;

import com.github.kyuubiran.ezxhelper.HookFactory;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.util.function.Consumer;

public class PointCheckHook extends LocationCheckHook {

    public static final BaseHook INSTANCE = new PointCheckHook();

    /**
     * 和父类hook方法一样，只不过是让目标函数永远返回true，从而让系统判断我们的点位一直合法
     *
     * @return
     */
    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.after(methodHookParam -> methodHookParam.setResult(true));
        };
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.POINT_CHECK_HOOK_KEY;
    }
}
