package com.threedai.handheldmagicmirror.utils;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.kongzue.dialog.v3.CustomDialog;
import com.threedai.handheldmagicmirror.R;

public class DialogUtils {

    private DialogUtils(){}

    /**
     * 获取转圈loading 的dialog
     * @param appCompatActivity activity上下文
     * @return
     */
    public static CustomDialog getLoadingDialog(AppCompatActivity appCompatActivity) {
        CustomDialog customDialog = CustomDialog.build(appCompatActivity, R.layout.dialog_loading, (dialog, v) -> {
            ImageView loading = v.findViewById(R.id.iv_loading);
            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(1000);
            rotate.setRepeatCount(Animation.INFINITE);
            loading.startAnimation(rotate);
        }).setCancelable(false);
        return customDialog;
    }
}
