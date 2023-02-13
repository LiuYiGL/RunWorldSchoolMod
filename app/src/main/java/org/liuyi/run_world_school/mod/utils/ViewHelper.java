package org.liuyi.run_world_school.mod.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.misc.ViewUtils;

import java.util.ArrayList;
import java.util.Objects;

import de.robv.android.xposed.XposedHelpers;

@SuppressLint("DiscouragedApi")
public class ViewHelper {

    private LayoutInflater layoutInflater;
    private Context context;
    private static boolean isInit;
    private static final String activity_setting_layout_name = "activity_setting";
    private static final String activity_account_setting_layout_name = "activity_account_setting";
    public static final String arrowItemIdName = "tvAccountSetting";
    private static Object activitySettingXmlBlockObj;
    private static Object activityAccountSettingXmlBlockObj;

    private static final ArrayList<TextView> arrowItemList = new ArrayList<>();
    private static final ArrayList<View> lineItemList = new ArrayList<>();
    private static final ArrayList<LinearLayout> switchItemList = new ArrayList<>();
    private static final ArrayList<LinearLayout> categoryItemList = new ArrayList<>();
    private static final ArrayList<LinearLayout> arrowAndStateItemItemList = new ArrayList<>();
    private static final ArrayList<TextView> sectionItemList = new ArrayList<>();


    private ViewHelper() {
    }

    public static ViewHelper newInstance(Context context) {
        ViewHelper viewHelper = new ViewHelper();
        viewHelper.layoutInflater = LayoutInflater.from(context);
        viewHelper.context = context;

        if (!isInit) {
            int id1 = ViewUtils.getIdByName(activity_setting_layout_name, "layout", context);
            XmlResourceParser layout1 = context.getResources().getLayout(id1);
            activitySettingXmlBlockObj = XposedHelpers.getObjectField(layout1, "mBlock");

            int id2 = ViewUtils.getIdByName(activity_account_setting_layout_name, "layout", context);
            XmlResourceParser layout2 = context.getResources().getLayout(id2);
            activityAccountSettingXmlBlockObj = XposedHelpers.getObjectField(layout2, "mBlock");
            isInit = true;
        }
        return viewHelper;
    }

    private XmlResourceParser getXmlResourceParser(Object obj) {
        if (obj.getClass().getName().equals("android.content.res.XmlBlock")) {
            return (XmlResourceParser) XposedHelpers.callMethod(obj, "newParser");
        }
        return null;
    }

    private void divideViewFromActivitySettingLayout() {
        try {
            ViewGroup layoutView = (ViewGroup) layoutInflater.inflate(getXmlResourceParser(activitySettingXmlBlockObj), null);
            ViewUtils.INSTANCE.findAllViewsByCondition(layoutView, view -> {
                        if (view instanceof TextView) {
                            return ((TextView) view).getCompoundDrawables()[2] != null;
                        }
                        return false;
                    }).stream().map(view -> (TextView) view)
                    .peek(ViewHelper::removeParent)
                    .peek(ViewHelper::initView)
                    .forEach(arrowItemList::add);

            ViewUtils.INSTANCE.findAllViewsByCondition(layoutView, view -> view.getClass().equals(View.class)
                            && view.getId() == -1)
                    .stream().peek(ViewHelper::removeParent)
                    .peek(ViewHelper::initView)
                    .forEach(lineItemList::add);

            ViewUtils.INSTANCE.findAllViewsByCondition(layoutView, view -> view instanceof LinearLayout
                            && ((LinearLayout) view).getChildAt(1) instanceof Switch)
                    .stream().map(view -> (LinearLayout) view)
                    .peek(ViewHelper::removeParent)
                    .peek(ViewHelper::initView)
                    .forEach(switchItemList::add);

        } catch (Exception e) {
            Log.e(e, "");
        }
    }

    public TextView getArrowItem(int id, String text) {
        TextView res;
        try {
            if (arrowItemList.isEmpty()) {
                divideViewFromActivitySettingLayout();
            }
            res = arrowItemList.remove(0);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getArrowItem");
            res = new TextView(context);
        }
        res.setId(id);
        res.setText(text);
        res.setClickable(true);
        return res;
    }

