package org.liuyi.run_world_school.mod;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.kyuubiran.ezxhelper.Log;

import org.liuyi.run_world_school.mod.hook.BaseHook;
import org.liuyi.run_world_school.mod.hook.base.AboutUsActivityHook;
import org.liuyi.run_world_school.mod.hook.base.SettingActivityHook;

import java.util.HashMap;
import java.util.Objects;

public class HookManager implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static HookManager INSTANCE;

    private boolean isInit;
    private final HashMap<String, BaseHook> baseHookHashMap = new HashMap<>();
    private static SharedPreferences sPref;
    private SharedPreferences.Editor editor;

    private final static String KEY = Constant.HookPrefKey.ASSIST_SUPPORT_HOOK_KEY;

    private HookManager(Context context) {
        init(context);
    }

    public static HookManager getInstance(Context context) {
        INSTANCE = INSTANCE != null ? INSTANCE : new HookManager(context);
        return INSTANCE;
    }

    private void init(Context context) {
        try {
            sPref = context.getSharedPreferences(Constant.string.ASSIST_SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
            editor = sPref.edit();
            sPref.registerOnSharedPreferenceChangeListener(this);
            SettingActivityHook.INSTANCE.init().hook();
            AboutUsActivityHook.INSTANCE.init().hook();
            isInit = true;
        } catch (Exception e) {
            Log.e("fail to init hookManager", e);
        }
    }


    /**
     * 将BaseHook的继承类添加进管理器，并恢复状态
     *
     * @param baseHooks
     */
    public void putBaseHook(BaseHook... baseHooks) {
        boolean isOpenAssist = sPref.getBoolean(KEY, false);
        for (BaseHook baseHook : baseHooks) {
            if (baseHook != null) {
                baseHookHashMap.put(baseHook.getPrefKey(), baseHook);
                if (!baseHook.isInit()) {
                    baseHook.init();
                }
                if (isOpenAssist && sPref.getBoolean(baseHook.getPrefKey(), false)) {
                    baseHook.hook();
                }
            }
        }
    }

    public void setHookState(String key, boolean state) {
        if (baseHookHashMap.containsKey(key) || key.equals(KEY)) {
            editor.putBoolean(key, state).apply();
        }
    }

    /**
     * 关闭所有Hook类的Hook
     */
    private void unHookAllBaseHook() {
        baseHookHashMap.values().forEach(BaseHook::unHook);
        Log.i("全部Hook已关闭", null);
    }

    private void restoreAllBaseHook() {
        if (!isInit) {
            return;
        }
        try {
            boolean isOpenAssist = sPref.getBoolean(KEY, false);
            baseHookHashMap.forEach((s, baseHook) -> {
                if (isOpenAssist && sPref.getBoolean(s, false)) {
                    baseHook.hook();
                } else {
                    baseHook.unHook();
                }
            });

        } catch (Exception e) {
            Log.e(e, "恢复Hook状态失败");
        }
    }

    public SharedPreferences getsPref() {
        return sPref;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY)) {
            if (sharedPreferences.getBoolean(key, false)) {
                restoreAllBaseHook();
            } else {
                unHookAllBaseHook();
            }
        } else if (baseHookHashMap.containsKey(key)) {
            BaseHook baseHook = baseHookHashMap.get(key);
            if (sharedPreferences.getBoolean(KEY, false)
                    && sharedPreferences.getBoolean(key, false)) {
                Objects.requireNonNull(baseHook).hook();
                Log.i(baseHook.getClassName() + "已开启", null);
            } else {
                Objects.requireNonNull(baseHook).unHook();
                Log.i(baseHook.getClassName() + "已关闭", null);
            }
        }
    }
}
