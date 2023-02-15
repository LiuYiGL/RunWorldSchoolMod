package org.liuyi.run_world_school.mod.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.github.kyuubiran.ezxhelper.EzXHelper;
import com.github.kyuubiran.ezxhelper.Log;

import org.liuyi.run_world_school.mod.Constant;

import java.io.InputStream;

public class WeChatAppreciationDialog extends AlertDialog.Builder {

    Activity activity;

    public WeChatAppreciationDialog(Context context) {
        super(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }
        setTitle(Constant.string.weChatAppreciationTitle);

        try (
                InputStream inputStream = EzXHelper.getModuleRes().getAssets().open("wechat_appreciation_code.png");
        ) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ImageView imageView = new ImageView(activity);
            imageView.setImageBitmap(bitmap);
            setView(imageView);

        } catch (Exception e) {
            Log.e("设置赞赏码异常", e);
        }

        setPositiveButton("打开微信扫一扫", ((dialog, which) -> {
            if (activity != null) {
                Intent intent = new Intent("com.tencent.mm.ui.ShortCutDispatchAction");
                activity.startActivity(intent);
            }
        }));

        setNegativeButton("下次一定", (dialog, which) -> {
        });

    }
}
