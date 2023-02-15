package org.liuyi.run_world_school.mod.hook.base;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.HookFactory;
import com.github.kyuubiran.ezxhelper.finders.MethodFinder;
import com.github.kyuubiran.ezxhelper.misc.ViewUtils;

import org.liuyi.run_world_school.mod.hook.BaseHook;
import org.liuyi.run_world_school.mod.utils.ViewHelper;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;

public class SettingActivityHook extends BaseHook {

    public static final BaseHook INSTANCE = new SettingActivityHook();
    private static final String targetClazzName = "com.zjwh.android_wh_physicalfitness.activity.mine.SettingActivity";

    @Override
    public Consumer<HookFactory> hookFactoryConsumer() {
        return hookFactory -> {
            hookFactory.after(methodHookParam -> {
                if (methodHookParam.thisObject instanceof Activity) {
                    Activity activity = (Activity) methodHookParam.thisObject;

                    // 获取activity的布局
                    LinearLayout linearLayout = (LinearLayout) Objects.requireNonNull(ViewUtils.INSTANCE.findViewByIdName(activity, ViewHelper.arrowItemIdName)).getParent();

                    // 新建一个item
                    ViewHelper viewHelper = ViewHelper.newInstance(activity);
                    TextView tvRunAssist = viewHelper.getArrowItem(0, "运动辅助");
                    tvRunAssist.setOnClickListener(v -> {
                        Intent intent = new Intent();
                        intent.setClassName(EzXHelper.getAppContext().getPackageName(),
                                "com.zjwh.android_wh_physicalfitness.activity.mine.AboutUsActivity");
                        intent.putExtra("custom", "AssistSetting");
                        activity.startActivity(intent);
                    });

                    // 添加进布局
                    linearLayout.addView(tvRunAssist, 0);
                    linearLayout.addView(viewHelper.getLineItem(), 1);
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
