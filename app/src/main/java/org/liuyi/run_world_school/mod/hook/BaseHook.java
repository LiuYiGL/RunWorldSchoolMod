package org.liuyi.run_world_school.mod.hook;

import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.Log;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import de.robv.android.xposed.XC_MethodHook;

public abstract class BaseHook {

    private boolean isInit;
    private boolean isHooked;
    private Method targetMethod;

    private XC_MethodHook.Unhook unhook;

    public abstract BaseHook init();

    public void hook() {
        if (!isInit()) {
            Log.i(getClassName() + " is not be init", null);
            return;
        }
        if (isHooked()) {
            return;
        }
        try {
            unhook = HookFactory.createMethodHook(targetMethod, hookFactoryConsumer());
            isHooked = true;
        } catch (Exception e) {
            Log.e(e, getClassName() + "is failed to hook");
        }
    }

    public void unHook() {
        if (isInit && isHooked && unhook != null) {
            unhook.unhook();
            setHooked(false);
            unhook = null;
        }
    }

    public abstract Consumer<HookFactory> hookFactoryConsumer();

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public boolean isHooked() {
        return isHooked;
    }

    public void setHooked(boolean hooked) {
        isHooked = hooked;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public XC_MethodHook.Unhook getUnhook() {
        return unhook;
    }

    public void setUnhook(XC_MethodHook.Unhook unhook) {
        this.unhook = unhook;
    }
}
