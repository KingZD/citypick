package com.example.demo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.demo.callback.DialogCallback;
import com.example.demo.dialog.SelectAreaDialog;
import com.example.demo.dialog.TipsDialog;

/**
 * 文件名:MainActivity
 * 创建者:zed
 * 创建日期:2019/5/21 15:00
 * 描述:TODO
 */
public class MainActivity extends AppCompatActivity {
    SelectAreaDialog mSelectAreaDialog;
    TextView tvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = findViewById(R.id.tvShow);
        mSelectAreaDialog = new SelectAreaDialog(this);
    }

    public void showArea(View view) {
        mSelectAreaDialog.dataBindView(tvShow);
        mSelectAreaDialog.show();
    }

    public void showTips(View view) {
        TipsDialog
                .createDialog(this, R.layout.dialog_tips)
                .setText(R.id.tvContent, "我是内容")
                .setText(R.id.tvTitle, "我是标题")
                .bindClick(R.id.btSure, null)
                .show(new DialogCallback() {
                    @Override
                    public boolean callback(TipsDialog tipsDialog, Dialog dialog) {
                        //这里可以做一些初始化数据的操作
                        tipsDialog.setText(R.id.tvContent, "我拦截了标题内容");
                        //这里可以处理 根据业务逻辑选择是否 显示提示框 true显示 false 不显示
                        return true;
                    }
                });
    }
}
