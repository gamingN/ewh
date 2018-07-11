package com.example.veeotech.easywarehouse.old;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.veeotech.easywarehouse.BaseActivity;
import com.example.veeotech.easywarehouse.R;

/**
 * Created by VeeoTech on 2018/3/23.
 */

public class ResultActivity extends BaseActivity {
    TextView tv_result;
    Button bt_comf;
    String str_resultinfo;

    @Override
    protected void init() {
        setToolBarTitle("掃碼結果");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_result;
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
        str_resultinfo=getIntent().getStringExtra(KEY_PASSRESULT);
        tv_result=(TextView) findViewById(R.id.tv_result);
        bt_comf=(Button) findViewById(R.id.bt_comf);

        tv_result.setText("結果:"+str_resultinfo);


        bt_comf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
