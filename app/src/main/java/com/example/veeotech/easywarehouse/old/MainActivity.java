package com.example.veeotech.easywarehouse.old;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.BaseActivity;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.ChuActivity;

public class MainActivity extends BaseActivity {

    Button bt_rucang;
    Button bt_chucang;
    String str_personId;
    private long firstTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initvView();
    }

    @Override
    protected void init() {
        str_personId=getIntent().getStringExtra("person_Id");
        setToolBarTitle("用戶:"+str_personId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected boolean isShowRightText() {
        return false;
    }

    private void initvView() {
        bt_rucang=(Button) findViewById(R.id.bt_rucang);
        bt_chucang=(Button) findViewById(R.id.bt_chucang);
        bt_rucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_ru=new Intent(getApplicationContext(),RuActivity.class);
                startActivity(intent_ru);
            }
        });

        bt_chucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_chu=new Intent(getApplicationContext(),ChuActivity.class);
                startActivity(intent_chu);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
