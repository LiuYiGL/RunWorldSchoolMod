package org.liuyi.run_world_school.mod;

public class Constant {

    public static class string {
        public static final String ASSIST_SETTINGS_PREF_NAME = "run_assist_pref";
        public static final String coolapk_url = "coolmarket://u/1735098";
        public static final String github_url = "https://github.com/LiuYiGL/RunWorldSchoolMod";
        public static final String version_text = "作者：LiuYi_GL\n版本：23.02.11-5.1.3";
        public static final String useGuideTitle = "使用须知";
        public static final String runModeTitle = "跑步模式";
        public static final String weChatAppreciationTitle = "微信赞赏";
    }

    public static class HookPrefKey {
        public static final String AD_ACTIVITY_HOOK_KEY = "ad_activity_hook";
        public static final String AD_DIALOG_HOOK_KEY = "ad_dialog_hook";
        public static final String AD_SPLASH_HOOK_KEY = "ad_splash_hook";
        public static final String ASSIST_SUPPORT_HOOK_KEY = "assist_support";
        public static final String FORCE_CANCEL_DIALOG_HOOK_KEY = "force_cancel_dialog";
        public static final String LOCATION_CHECK_HOOK_KEY = "location_check_hook";
        public static final String POINT_CHECK_HOOK_KEY = "point_check_hook";
        public static final String RUN_MODE_HOOK_KEY = "run_mode_support";
        public static final String RUN_MODE_OPT_KEY = "run_mode";
        public static final String RUN_VALID_TIME_HOOK_KEY = "run_valid_time_hook";
    }

    public enum RunMode {
        DEFAULT(0, "默认"),
        ORDER(1, "有序点位"),
        DISORDER(2, "无序点位");

        private final int value;
        private final String text;

        RunMode(int value, String text) {
            this.value = value;
            this.text = text;
        }

        public static String getText(int value) {
            for (RunMode runMode : RunMode.values()) {
                if (runMode.value == value) {
                    return runMode.text;
                }
            }
            return null;
        }

        public String getText() {
            return text;
        }

        public int getValue() {
            return value;
        }
    }
}
