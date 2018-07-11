package com.example.veeotech.easywarehouse;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.adapter.MyFragmentPageAdapter;
import com.example.veeotech.easywarehouse.bean.ExitBean;
import com.example.veeotech.easywarehouse.bean.OrderBean;
import com.example.veeotech.easywarehouse.bean.WarehouseBean;
import com.example.veeotech.easywarehouse.bean.Zonebean;
import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.SPUtils;
import com.example.veeotech.easywarehouse.utils.ScannerUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.functions.Action1;

/**
 * Created by VeeoTech on 2018/3/27.
 */

public class AnswerActivity extends BaseActivity implements View.OnClickListener{
//    private TabLayout tab_answer;
//    private ViewPager vp_answer;
    private Button bt_erwei_contin_c;
    private Button bt_tiaoxing_contin_c;

    private LinearLayout ll_answer;


    private int flag_ifzone=0;
    private int if_zone_flag;

    private List<OrderBean> lists = new ArrayList<>();
    private List<ExitBean> exitlists=new ArrayList<>();

    private List<Zonebean> zonebeanlists = new ArrayList<>();

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);
    private MyFragmentPageAdapter myAdapter;
    private android.support.v4.app.FragmentTransaction transaction;
    private android.support.v4.app.FragmentManager manager;
    private IntentIntegrator integrator;

    OrderFragment mOrderFragment;
    //繼續掃描得到的sku
    private String pass_sku;
    private String uid;
    private String brand;


    private String zondid;
    private String isurgent;

    private ItemUpdateLisener itemUpdateLisener;

    @Override
    protected void init() {
        setToolBarTitle("入倉處理中");
//        setRightTvTitle("提交");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_c;
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

        //從掃描中接收是否顯示急件flag
        flag_ifzone=getIntent().getIntExtra(IntentKeyUtil.KEY_IF_ZONE,0);
        SPUtils.put(getApplicationContext(),IntentKeyUtil.KEY_SP_IF_ZONE,flag_ifzone);

        //傳過來的sku
        pass_sku = getIntent().getStringExtra("sku");
//        uid= (String) SPUtils.get(this,"uid","");
//        brand =(String)SPUtils.get(this,"brand","");
        uid = getIntent().getStringExtra("uid");
        brand = getIntent().getStringExtra("brand");
        Log.d("zwx","收到Intent UID:"+uid+" Brand:"+brand);
        request(pass_sku, uid, brand);

//        tab_answer = (TabLayout) findViewById(R.id.tab_answer);
//        vp_answer = (ViewPager) findViewById(R.id.vp_answer);
        bt_erwei_contin_c = (Button) findViewById(R.id.bt_erwei_contin_c);
        bt_tiaoxing_contin_c = (Button) findViewById(R.id.bt_tiaoxing_contin_c);
        bt_erwei_contin_c.setOnClickListener(this);
        bt_tiaoxing_contin_c.setOnClickListener(this);
        
        initview();

//        initFragmentList();

//        myAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), mFragments, mTitleList);
//        vp_answer.setAdapter(myAdapter);
//        myAdapter.notifyDataSetChanged();
//        tab_answer.setTabMode(TabLayout.MODE_FIXED);
//        tab_answer.setupWithViewPager(vp_answer);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initview() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        mOrderFragment = new OrderFragment(lists);
        transaction.add(R.id.add_fragment, mOrderFragment);
        transaction.commit();
        itemUpdateLisener = (ItemUpdateLisener)mOrderFragment;
    }

