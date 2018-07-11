package com.example.veeotech.easywarehouse.bean;

/**
 * Created by VeeoTech on 8/4/2018.
 */

public class OutBean {
    /**
     * sku : ABC$
     * qty : 108
     * uid : muyun
     * brand : IBD
     */

    private String sku;
    private int qty;
    private String uid;
    private String brand;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
