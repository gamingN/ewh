package com.example.veeotech.easywarehouse.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.bean.HurryBean;

import java.util.List;

/**
 * Created by VeeoTech on 9/4/2018.
 */

public class HurryAdapter extends BaseQuickAdapter<HurryBean,BaseViewHolder> {
    public HurryAdapter(List<HurryBean> data) {
        super(R.layout.item_hurry, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HurryBean item) {
        helper.setText(R.id.tv_item_hurry_dnno,item.getDn_no());
        helper.setText(R.id.tv_item_hurry_sku,item.getSku());
        helper.setText(R.id.tv_item_hurry_qty,item.getQty());
        helper.setText(R.id.tv_item_hurry_outqty,item.getOut_qty());
    }
}
