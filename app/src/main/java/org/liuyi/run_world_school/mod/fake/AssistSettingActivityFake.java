package org.liuyi.run_world_school.mod.fake;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.misc.AndroidUtils;
import com.github.kyuubiran.ezxhelper.misc.ViewUtils;

import org.liuyi.run_world_school.mod.Constant;
import org.liuyi.run_world_school.mod.HookManager;
import org.liuyi.run_world_school.mod.dialog.RunModeDialog;
import org.liuyi.run_world_school.mod.dialog.UseGuiseDialog;
import org.liuyi.run_world_school.mod.dialog.WeChatAppreciationDialog;
import org.liuyi.run_world_school.mod.utils.ViewHelper;

import java.util.Objects;

@SuppressLint({"StaticFieldLeak", "UseSwitchCompatOrMaterialCode"})
public class AssistSettingActivityFake extends ActivityFake implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, DialogInterface.OnClickListener {

    public static ActivityFake INSTANCE = new AssistSettingActivityFake();

    private Switch swAssistSupport;
    private TextView tvUseGuise;
    private LinearLayout llRunMode;
    private TextView tvRunModeState;
    private Switch swRunValidTime;
    private Switch swLocationCheck;
    private Switch swPointCheck;
    private Switch swAdSplash;
    private Switch swAdActivity;
    private Switch swAdDialog;
    private Switch swForceCancelDialog;
    private LinearLayout llAuthorHome;
    private LinearLayout llOpenSource;
    private TextView tvWeChatAppreciation;

    private HookManager hookManager;
    private SharedPreferences sPref;

    /**
     * 负责绑定控件和监听
     */
    @Override
    protected void startActivity() {
        swAssistSupport = findViewById(ID.ASSIST_SUPPORT);
        tvUseGuise = findViewById(ID.USE_GUISE);
        llRunMode = findViewById(ID.RUN_MODE);
        tvRunModeState = findViewById(ID.RUM_MODE_STATE);
        swRunValidTime = findViewById(ID.RUN_VALID_TIME);
        swLocationCheck = findViewById(ID.LOCATION_CHECK);
        swPointCheck = findViewById(ID.POINT_CHECK);
        swAdSplash = findViewById(ID.AD_SPLASH);
        swAdActivity = findViewById(ID.AD_ACTIVITY);
        swAdDialog = findViewById(ID.AD_DIALOG);
        swForceCancelDialog = findViewById(ID.FORCE_CANCEL_DIALOG);
        llAuthorHome = findViewById(ID.CONTACT_AUTHOR);
        llOpenSource = findViewById(ID.OPEN_SOURCES);
        tvWeChatAppreciation = findViewById(ID.WECHAT_APPRECIATION);


        swAssistSupport.setChecked(sPref.getBoolean(Constant.HookPrefKey.ASSIST_SUPPORT_HOOK_KEY, false));
        tvRunModeState.setText(Constant.RunMode.getText(sPref.getInt(Constant.HookPrefKey.RUN_MODE_OPT_KEY, 0)));
        swRunValidTime.setChecked(sPref.getBoolean(Constant.HookPrefKey.RUN_VALID_TIME_HOOK_KEY, false));
        swLocationCheck.setChecked(sPref.getBoolean(Constant.HookPrefKey.LOCATION_CHECK_HOOK_KEY, false));
        swPointCheck.setChecked(sPref.getBoolean(Constant.HookPrefKey.POINT_CHECK_HOOK_KEY, false));
        swAdSplash.setChecked(sPref.getBoolean(Constant.HookPrefKey.AD_SPLASH_HOOK_KEY, false));
        swAdActivity.setChecked(sPref.getBoolean(Constant.HookPrefKey.AD_ACTIVITY_HOOK_KEY, false));
        swAdDialog.setChecked(sPref.getBoolean(Constant.HookPrefKey.AD_DIALOG_HOOK_KEY, false));
        swForceCancelDialog.setChecked(sPref.getBoolean(Constant.HookPrefKey.FORCE_CANCEL_DIALOG_HOOK_KEY, false));

        // 设置监听
        swAssistSupport.setOnCheckedChangeListener(this);
        tvUseGuise.setOnClickListener(this);
        llRunMode.setOnClickListener(this);
        swRunValidTime.setOnCheckedChangeListener(this);
        swLocationCheck.setOnCheckedChangeListener(this);
        swPointCheck.setOnCheckedChangeListener(this);
        swAdSplash.setOnCheckedChangeListener(this);
        swAdActivity.setOnCheckedChangeListener(this);
        swAdDialog.setOnCheckedChangeListener(this);
        swForceCancelDialog.setOnCheckedChangeListener(this);
        llAuthorHome.setOnClickListener(this);
        llOpenSource.setOnClickListener(this);
        tvWeChatAppreciation.setOnClickListener(this);
    }

