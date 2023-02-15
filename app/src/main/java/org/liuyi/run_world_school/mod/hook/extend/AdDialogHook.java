package org.liuyi.run_world_school.mod.hook.extend;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class AdDialogHook extends BaseHook {

    public static final BaseHook INSTANCE = new AdDialogHook();

    private static final String targetClazzName = "com.fighter.loader.view.InteractTemplateAdDialog";
    private static final String methodName0 = "show";
    private static final String methodName1 = "reallyShow";
    private Method method0;
    private Method method1;


    /**
     * 替换show方法
     * @return
     */
    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.replace(methodHookParam -> null);
        };
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.AD_DIALOG_HOOK_KEY;
    }

    @Override
    public String getTargetClazzName() {
        return targetClazzName;
    }

    @Override
    public Method getTargetMethod(MethodFinder methodFinder) {
        return methodFinder.filterByName(methodName0).first();
    }
}
