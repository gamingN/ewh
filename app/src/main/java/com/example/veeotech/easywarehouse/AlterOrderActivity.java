package com.example.veeotech.easywarehouse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.view.AmountView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by VeeoTech on 29/3/2018.
 */

public class AlterOrderActivity extends BaseActivity {

    private TextView tv_dingdanhao, tv_sku, tv_qty;
    private AmountView mAmountView;
    private Button bt_alter_commit;
    private LinearLayout ll_alterhuodan;


    private String alter_id;
    private String alter_qty;
    private String alter_recqty;
    private String alter_dnno;
    private String alter_sku;

    private int origin_recqty;
    private int alter_recqty_t;

    @Override
    protected void init() {
        setToolBarTitle("修改處理量");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alterhuodan;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isShowRightText() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alter_id = getIntent().getStringExtra(IntentKeyUtil.KEY_ID);
        alter_dnno = getIntent().getStringExtra(IntentKeyUtil.KEY_DNNO);
        alter_sku = getIntent().getStringExtra(IntentKeyUtil.KEY_SKU);
        alter_qty = getIntent().getStringExtra(IntentKeyUtil.KEY_QTY);
        alter_recqty = getIntent().getStringExtra(IntentKeyUtil.KEY_RECQTY);

        ll_alterhuodan = (LinearLayout) findViewById(R.id.ll_alterhuodan);
        bt_alter_commit = (Button) findViewById(R.id.bt_altercommit);
        tv_dingdanhao = (TextView) findViewById(R.id.tv_alter_dingdanhao);
        tv_sku = (TextView) findViewById(R.id.tv_alter_sku);
        tv_qty = (TextView) findViewById(R.id.tv_alter_qty);
        mAmountView = (AmountView) findViewById(R.id.amount_view);

        origin_recqty = Integer.parseInt(alter_recqty);
        alter_recqty_t = Integer.parseInt(alter_recqty);

        tv_dingdanhao.setText(alter_dnno);
        tv_sku.setText(alter_sku);
        tv_qty.setText(alter_qty);

        mAmountView.setAmount(alter_recqty_t);
        mAmountView.postInvalidate();

        if (Integer.parseInt(alter_qty) <= Integer.parseInt(alter_recqty)) {
            ll_alterhuodan.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colortest_green));
        } else {
            ll_alterhuodan.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colortest_red));
        }

        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                alter_recqty_t = amount;
            }
        });

        bt_alter_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request_alter(alter_id, alter_recqty_t);
                finish();

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void request_alter(String id, int alter_recqty) {
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<String> call = ipService.getOrderAlter(id, alter_recqty);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
