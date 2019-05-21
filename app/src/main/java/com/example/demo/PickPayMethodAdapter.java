package com.example.demo;


import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.demo.bean.AddressBean;

/**
 * 文件名:PickPayMethodAdapter
 * 创建者:zed
 * 创建日期:2019/4/10 18:23
 * 描述:TODO
 */
public class PickPayMethodAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    private int mCheckCurrentPosition = -1;
    private int mOldCheckPosition = -1;

    public PickPayMethodAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        if (item instanceof String) {
            helper.setText(R.id.tvSelect, item.toString());
        } else if (item instanceof AddressBean) {
            AddressBean bean = (AddressBean) item;
            helper.setText(R.id.tvSelect, bean.getName());
        }
        int color = helper.getAdapterPosition() == mCheckCurrentPosition
                ? R.color.color_f83d2f : R.color.color_333333;
        if (mOldCheckPosition == helper.getAdapterPosition()) {
            color = R.color.color_333333;
            mOldCheckPosition = -1;
        }
        ((TextView) helper.getView(R.id.tvSelect))
                .setTextColor(ContextCompat.getColor(helper.itemView.getContext(), color));
        helper.setVisible(R.id.icCheck, helper.getAdapterPosition() == mCheckCurrentPosition);
    }


    /**
     * 选中点击的 item
     *
     * @param checkCurrentPosition
     */
    public void setCheckCurrentPosition(int checkCurrentPosition) {
        this.mCheckCurrentPosition = checkCurrentPosition;
        notifyItemChanged(checkCurrentPosition);
    }

    /**
     * 取消选中旧的item
     *
     * @param oldCheckPosition
     */
    public void resetOldItem(int oldCheckPosition) {
        this.mOldCheckPosition = oldCheckPosition;
        notifyItemChanged(mOldCheckPosition);
    }
}