    /**
     * 负责对关键变量进行赋值，并执行startActivity
     */
    @Override
    protected void initActivity() {
        try {
            if (!isLayoutInit()) {
                initLayout();
            }
            hookManager = HookManager.getInstance(getActivity());
            sPref = hookManager.getsPref();
            setInit(true);
            startActivity();
        } catch (Exception e) {
            Log.e(e, "");
        }
    }

    /**
     * 负责分配id并放入Content
     */
    @Override
    protected void initLayout() {
        try {
            ViewHelper viewHelper = ViewHelper.newInstance(getActivity());
            LinearLayout layoutContent = getLayoutContent();
            ViewHelper.CategoryLinearLayoutBuilder builder = new ViewHelper.CategoryLinearLayoutBuilder(viewHelper);
            layoutContent.addView(builder
                    .addSwitchItem(ID.ASSIST_SUPPORT.getId(), ID.ASSIST_SUPPORT.getText())
                    .addArrowItem(ID.USE_GUISE.getId(), ID.USE_GUISE.getText())
                    .build());

            layoutContent.addView(builder
                    .addArrowAndStateItem(ID.RUN_MODE.getId(), ID.RUN_MODE.getText(), ID.RUM_MODE_STATE.getId(), ID.RUM_MODE_STATE.getText())
                    .addSwitchItem(ID.RUN_VALID_TIME.getId(), ID.RUN_VALID_TIME.getText())
                    .build());

            layoutContent.addView(builder
                    .addSwitchItem(ID.LOCATION_CHECK.getId(), ID.LOCATION_CHECK.getText())
                    .addSwitchItem(ID.POINT_CHECK.getId(), ID.POINT_CHECK.getText())
                    .build());

            layoutContent.addView(builder
                    .addSwitchItem(ID.AD_SPLASH.getId(), ID.AD_SPLASH.getText())
                    .addSwitchItem(ID.AD_ACTIVITY.getId(), ID.AD_ACTIVITY.getText())
                    .addSwitchItem(ID.AD_DIALOG.getId(), ID.AD_DIALOG.getText())
                    .build());

            layoutContent.addView(builder
                    .addSwitchItem(ID.FORCE_CANCEL_DIALOG.getId(), ID.FORCE_CANCEL_DIALOG.getText())
                    .build());

            layoutContent.addView(builder
                    .addArrowAndStateItem(ID.CONTACT_AUTHOR.getId(), ID.CONTACT_AUTHOR.getText(),
                            ID.COOLAPK.getId(), ID.COOLAPK.getText())
                    .addArrowAndStateItem(ID.OPEN_SOURCES.getId(), ID.OPEN_SOURCES.getText(),
                            ID.GITHUB.getId(), ID.GITHUB.getText())
                    .addArrowItem(ID.WECHAT_APPRECIATION.getId(), ID.WECHAT_APPRECIATION.getText())
                    .build());

            setLayoutInit(true);
        } catch (Exception e) {
            Log.e(e, "fail to init layout");
        }

    }

    @Override
    protected String getTitle() {
        return "运动辅助";
    }

