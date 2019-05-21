package com.example.demo.dialog;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.demo.PickPayMethodAdapter;
import com.example.demo.R;
import com.example.demo.bean.AddressBean;
import com.example.demo.greendao.db.RegionDB;

import java.util.List;

/**
 * 文件名:PickPayMethodDialog
 * 创建者:zed
 * 创建日期:2019/4/10 18:28
 * 描述:选择省市区
 */
public class SelectAreaDialog extends BaseDialog implements View.OnClickListener {
    private RecyclerView rlList;
    private PickPayMethodAdapter mPickPayMethodAdapter;
    //填充内容的view
    private TextView mContentView;
    private RadioGroup rgGroup;
    private AddressBean mRegionBean;
    private AddressBean mProvinceBean;
    private AddressBean mCityBean;
    private AddressBean mBarangayBean;
    private List<AddressBean> allRegions;
    private List<AddressBean> provinceBeans;
    private List<AddressBean> cityBeans;
    private List<AddressBean> barangays;
    private int checkPosition = -1;
    private RadioButton tabRegion;
    private RadioButton tabProvince;
    private RadioButton tabCity;
    private RadioButton tabBarangay;

    public SelectAreaDialog(Context context) {
        super(context, true);
        setContentView(R.layout.dialog_area_pick);
        setCanceledOnTouchOutside(true);
        tabRegion = findViewById(R.id.tabRegion);
        tabRegion.setOnClickListener(this);
        tabProvince = findViewById(R.id.tabProvince);
        tabProvince.setOnClickListener(this);
        tabCity = findViewById(R.id.tabCity);
        tabCity.setOnClickListener(this);
        tabBarangay = findViewById(R.id.tabBarangay);
        tabBarangay.setOnClickListener(this);

        findViewById(R.id.rlContainer).setOnClickListener(this);
        findViewById(R.id.btCancel).setOnClickListener(this);
        findViewById(R.id.tvSure).setOnClickListener(this);
        rgGroup = findViewById(R.id.rgGroup);
        rlList = findViewById(R.id.rlList);
        rlList.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ed_area_split));
        rlList.addItemDecoration(dividerItemDecoration);
        mPickPayMethodAdapter = new PickPayMethodAdapter(R.layout.item_pick_area_method);
        allRegions = RegionDB.getAllRegions();
        rlList.setAdapter(mPickPayMethodAdapter);
        mPickPayMethodAdapter.replaceData(allRegions);
        mPickPayMethodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //记录当前的item的选中位置
                AddressBean obj = (AddressBean) adapter.getData().get(position);
                if (obj.getType() == 1) {
                    mRegionBean = obj;
                    //州更新 之后的省市街道都要清空
                    provinceBeans = null;
                    mProvinceBean = null;
                    cityBeans = null;
                    mCityBean = null;
                    barangays = null;
                    mBarangayBean = null;

                    tabRegion.setText(mRegionBean.getName());
                    tabProvince.setText("请选择");

                    long allChildCount = RegionDB.getAllChildCount(mRegionBean.getId());
                    if (allChildCount > 0) {
                        //默认选中后 进入下一个地址选项
                        tabProvince.performClick();
                    } else {
                        tabProvince.setText("");
                        //最后一项选中
                        mPickPayMethodAdapter.setCheckCurrentPosition(position);
                        dismiss();
                        //如果没有下一项了 直接填充数据
                        fillTextViewContent();
                    }
                } else if (obj.getType() == 2) {
                    mProvinceBean = obj;
                    //省更新 之后的市街道都要清空
                    cityBeans = null;
                    mCityBean = null;
                    barangays = null;
                    mBarangayBean = null;
                    tabProvince.setText(mProvinceBean.getName());
                    tabCity.setText("请选择");

                    long allChildCount = RegionDB.getAllChildCount(mProvinceBean.getId());
                    if (allChildCount > 0) {
                        //默认选中后 进入下一个地址选项
                        tabCity.performClick();
                    } else {
                        tabCity.setText("");
                        //最后一项选中
                        mPickPayMethodAdapter.setCheckCurrentPosition(position);
                        dismiss();
                        //如果没有下一项了 直接填充数据
                        fillTextViewContent();
                    }
                } else if (obj.getType() == 3) {
                    mCityBean = obj;
                    //市更新 之后的街道都要清空
                    barangays = null;
                    mBarangayBean = null;
                    tabCity.setText(mCityBean.getName());
                    tabBarangay.setText("请选择");
                    long allChildCount = RegionDB.getAllChildCount(mCityBean.getId());
                    if (allChildCount > 0) {
                        //默认选中后 进入下一个地址选项
                        tabBarangay.performClick();
                    } else {
                        tabBarangay.setText("");
                        //最后一项选中
                        mPickPayMethodAdapter.setCheckCurrentPosition(position);
                        dismiss();
                        //如果没有下一项了 直接填充数据
                        fillTextViewContent();
                    }
                } else if (obj.getType() == 4) {
                    //如果自动下一步 只需要最后一项选中
                    mPickPayMethodAdapter.setCheckCurrentPosition(position);
                    mBarangayBean = obj;
                    tabBarangay.setText(mBarangayBean.getName());
                    dismiss();
                    //只有全部选择完毕才会填充
                    fillTextViewContent();
                }

                //如果自动下一步就不需要这个选中的效果
