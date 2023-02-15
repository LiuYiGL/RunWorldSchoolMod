package org.liuyi.run_world_school.mod.dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.Log;

import org.liuyi.run_world_school.mod.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UseGuiseDialog extends AlertDialog.Builder {

    public UseGuiseDialog(Context context) {
        super(context);

        setTitle(Constant.string.useGuideTitle);
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = EzXHelper.getModuleRes().getAssets().open("use_guide_description.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            bufferedReader.lines().forEach(s -> {
                stringBuilder.append(s).append("\n");
            });

        } catch (IOException e) {
            Log.e(e, "获取指导文本失败");
        }
        setMessage(stringBuilder);
        setPositiveButton("确定", (dialog, which) -> {
        });
        show();
    }
}
