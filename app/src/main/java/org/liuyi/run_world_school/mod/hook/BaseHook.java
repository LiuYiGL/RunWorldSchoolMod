package org.liuyi.run_world_school.mod.hook;

import com.github.kyuubiran.ezxhelper.ClassUtils;
import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import de.robv.android.xposed.XC_MethodHook;

public abstract class BaseHook {

    private boolean isInit;
    private boolean isHooked;
    private MethodFinder methodFinder;

    private XC_MethodHook.Unhook unhook;

    public BaseHook init() {
        if (!isInit()) {
            try {
                Class<?> aClass = ClassUtils.loadFirstClass(getTargetClazzName());
                methodFinder = MethodFinder.fromClass(aClass);
                setInit(true);
            } catch (Exception e) {
                Log.e(getClassName() + "初始化失败", e);
            }
        }
        return this;
    }

    public void hook() {
        if (!isInit()) {
            Log.i(getClassName() + " is not be init", null);
            return;
        }
        if (isHooked()) {
            return;
        }
        try {
            unhook = HookFactory.createMethodHook(getTargetMethod(methodFinder), hookFactoryConsumer());
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

    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
        };
    }

    public abstract String getPrefKey();

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    public abstract String getTargetClazzName();

    public abstract Method getTargetMethod(MethodFinder methodFinder);

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

    public XC_MethodHook.Unhook getUnhook() {
        return unhook;
    }

    public void setUnhook(XC_MethodHook.Unhook unhook) {
        this.unhook = unhook;
    }

    public void LogD(String... strings) {
        Log.d(getClassName() + buildLogString(strings), null);
    }

    public void LogI(String... strings) {
        Log.i(getClassName() + buildLogString(strings), null);
    }


    private StringBuilder buildLogString(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            if (str != null) {
                builder.append(": ").append(str);
            }
        }
        return builder;
    }
}
