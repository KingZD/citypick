package com.example.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.demo.MyApplication;
import com.example.demo.R;
import com.example.demo.callback.DialogCallback;

/**
 * Created by zd on 16/3/22.
 */
public class TipsDialog {
    private static Dialog dialog;
    private static TipsDialog tipsDialog;

    private static TipsDialog getInstance() {
        if (tipsDialog == null)
            tipsDialog = new TipsDialog();
        return tipsDialog;
    }

    public static TipsDialog createDialog(Context context, int layoutId) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(layoutId);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return tipsDialog;
    }

    public static TipsDialog createDialog(Context context, int layoutId, int styleIds) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, styleIds);
        dialog.setContentView(layoutId);
        return tipsDialog;
    }

    public TipsDialog setCancelable(boolean cancel) {
        if (dialog != null)
            dialog.setCancelable(cancel);
        return tipsDialog;
    }

    public TipsDialog setCanceledOnTouchOutside(boolean cancel) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancel);
        return tipsDialog;
    }

    public static TipsDialog createDialog(Context context, View layout) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(layout);
        return tipsDialog;
    }

    public static TipsDialog createDialogFromBottom(Context context, int layoutId) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, R.style.Dialog_NoTitle);
        dialog.setContentView(layoutId);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        fromBottom();
        return tipsDialog;
    }

    public static TipsDialog createDialogFromBottom(Context context, View layout) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, R.style.Dialog_NoTitle);
        dialog.setContentView(layout);
        fromBottom();
        return tipsDialog;
    }

    private static TipsDialog fromBottom() {
        WindowManager.LayoutParams localLayoutParams = dialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.width = getScreenWidth();
        localLayoutParams.y = 0;
        localLayoutParams.x = 0;
        dialog.onWindowAttributesChanged(localLayoutParams);
        return tipsDialog;
    }

    /**
     * 设置view背景透明度
     *
     * @param alpha
     * @return
     */
    public TipsDialog setBgAplha(float alpha) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.alpha = alpha;
        dialog.getWindow().setAttributes(lp);
        return tipsDialog;
    }

    /**
     * 设置window背景透明度
     *
     * @param alpha
     * @return
     */
    public TipsDialog setDimAplha(float alpha) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = alpha;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return tipsDialog;
    }

    /**
     * 设置文字大小
     *
     * @param viewId
     * @param size
     * @return
     */
    public TipsDialog setTextSize(int viewId, int size) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setTextSize(size);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    /**
     * 设置文字大小
     *
     * @param viewId
     * @param size
     * @return
     */
    public TipsDialog setTextSize(int viewId, int unit, int size) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setTextSize(unit, size);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public TipsDialog setText(int viewId, String title) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setText(title);
            ((TextView) dialog.findViewById(viewId)).setMovementMethod(ScrollingMovementMethod.getInstance());
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public TipsDialog setBackgroundResouce(int viewId, int backgroundId) {
        if (dialog != null) {
            dialog.findViewById(viewId).setBackgroundResource(backgroundId);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public <T extends View> T getView(int viewId) {
        if (dialog != null) {
            return dialog.findViewById(viewId);
        }
        return null;
    }

    public void show(DialogCallback callback) {
        //false 不显示
        if (!callback.callback(this,dialog))
            return;
        if (dialog != null)
            dialog.show();
    }

    public void show() {
        if (dialog != null)
            dialog.show();
    }

    public void dismiss() {
        if (dialog != null)
            dialog.dismiss();
        tipsDialog = null;
        dialog = null;
    }

    public TipsDialog bindClick(final int viewId, final TipClickListener onClickListener) {
        if (dialog != null) {
            dialog.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.onClick(v, TipsDialog.this);
                    dismiss();
                }
            });
        }
        return tipsDialog;
    }

    /**
     * 绑定点击事件
     *
     * @param viewId
     * @param onClickListener
     * @return
     */
    public TipsDialog bindClickV1(final int viewId, final TipClickListener onClickListener) {
        if (dialog != null) {
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
            dialog.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.onClick(v, TipsDialog.this);
                }
            });
        }
        return tipsDialog;
    }

    public TipsDialog OnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null && onCancelListener != null) {
            dialog.setOnCancelListener(onCancelListener);
        }
        return tipsDialog;
    }

    public interface TipClickListener {
        void onClick(View v, TipsDialog dialog);
    }

    public TipsDialog setVisible(Context context, int viewId, boolean isVisible) {
        if (dialog != null) {
            if (isVisible) {
                dialog.findViewById(viewId).setVisibility(View.VISIBLE);
            } else {
                dialog.findViewById(viewId).setVisibility(View.GONE);
            }
        }
        return tipsDialog;
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    private static int getScreenWidth() {
        WindowManager windowManager =
                (WindowManager) MyApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }
}
