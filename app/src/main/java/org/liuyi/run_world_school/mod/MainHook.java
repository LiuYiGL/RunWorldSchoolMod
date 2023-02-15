package org.liuyi.run_world_school.mod;

import android.app.Application;
import android.content.Context;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;

import org.liuyi.run_world_school.mod.hook.extend.AdActivityHook;
import org.liuyi.run_world_school.mod.hook.extend.AdDialogHook;
import org.liuyi.run_world_school.mod.hook.extend.AdSplashHook;
import org.liuyi.run_world_school.mod.hook.extend.ForceCancelDialogHook;
import org.liuyi.run_world_school.mod.hook.extend.LocationCheckHook;
import org.liuyi.run_world_school.mod.hook.extend.PointCheckHook;
import org.liuyi.run_world_school.mod.hook.extend.RunModeHook;
import org.liuyi.run_world_school.mod.hook.extend.RunValidTimeHook;

import java.lang.reflect.Method;
import java.util.Objects;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    private static final String PKG_NAME = "com.zjwh.android_wh_physicalfitness";
    private static final String TAG = "FuckRun";


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (Objects.equals(lpparam.packageName, PKG_NAME)) {
            try {
                EzXHelper.initHandleLoadPackage(lpparam);
                EzXHelper.setLogTag(TAG);
                EzXHelper.setToastTag(TAG);
                Log.i("进入运动世界校园", null);
                Method attach = MethodFinder.fromClass(Application.class).filterByName("attach").first();
                HookFactory.createMethodHook(attach, hookFactory -> {
                    hookFactory.after(methodHookParam -> {
                        if (methodHookParam.args[0] instanceof Context) {
                            EzXHelper.setAppContext((Context) methodHookParam.args[0]);
                            Log.d("Success to initAppContext", null);

                            HookManager hookManager = HookManager.getInstance((Context) methodHookParam.args[0]);
                            hookManager.putBaseHook(
                                    RunValidTimeHook.INSTANCE,
                                    LocationCheckHook.INSTANCE,
                                    PointCheckHook.INSTANCE,
                                    AdSplashHook.INSTANCE,
                                    AdActivityHook.INSTANCE,
                                    AdDialogHook.INSTANCE,
                                    ForceCancelDialogHook.INSTANCE,
                                    RunModeHook.INSTANCE);
                        }
                    });
                });
            } catch (Exception e) {
                Log.e(e, "Use mod is failed");
            }
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        EzXHelper.initZygote(startupParam);
    }
}