//                mPickPayMethodAdapter.setCheckCurrentPosition(position);
                if (checkPosition != position)
                    mPickPayMethodAdapter.resetOldItem(checkPosition);
                checkPosition = position;
                validateButton();
            }
        });
        validateButton();
    }

    /**
     * 填充文本
     */
    private void fillTextViewContent() {
        StringBuilder stringBuffer = new StringBuilder();
        if (mRegionBean != null) {
            stringBuffer.append(mRegionBean.getName());
            stringBuffer.append(" ");
        }
        if (mProvinceBean != null) {
            stringBuffer.append(mProvinceBean.getName());
            stringBuffer.append(" ");
        }
        if (mCityBean != null) {
            stringBuffer.append(mCityBean.getName());
            stringBuffer.append(" ");
        }
        if (mBarangayBean != null) {
            stringBuffer.append(mBarangayBean.getName());
            stringBuffer.append(" ");
        }
        //填充数据
        if (mContentView != null && stringBuffer.length() > 0)
            mContentView.setText(stringBuffer.toString());
    }

    /**
     * 绑定数据展示的view
     *
     * @param tv
     */
    public void dataBindView(TextView tv) {
        mContentView = tv;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSure:
                fillTextViewContent();
                dismiss();
                break;
            case R.id.btCancel:
                resetStatus();
                cancel();
                break;
            case R.id.tabRegion:
                mPickPayMethodAdapter.replaceData(allRegions);
                checkDefaultItem(mRegionBean);
                break;
            case R.id.tabProvince:
                if (mRegionBean == null) {
                    Log.e("SelectAreaDialog", "mRegionBean is NULL");
                    return;
                }
                provinceBeans = RegionDB.getAllChild(mRegionBean.getId());
                mPickPayMethodAdapter.replaceData(provinceBeans);
                checkDefaultItem(mProvinceBean);
                break;
            case R.id.tabCity:
                if (mRegionBean == null) {
                    Log.e("SelectAreaDialog", "mRegionBean is NULL");
                    return;
                }
                if (mProvinceBean == null) {
                    Log.e("SelectAreaDialog", "mProvinceBean is NULL");
                    return;
                }
                cityBeans = RegionDB.getAllChild(mProvinceBean.getId());
                mPickPayMethodAdapter.replaceData(cityBeans);
                checkDefaultItem(mCityBean);
                break;
            case R.id.tabBarangay:
                if (mRegionBean == null) {
                    Log.e("SelectAreaDialog", "mRegionBean is NULL");
                    return;
                }
                if (mProvinceBean == null) {
                    Log.e("SelectAreaDialog", "mProvinceBean is NULL");
                    return;
                }
                if (mCityBean == null) {
                    Log.e("SelectAreaDialog", "mCityBean is NULL");
                    return;
                }
                barangays = RegionDB.getAllChild(mCityBean.getId());
                tabBarangay.setVisibility(View.VISIBLE);
                if (barangays.size() <= 0) {
                    tabBarangay.setVisibility(View.INVISIBLE);
                } else {
                    mPickPayMethodAdapter.replaceData(barangays);
                    checkDefaultItem(mBarangayBean);
                }
                break;
        }
        validateButton();
    }

    @Override
    public void cancel() {
        super.cancel();
        resetStatus();
    }

    private void resetStatus() {
        mRegionBean = null;
        mProvinceBean = null;
        mCityBean = null;
        mBarangayBean = null;
        //州更新 之后的省市街道都要清空
        provinceBeans = null;
        cityBeans = null;
        barangays = null;
        if (tabRegion != null) {
            tabRegion.setText("请选择");
            tabRegion.performClick();
        }
    }

    /**
     * 默认选中的item
     *
     * @param bean
     */
    private void checkDefaultItem(AddressBean bean) {
        checkPosition = -1;
        if (bean != null) {
            if (bean.getType() == 1) {
                checkPosition = allRegions.indexOf(bean);
            } else if (bean.getType() == 2) {
                for (int i = 0; i < provinceBeans.size(); i++) {
                    if (provinceBeans.get(i).getId().equals(bean.getId())) {
                        checkPosition = i;
                        break;
                    }
                }
            } else if (bean.getType() == 3) {
                for (int i = 0; i < cityBeans.size(); i++) {
                    if (cityBeans.get(i).getId().equals(bean.getId())) {
                        checkPosition = i;
                        break;
                    }
                }
            } else if (bean.getType() == 4) {
                for (int i = 0; i < barangays.size(); i++) {
                    if (barangays.get(i).getId().equals(bean.getId())) {
                        checkPosition = i;
                        break;
                    }
                }
            }
        }
        if (checkPosition > -1)
            rlList.scrollToPosition(checkPosition);
        mPickPayMethodAdapter.setCheckCurrentPosition(checkPosition);
    }

    /**
     * 验证当前按钮是否可以点击
     * 不需要检验第一个按钮 因为第一个一直可以点击
     */
    private void validateButton() {
//        findViewById(R.id.tabRegion).setEnabled(mRegionBean != null);
        //如果州不为空，则省可以点击
        findViewById(R.id.tabProvince).setEnabled(mRegionBean != null);
        findViewById(R.id.tabProvince).setVisibility(mRegionBean != null ? View.VISIBLE : View.INVISIBLE);
        //如果省不为空，则市可以点击
        findViewById(R.id.tabCity).setEnabled(mProvinceBean != null);
        findViewById(R.id.tabCity).setVisibility(mProvinceBean != null ? View.VISIBLE : View.INVISIBLE);
        //如果市不为空，则街道可以点击
        findViewById(R.id.tabBarangay).setEnabled(mCityBean != null);
        findViewById(R.id.tabBarangay).setVisibility(barangays == null ? View.INVISIBLE : barangays.size() > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    public AddressBean getBarangayBean() {
        return mBarangayBean;
    }

    public AddressBean getCityBean() {
        return mCityBean;
    }

    public AddressBean getRegionBean() {
        return mRegionBean;
    }

    public AddressBean getProvinceBean() {
        return mProvinceBean;
    }
}
