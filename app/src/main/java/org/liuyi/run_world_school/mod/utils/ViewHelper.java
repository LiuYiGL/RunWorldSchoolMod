package org.liuyi.run_world_school.mod.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.misc.ViewUtils;

import java.util.Objects;

@SuppressLint("DiscouragedApi")
public class ViewHelper {

    private LayoutInflater layoutInflater;
    public static final String activity_setting_layout_name = "activity_setting";
    public static final String account_setting_layout_name = "account_setting";
    public static final String arrowItemIdName = "tvAccountSetting";


    public ViewHelper(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public TextView getArrowItem(int id, String text) {
        TextView res = null;
        try {
            View view = getLayoutView(activity_setting_layout_name);
            assert view != null;
            res = (TextView) ViewUtils.INSTANCE.findViewByIdName(view, arrowItemIdName);
            assert res != null;
            res.setId(id);
            res.setText(text);
            res.setClickable(true);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getArrowItem");
        }
        return res;
    }

    public View getLineItem() {
        View res = null;
        try {
            View view = getLayoutView(activity_setting_layout_name);
            assert view != null;
            LinearLayout parent = (LinearLayout) (Objects.requireNonNull(ViewUtils.INSTANCE.findViewByIdName(view, arrowItemIdName)).getParent());
            res = parent.getChildAt(1);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getLineItem");
        }
        return res;
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public LinearLayout getSwitchItem(int id, String text) {
        LinearLayout res = null;
        try {
            View view = getLayoutView(activity_setting_layout_name);
            assert view != null;
            res = (LinearLayout) Objects.requireNonNull(ViewUtils.INSTANCE.findViewByIdName(view, "tv_notify_server")).getParent();
            TextView tvTitle = (TextView) res.getChildAt(0);
            Switch aSwitch = (Switch) res.getChildAt(1);
            tvTitle.setText(text);
            aSwitch.setId(id);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getSwitchItem");
        }
        return res;
    }

    public LinearLayout getCategoryItem() {
        LinearLayout res = null;
        try {
            View view = getLayoutView(activity_setting_layout_name);
            assert view != null;
            res = (LinearLayout) (Objects.requireNonNull(ViewUtils.INSTANCE.findViewByIdName(view, arrowItemIdName)).getParent());
            res.removeAllViews();
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getCategoryItem");
        }
        return res;
    }

    public LinearLayout getArrowAndStateItem(int titleId, String titleText, int stateId, String stateText) {
        LinearLayout res = null;
        try {
            View view = getLayoutView(account_setting_layout_name);
            assert view != null;
            res = (LinearLayout) ViewUtils.INSTANCE.findViewByIdName(view, "llWX");
            assert res != null;
            TextView tvTitle = (TextView) res.getChildAt(0);
            TextView tvState = (TextView) res.getChildAt(1);

            tvTitle.setId(titleId);
            tvTitle.setText(titleText);
            tvState.setId(stateId);
            tvState.setText(stateText);

            res.setId(0);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getArrowAndStateItem");
        }
        return res;
    }

    public TextView getSectionItem(int id, String text) {
        TextView res = null;
        try {
            View layoutView = getLayoutView(account_setting_layout_name);
            assert layoutView != null;
            res = ViewUtils.INSTANCE.findViewByConditionAs((ViewGroup) layoutView,
                    view -> view instanceof TextView && ((TextView) view).getText() == "账号绑定");
            assert res != null;
            res.setId(id);
            res.setText(text);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getSectionItem");
        }
        return res;
    }


    /**
     * 实例化一个布局
     *
     * @param layoutName
     * @return
     */
    private View getLayoutView(String layoutName) {
        try {
            int layout_id = EzXHelper.getAppContext().getResources().getIdentifier(layoutName,
                    "layout", EzXHelper.getAppContext().getPackageName());
            return layoutInflater.inflate(layout_id, null);
        } catch (Exception e) {
            Log.e(e, "fail to getLayoutView");
        }
        return null;
    }

    /**
     * 移除父子控件的连接
     *
     * @param view
     */
    private void removeParent(View view) {
        if (view != null && view.getParent() != null) {
            ((ViewGroup) (view.getParent())).removeView(view);
        }
    }

    public static class CategoryLinearLayoutBuilder {
        private LinearLayout linearLayout;
        private final ViewHelper viewHelper;

        public CategoryLinearLayoutBuilder(ViewHelper viewHelper) {
            this.viewHelper = viewHelper;
            if (viewHelper != null) {
                linearLayout = viewHelper.getCategoryItem();
            }
        }

        public LinearLayout build() {
            if (viewHelper == null) {
                return null;
            }
            LinearLayout oldLinearLayout = linearLayout;
            linearLayout = viewHelper.getCategoryItem();
            return oldLinearLayout;
        }

        public CategoryLinearLayoutBuilder addItem(View view) {
            if (viewHelper != null && view != null) {
                linearLayout.addView(view);
                linearLayout.addView(viewHelper.getLineItem());
            }
            return this;
        }
    }

}
