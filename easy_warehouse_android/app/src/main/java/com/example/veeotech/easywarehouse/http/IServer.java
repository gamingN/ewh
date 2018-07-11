package com.example.veeotech.easywarehouse.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by VeeoTech on 2018/3/24.
 */

public interface IServer {
//    @Headers({
//            "Accept-Encoding: application/json"
//    })
//    @GET("kitest.php")
//    Call<OrderBean> getOrder(@Query("sku") String sku);

//    @GET("kitest.php?sku=812803013244")
//    Call<OrderBean> getOrderTest();


    @GET("loginEasyWh.php")
    Call<String> getloginaid(@Query("uid") String uid, @Query("pw") String pw);

    @GET("alterEasyWh.php")
    Call<String> getOrderAlter(@Query("id") String id, @Query("recqty") int recqty);

    @GET("requireEasyWh.php")
    Call<String> getorderInfo(@Query("aid") String aid);

    @GET("enterEasyWh.php")
    Call<String> getZoneInfo(@Query("sku") String sku, @Query("aid") String aid);

    @GET("exitRequireEasyWh.php")
    Call<String> getExitInfo(@Query("aid") String aid);

    @GET("exitInfoEasyWh.php")
    Call<String> getOutInfo(@Query("aid") String aid);

    @GET("exitEasyWh.php")
    Call<String> getUpdateOut(@Query("sku") String sku,@Query("aid") String aid);

    @GET("commitEasyWh.php")
    Call<String> getCommitUpdate(@Query("aid") String aid);

    @GET("hurryEasyWh.php")
    Call<String> getHurryInfo();

}
