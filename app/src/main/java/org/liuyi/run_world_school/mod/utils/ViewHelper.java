package org.liuyi.run_world_school.mod.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.misc.ViewUtils;

import java.util.Objects;

@SuppressLint("DiscouragedApi")
public class ViewHelper {

    private LayoutInflater layoutInflater;
    public static final String activity_setting_layout_name = "activity_setting";
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


}
