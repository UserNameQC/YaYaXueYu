package com.runto.controler.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.runto.controler.R;

public class DialogUtils {

    public static Dialog createBottomDialog(Context context, int layoutId){
        final Dialog dialog = new Dialog(context, R.style.Dialog_Bottom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutId);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels;
        window.setAttributes(lp);
        return dialog;
    }

    public static Dialog showDialogWithLayout(Context context, int layoutId) {
        Dialog dialog = new Dialog(context, R.style.my_dialog);
        dialog.setContentView(layoutId);
        //setWindowTransparent(dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }
}
