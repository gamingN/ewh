package com.example.veeotech.easywarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.ui_fragment.TabMainActivity;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.SPUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by VeeoTech on 2018/3/22.
 */

public class LoginActivity extends BaseActivity {

    private EditText et_zhanghao;
    private EditText et_mima;
    private Button bt_login;
    private String uid;
    private String pw;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        et_zhanghao = (EditText) findViewById(R.id.et_zhanghao);
        et_mima = (EditText) findViewById(R.id.et_mima);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = et_zhanghao.getText().toString();
                pw = et_mima.getText().toString();

                loginRequest();
            }
        });
    }

    private void loginRequest() {
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<String> call = ipService.getloginaid(uid, pw);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("kiki", "onResponse: "+response.body());
                if (response.body().equals("1")) {
                    SPUtils.put(getApplicationContext(), IntentKeyUtil.KEY_AID, uid);
                    Intent intent = new Intent(LoginActivity.this, TabMainActivity.class);
//                    intent.putExtra("person_Id",uid);
                    startActivity(intent);
                    finish();
                } else {
                    et_zhanghao.setText("");
                    et_mima.setText("");
                    Toast.makeText(getApplicationContext(), "登錄失敗", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    protected void init() {
        setToolBarTitle("員工請登錄");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected boolean isShowRightText() {
        return false;
    }


}
