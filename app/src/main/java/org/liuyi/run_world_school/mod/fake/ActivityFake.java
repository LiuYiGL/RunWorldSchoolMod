package org.liuyi.run_world_school.mod.fake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.kyuubiran.ezxhelper.Log;
import com.github.kyuubiran.ezxhelper.misc.ViewUtils;

@SuppressLint("StaticFieldLeak")
public abstract class ActivityFake {

    private Activity activity;

    private boolean isInit;
    private boolean isLayoutInit;
    private LinearLayout layoutContent;
    private TextView tvTitle;

    public void fakeActivity(Activity activity) {
        this.activity = activity;
        if (!isInit) {
            layoutContent = new LinearLayout(activity);
            initActivity();
        } else {
            ((ViewGroup) layoutContent.getParent()).removeView(layoutContent);
        }
        ViewGroup root = activity.findViewById(android.R.id.content);
        ((LinearLayout) root.getChildAt(0)).addView(layoutContent, 1);
    }

    public abstract void startActivity();

    public abstract void initActivity();

    public abstract void initLayout();

    public boolean isLayoutInit() {
        return isLayoutInit;
    }

    public void setLayoutInit(boolean layoutInit) {
        isLayoutInit = layoutInit;
    }

    public LinearLayout getLayoutContent() {
        return layoutContent;
    }

    public void setLayoutContent(LinearLayout layoutContent) {
        this.layoutContent = layoutContent;
    }

    public void setTitle(String title) {
        if (tvTitle == null && activity != null) {
            try {
                tvTitle = (TextView) ViewUtils.INSTANCE.findViewByIdName(activity, "title");
            } catch (Exception e) {
                Log.e(e, "fail to set title, may be due to activity");
            }
        }
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }


    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

}