    public View getLineItem() {
        View res;
        try {
            if (lineItemList.isEmpty()) {
                divideViewFromActivitySettingLayout();
            }
            res = lineItemList.remove(0);
        } catch (Exception e) {
            Log.e(e, "fail to getLineItem");
            res = new View(context);
        }
        return res;
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public synchronized LinearLayout getSwitchItem(int id, String text) {
        LinearLayout res;
        try {
            if (switchItemList.isEmpty()) {
                divideViewFromActivitySettingLayout();
            }
            res = switchItemList.remove(0);
            TextView tvTitle = (TextView) res.getChildAt(0);
            Switch aSwitch = (Switch) res.getChildAt(1);
            tvTitle.setText(text);
            aSwitch.setId(id);
        } catch (Exception e) {
            Log.e(e, "fail to getSwitchItem");
            res = new LinearLayout(context);
        }
        return res;
    }

    public synchronized LinearLayout getCategoryItem() {
        LinearLayout res = null;
        try {
            View view = layoutInflater.inflate(getXmlResourceParser(activitySettingXmlBlockObj), null);
            assert view != null;
            res = (LinearLayout) (Objects.requireNonNull(ViewUtils.INSTANCE.findViewByIdName(view, arrowItemIdName)).getParent());
            res.removeAllViews();
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getCategoryItem");
        }
        return res;
    }

    public synchronized LinearLayout getArrowAndStateItem(int titleId, String titleText, int stateId, String stateText) {
        LinearLayout res = null;
        try {
            View view = layoutInflater.inflate(getXmlResourceParser(activityAccountSettingXmlBlockObj), null);
            assert view != null;
            res = (LinearLayout) ViewUtils.INSTANCE.findViewByIdName(view, "llWX");
            assert res != null;
            TextView tvTitle = (TextView) res.getChildAt(0);
            TextView tvState = (TextView) res.getChildAt(1);

            tvTitle.setText(titleText);
            tvState.setId(stateId);
            tvState.setText(stateText);

            res.setId(titleId);
            ViewGroup.LayoutParams params = arrowItemList.get(0).getLayoutParams();
            res.setLayoutParams(params);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getArrowAndStateItem");
        }
        return res;
    }

    public synchronized TextView getSectionItem(int id, String text) {
        TextView res = null;
        try {
            View layoutView = layoutInflater.inflate(getXmlResourceParser(activityAccountSettingXmlBlockObj), null);
            assert layoutView != null;
            res = ViewUtils.INSTANCE.findViewByConditionAs((ViewGroup) layoutView,
                    view -> {
                        if (view instanceof TextView) {
                            String s = (String) ((TextView) view).getText();
                            return s.equals("账号绑定");
                        }
                        return false;
                    });
            assert res != null;
            res.setId(id);
            res.setText(text);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) res.getLayoutParams();
            params.setMargins(0, params.topMargin / 2, 0, params.bottomMargin / 2);
            removeParent(res);
        } catch (Exception e) {
            Log.e(e, "fail to getSectionItem");
        }
        return res;
    }

    /**
     * 移除父子控件的连接
     *
     * @param view
     */
    private static void removeParent(View view) {
        if (view != null && view.getParent() != null) {
            ((ViewGroup) (view.getParent())).removeView(view);
        }
    }

    private static void initView(View view) {
        if (view == null) {
            return;
        }
        view.setId(0);
        view.setVisibility(View.VISIBLE);

        if (view instanceof TextView) {
            ((TextView) view).setText("");
        } else if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                initView(linearLayout.getChildAt(i));
            }
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

        public CategoryLinearLayoutBuilder(Context context) {
            this(ViewHelper.newInstance(context));
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

        public CategoryLinearLayoutBuilder addSwitchItem(int id, String text) {
            return viewHelper != null ?
                    addItem(viewHelper.getSwitchItem(id, text)) : this;

        }

        public CategoryLinearLayoutBuilder addArrowItem(int id, String text) {
            return viewHelper != null ?
                    addItem(viewHelper.getArrowItem(id, text)) : this;

        }

        public CategoryLinearLayoutBuilder addArrowAndStateItem(int titleId, String titleText, int stateId, String stateText) {
            return viewHelper != null ?
                    addItem(viewHelper.getArrowAndStateItem(titleId, titleText, stateId, stateText)) : this;
        }

        public CategoryLinearLayoutBuilder addSectionItem(int id, String text) {
            return viewHelper != null ?
                    addItem(viewHelper.getSectionItem(id, text)) : this;
        }
    }

}
