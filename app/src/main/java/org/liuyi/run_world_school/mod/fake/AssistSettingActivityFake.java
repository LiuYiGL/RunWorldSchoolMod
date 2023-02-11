package org.liuyi.run_world_school.mod.fake;

import android.widget.TextView;

import com.github.kyuubiran.ezxhelper.Log;

import org.liuyi.run_world_school.mod.utils.ViewHelper;

public class AssistSettingActivityFake extends ActivityFake {


    /**
     * 负责绑定控件和监听
     */
    @Override
    public void startActivity() {

    }

    /**
     * 负责对关键变量进行赋值，并执行startActivity
     */
    @Override
    public void initActivity() {
        try {
            if (!isLayoutInit()) {
                initLayout();
            }


        } catch (Exception e) {
            Log.e(e, "");
        }
        setInit(true);
        startActivity();
    }

    /**
     * 负责分配id并放入Content
     */
    @Override
    public void initLayout() {
        ViewHelper viewHelper = new ViewHelper(getActivity());
        // getContentView().setVisibility(View.GONE);
        TextView textView = new TextView(getActivity());
        textView.setText(this.toString());
        getLayoutContent().addView(textView);
        setLayoutInit(true);
    }

}