//    private void initFragmentList() {
//        if (mTitleList.size() != 0) {
//            return;
//        }
//        mTitleList.add("入倉處理中");
//        mTitleList.add("出倉處理中");
//        mFragments.add(new OrderFragment(lists));
//        mFragments.add(new MoreoverFragment(exitlists));
//    }



    private void request(String passsku,String uid,String brand) {
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<String> call = ipService.getZoneInfo(passsku,(String) SPUtils.get(getApplicationContext(), IntentKeyUtil.KEY_AID, ""),uid,brand);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                    if(response.body().equals("-1")){
                        Intent broadcast = new Intent("android.intent.action.MEDICAL_BROADCAST");
                        broadcast.putExtra("zondid", "沒有該件商品!");
                        broadcast.putExtra("isurgent", "");
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcast);

                    }else if(response.body().startsWith("1")){
                        String strResponse=response.body().substring(1);
                        if(strResponse.equals("")){
                            strResponse="入庫(未分貨架)";
                        }else{
                            strResponse="入庫"+"("+strResponse+")";
                        }
                        Intent broadcast = new Intent("android.intent.action.MEDICAL_BROADCAST");
                        broadcast.putExtra("zondid", strResponse);
                        broadcast.putExtra("isurgent", "");
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcast);

                    } else {
                        Gson gson = new Gson();
                        List<Zonebean> zonelists = gson.fromJson(response.body().toString(), new TypeToken<List<Zonebean>>() {
                        }.getType());
                        zonebeanlists.clear();
                        if (zonelists != null) {
                            for (Zonebean zone : zonelists) {
                                zonebeanlists.add(zone);
                                if (zonebeanlists.get(0).getZone_id() != null) {
                                    zondid = zonebeanlists.get(0).getZone_id();
                                } else {
                                    zondid = "null";
                                }
                                isurgent = zonebeanlists.get(0).getIs_urgent();
                            }
                        } else {
                            zondid = "null";
                            isurgent = "null";
                        }
                        Intent broadcast = new Intent("android.intent.action.MEDICAL_BROADCAST");
                        broadcast.putExtra("zondid", zondid);
                        broadcast.putExtra("isurgent", isurgent);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcast);
                    }
                    itemUpdateLisener.ItemUpdate();
                }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_erwei_contin_c:
                RxPermissions.getInstance(getApplicationContext())
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                                    new ScannerUtil(AnswerActivity.this, integrator).selectTwoActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "沒有權限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.bt_tiaoxing_contin_c:
                RxPermissions.getInstance(getApplicationContext())
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                                    new ScannerUtil(AnswerActivity.this,integrator).selectOneActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "沒有權限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "掃碼取消！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
                pass_sku= result.getContents();
                requesTEasyWhCount(pass_sku);
              //  request(pass_sku);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        integrator=null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    private void requesTEasyWhCount(final String sku){
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<WarehouseBean> call = ipService.getEasyWhCount(sku);
        call.enqueue(new Callback<WarehouseBean>() {
            @Override
            public void onResponse(Call<WarehouseBean> call, Response<WarehouseBean> response) {
                if(response.body().getFlag()==0){
                    request(sku,"","");
                }else if(response.body().getFlag()==1){
                    if(response.body().getData()!=null){
                        request(sku,response.body().getData().get(0).getUid(),response.body().getData().get(0).getBrand());
                    }
                }else if(response.body().getFlag()==2){
                    finish();
                    Intent intent = new Intent(AnswerActivity.this, SelectWhActivity.class);
                    intent.putExtra("sku",sku);
                    intent.putExtra(IntentKeyUtil.KEY_IF_ZONE,if_zone_flag);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<WarehouseBean> call, Throwable t) {

            }
        });
    }
    //    private void request_alter(String id,int alter_outqty){
//        String url ="http://192.168.0.96:10000/";
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(HttpUtils.getclient())
//                .baseUrl(url)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
//        IServer ipService = retrofit.create(IServer.class);
//        Call<String> call = ipService.getOrderAlter(id,alter_outqty);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
////                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
////                Gson gson=new Gson();
////                List<OrderBean> orderlists=gson.fromJson(response.body().toString(),new TypeToken<List<OrderBean>>(){}.getType());
////                for(OrderBean order:orderlists){
////                    lists.add(order);
////                }
////                ((OrderFragment)mFragments.get(0)).refresh(lists);
//            }
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}
