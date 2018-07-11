package com.example.veeotech.easywarehouse.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.veeotech.easywarehouse.AlterOrderActivity;
import com.example.veeotech.easywarehouse.bean.OrderBean;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.SPUtils;

import java.util.List;

/**
 * Created by VeeoTech on 2018/3/27.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderBean,BaseViewHolder> {


    public OrderAdapter(List<OrderBean> data) {
        super(R.layout.item_huodan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderBean item) {


        if(Integer.parseInt(item.getQty())<=Integer.parseInt(item.getRec_qty())){
            CardView cardView=helper.getView(R.id.cv_item);
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colortest_green));

        }else {
            CardView cardView=helper.getView(R.id.cv_item);
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colortest_red));

        }
        helper.setText(R.id.tv_item_dnno, item.getDn_no());
        helper.setText(R.id.tv_item_sku, item.getSku());
        helper.setText(R.id.tv_item_qty, item.getQty());
        helper.setText(R.id.tv_item_outqty, item.getRec_qty());
        helper.setText(R.id.tv_item_dndate,item.getOrder_date());

        if(Integer.valueOf(item.getRec_qty())<Integer.valueOf(item.getQty())){
            IntentKeyUtil.FLAG_COMMIT=1;
        }

        SPUtils.put(mContext,IntentKeyUtil.KEY_COMMIT_FLAG,IntentKeyUtil.FLAG_COMMIT);
//        helper.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(mContext,AlterOrderActivity.class);
//                intent.putExtra(IntentKeyUtil.KEY_ID,item.getId());
//                intent.putExtra(IntentKeyUtil.KEY_DNNO,item.getDn_no());
//                intent.putExtra(IntentKeyUtil.KEY_SKU,item.getSku());
//                intent.putExtra(IntentKeyUtil.KEY_QTY,item.getQty());
//                intent.putExtra(IntentKeyUtil.KEY_RECQTY,item.getRec_qty());
//                mContext.startActivity(intent);
//            }
//        });
    }
}
