package com.example.veeotech.easywarehouse.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.bean.OutBean;
import com.example.veeotech.easywarehouse.ChuActivity;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;

import java.util.List;

/**
 * Created by VeeoTech on 8/4/2018.
 */

public class OutAdapter extends BaseQuickAdapter<OutBean,BaseViewHolder> {
    public OutAdapter(List<OutBean> data) {
        super(R.layout.item_out, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OutBean item) {
        helper.setText(R.id.tv_item_output_sku, item.getSku());
        helper.setText(R.id.tv_item_output_uid,item.getUid());
        helper.setText(R.id.tv_item_output_brand,item.getBrand());
        helper.setText(R.id.tv_item_output_qty, item.getQty()+"");

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, ChuActivity.class);
                intent.putExtra(IntentKeyUtil.KEY_SKU,item.getSku());
                intent.putExtra(IntentKeyUtil.KEY_UID,item.getUid());
                intent.putExtra(IntentKeyUtil.KEY_BRAND,item.getBrand());
                intent.putExtra(IntentKeyUtil.KEY_QTY,item.getQty());
                mContext.startActivity(intent);
            }
        });
    }

}
