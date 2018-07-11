package com.example.veeotech.easywarehouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.veeotech.easywarehouse.AnswerActivity;
import com.example.veeotech.easywarehouse.OnItemWhClickListener;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.bean.WarehouseBean;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;

import java.util.List;

/**
 * Created by VeeoTech on 2018/5/18.
 */

public class WarehouseAdapter extends BaseQuickAdapter<WarehouseBean.DataBean,BaseViewHolder> {
    int if_zone_flag ;
    Context context;
    OnItemWhClickListener onItemWhClickListener;
    public WarehouseAdapter(List<WarehouseBean.DataBean> data, int if_zone_flag, OnItemWhClickListener onItemWhClickListener) {
        super(R.layout.item_warehouse, data);
        this.if_zone_flag = if_zone_flag;
        this.onItemWhClickListener = onItemWhClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final WarehouseBean.DataBean item) {
            helper.setText(R.id.tv_warehouse_sku,item.getSku());
            helper.setText(R.id.tv_warehouse_uid,item.getUid());
            helper.setText(R.id.tv_warehouse_brand,item.getBrand());

            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AnswerActivity.class);
                    intent.putExtra("sku",item.getSku());
                    intent.putExtra("uid",item.getUid());
                    intent.putExtra("brand",item.getBrand());
                    intent.putExtra(IntentKeyUtil.KEY_IF_ZONE,if_zone_flag);
                    mContext.startActivity(intent);
                    onItemWhClickListener.OnItemWhClick();
                    Log.d("zwx","發送Intent UID:"+item.getUid()+" Brand:"+item.getBrand());
                }
            });
    }
}
