package com.example.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.demo.MyApplication;
import com.example.demo.R;


public class BaseDialog extends Dialog {
    View mView;
    boolean cancel = true;
    boolean touchOutsideCancel = false;

    protected View getRootView() {
        return mView;
    }

    public BaseDialog(Context context) {
        super(context, R.style.dialog);
    }

    public BaseDialog(Context context, boolean fromButton) {
        super(context, R.style.Dialog_NoTitle);
        if (fromButton) {
            fromBottom();
        }
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(@LayoutRes int resource) {
        mView = LayoutInflater.from(getContext()).inflate(resource, null);
        //设置SelectPicPopupWindow的View
        setContentView(mView);
        //设置全屏
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (touchOutsideCancel)
                    dismiss();
            }
        });
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        cancel = flag;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        touchOutsideCancel = cancel;
    }

    private void fromBottom() {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.width = getScreenWidth();
        localLayoutParams.y = 0;
        localLayoutParams.x = 0;
        onWindowAttributesChanged(localLayoutParams);
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    private int getScreenWidth() {
        WindowManager windowManager =
                (WindowManager) MyApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }
}
