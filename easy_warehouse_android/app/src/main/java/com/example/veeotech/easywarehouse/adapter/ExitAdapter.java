package com.example.veeotech.easywarehouse.adapter;

import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.bean.ExitBean;

import java.util.List;

/**
 * Created by VeeoTech on 8/4/2018.
 */

public class ExitAdapter extends BaseQuickAdapter<ExitBean,BaseViewHolder> {
    public ExitAdapter(List<ExitBean> data) {
        super(R.layout.item_huodan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ExitBean item) {
        if(Integer.parseInt(item.getQty())<=Integer.parseInt(item.getOut_qty())){
            CardView cardView=helper.getView(R.id.cv_item);
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colortest_green));
        }else {
            CardView cardView=helper.getView(R.id.cv_item);
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colortest_red));
        }
        helper.setText(R.id.tv_item_dnno, item.getDn_no());
        helper.setText(R.id.tv_item_dndate,item.getOrder_date());
        helper.setText(R.id.tv_item_sku, item.getSku());
        helper.setText(R.id.tv_item_qty, item.getQty());
        helper.setText(R.id.tv_item_outqty, item.getOut_qty());

//        helper.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(mContext,AlterOrderActivity.class);
//                intent.putExtra(IntentKeyUtil.KEY_ID,item.getId());
//                intent.putExtra(IntentKeyUtil.KEY_DNNO,item.getDn_no());
//                intent.putExtra(IntentKeyUtil.KEY_SKU,item.getSku());
//                intent.putExtra(IntentKeyUtil.KEY_QTY,item.getQty());
//                intent.putExtra(IntentKeyUtil.KEY_RECQTY,item.getOut_qty());
//                mContext.startActivity(intent);
//            }
//        });
    }

}
