package com.example.veeotech.easywarehouse.bean;

import java.util.List;

/**
 * Created by VeeoTech on 2018/5/17.
 */

public class WarehouseBean {

    /**
     * flag : 1
     * data : [{"sku":"8809392871289","uid":"muyun","brand":"tok"}]
     */

    private int flag;
    private List<DataBean> data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sku : 8809392871289
         * uid : muyun
         * brand : tok
         */

        private String sku;
        private String uid;
        private String brand;

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
