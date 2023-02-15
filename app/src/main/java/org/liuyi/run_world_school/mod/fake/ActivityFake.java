package org.liuyi.run_world_school.mod.fake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    private ScrollView scrollView;

    public void fakeActivity(Activity activity) {
        this.activity = activity;
        ViewGroup root = activity.findViewById(android.R.id.content);
        if (!isInit) {
            layoutContent = new LinearLayout(activity);
            layoutContent.setOrientation(LinearLayout.VERTICAL);
            scrollView = new ScrollView(activity);
            scrollView.addView(layoutContent);
            ((LinearLayout) root.getChildAt(0)).addView(scrollView, 1);
            initActivity();
        } else {
            if (scrollView.getParent() != null) {
                ((ViewGroup) scrollView.getParent()).removeView(scrollView);
            }
            ((LinearLayout) root.getChildAt(0)).addView(scrollView, 1);
        }
        setTitle(getTitle());
        restoreState();
    }

    protected abstract void startActivity();

    protected abstract void initActivity();

    protected abstract void initLayout();

    protected abstract String getTitle();

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
        if (activity != null) {
            try {
                TextView textView = (TextView) ViewUtils.INSTANCE.findViewByIdName(activity, "title");
                assert textView != null;
                textView.setText(title);
            } catch (Exception e) {
                Log.e(e, "fail to set title, may be due to activity");
            }
        }
    }

    public <T extends View> T findViewById(int id) {
        if (activity != null) {
            return activity.findViewById(id);
        }
        return null;
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

    public abstract void restoreState();
}
