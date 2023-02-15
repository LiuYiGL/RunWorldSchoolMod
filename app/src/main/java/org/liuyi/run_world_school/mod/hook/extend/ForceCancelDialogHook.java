package org.liuyi.run_world_school.mod.hook.extend;

import android.app.Dialog;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.hook.BaseHook;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import de.robv.android.xposed.XC_MethodHook;

public class ForceCancelDialogHook extends BaseHook {


    public final static BaseHook INSTANCE = new ForceCancelDialogHook();

    private final ArrayList<XC_MethodHook.Unhook> unhookArrayList = new ArrayList<>();
    private final ArrayList<Method> methodArrayList = new ArrayList<>();

    @Override
    public BaseHook init() {
        super.init();
        try {
            methodArrayList.addAll(MethodFinder.fromClass(Dialog.class).filter(
                    method -> Arrays.asList("setCancelable", "setCanceledOnTouchOutside")
                            .contains(method.getName())).toList());

        } catch (Exception e) {
            setInit(false);
        }
        return this;
    }

    @Override
    public void hook() {
        if (!isInit()) {
            Log.i(getClassName() + " is not be init", null);
            return;
        }
        if (isHooked()) {
            return;
        }
        try {
            unhookArrayList.addAll(HookFactory.createMethodHooks(methodArrayList, hookFactoryConsumer()));
            setHooked(true);
        } catch (Exception e) {
            Log.e(e, getClassName() + "is failed to hook");
        }
    }

    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.before(methodHookParam -> methodHookParam.args[0] = true);
        };
    }

    @Override
    public void unHook() {
        if (isInit() && isHooked() && !unhookArrayList.isEmpty()) {
            unhookArrayList.forEach(XC_MethodHook.Unhook::unhook);
            setHooked(false);
            unhookArrayList.clear();
        }
    }

    @Override
    public String getPrefKey() {
        return Constant.HookPrefKey.FORCE_CANCEL_DIALOG_HOOK_KEY;
    }

    @Override
    public String getTargetClazzName() {
        return "android.app.Dialog";
    }

    @Override
    public Method getTargetMethod(MethodFinder methodFinder) {
        return null;
    }
}
