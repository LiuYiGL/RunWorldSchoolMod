package org.liuyi.run_world_school.mod.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.liuyi.run_world_school.mod.Constant;

import java.util.Arrays;

public class RunModeDialog extends AlertDialog.Builder {
    public RunModeDialog(Context context) {
        super(context);

        setTitle(Constant.string.runModeTitle);

    }

    public AlertDialog.Builder setSingleChoiceItems(int checkedItem, DialogInterface.OnClickListener listener) {
        String[] items = Arrays.stream(Constant.RunMode.values()).map(Constant.RunMode::getText).toArray(String[]::new);
        return super.setSingleChoiceItems(items, checkedItem, listener);
    }
}