    @Override
    public void restoreState() {
        try {
            Objects.requireNonNull(ViewUtils.INSTANCE.findViewByIdName(getActivity(), "iv_aboutUsLogo")).setVisibility(View.GONE);
            TextView tv_version = (TextView) ViewUtils.INSTANCE.findViewByIdName(getActivity(), "tv_version");
            assert tv_version != null;
            tv_version.setText(Constant.string.version_text);
        } catch (Exception e) {
            Log.e(e, "");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        String key = ID.getKey(id);
        if (key != null && !key.isEmpty()) {
            AndroidUtils.showToast(getActivity(), key + ": " + isChecked, Toast.LENGTH_SHORT);
            hookManager.setSPrefState(key, isChecked);
            if (isChecked && key.equals(Constant.HookPrefKey.ASSIST_SUPPORT_HOOK_KEY)) {
                new UseGuiseDialog(getActivity());
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == ID.USE_GUISE.getId()) {
            new UseGuiseDialog(getActivity());
        } else if (id == ID.RUN_MODE.getId()) {
            RunModeDialog runModeDialog = new RunModeDialog(getActivity());
            runModeDialog.setSingleChoiceItems(sPref.getInt(Constant.HookPrefKey.RUN_MODE_OPT_KEY, 0), this);
            runModeDialog.show();
        } else if (id == ID.CONTACT_AUTHOR.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.string.coolapk_url))
                    .setPackage("com.coolapk.market");
            getActivity().startActivity(intent);
        } else if (id == ID.OPEN_SOURCES.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.string.github_url));
            getActivity().startActivity(intent);
        } else if (id == ID.WECHAT_APPRECIATION.getId()) {
            new WeChatAppreciationDialog(getActivity()).show();
        }
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onClick(DialogInterface dialog, int which) {
        tvRunModeState.setText(Constant.RunMode.getText(which));
        sPref.edit().putInt(Constant.HookPrefKey.RUN_MODE_OPT_KEY, which)
                .putBoolean(Constant.HookPrefKey.RUN_MODE_HOOK_KEY, !(which == 0)).apply();
        dialog.cancel();
    }

    enum ID {
        PLACEHOLDER("占位", "placeholder"),
        ASSIST_SUPPORT("辅助总开关", Constant.HookPrefKey.ASSIST_SUPPORT_HOOK_KEY),
        USE_GUISE(Constant.string.useGuideTitle, ""),
        RUN_MODE(Constant.string.runModeTitle, Constant.HookPrefKey.RUN_MODE_HOOK_KEY),
        RUM_MODE_STATE("默认", Constant.HookPrefKey.RUN_MODE_OPT_KEY),
        RUN_VALID_TIME("取消夜间跑步限制", Constant.HookPrefKey.RUN_VALID_TIME_HOOK_KEY),
        LOCATION_CHECK("关闭定位信息检测", Constant.HookPrefKey.LOCATION_CHECK_HOOK_KEY),
        POINT_CHECK("关闭点位信息检测", Constant.HookPrefKey.POINT_CHECK_HOOK_KEY),
        AD_SPLASH("取消开屏广告", Constant.HookPrefKey.AD_SPLASH_HOOK_KEY),
        AD_ACTIVITY("取消广告Activity", Constant.HookPrefKey.AD_ACTIVITY_HOOK_KEY),
        AD_DIALOG("取消弹窗广告", Constant.HookPrefKey.AD_DIALOG_HOOK_KEY),
        FORCE_CANCEL_DIALOG("支持返回取消弹窗", Constant.HookPrefKey.FORCE_CANCEL_DIALOG_HOOK_KEY),
        CONTACT_AUTHOR("作者主页", ""),
        OPEN_SOURCES("开源仓库", ""),
        COOLAPK("酷安", ""),
        GITHUB("GitHub", ""),
        WECHAT_APPRECIATION(Constant.string.weChatAppreciationTitle, "");

        private final String text;
        private final String key;

        ID(String text, String key) {
            this.text = text;
            this.key = key;
        }

        public static String getKey(int id) {
            for (ID value : ID.values()) {
                if (value.getId() == id) {
                    return value.key;
                }
            }
            return null;
        }

        public String getText() {
            return text;
        }

        public int getId() {
            return ordinal();
        }

    }

    public <T extends View> T findViewById(ID id) {
        return super.findViewById(id.getId());
    }
}
