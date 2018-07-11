package com.example.veeotech.easywarehouse.bean;

/**
 * Created by VeeoTech on 8/4/2018.
 */

public class OutBean {

    /**
     * sku : 812803013243
     * qty : 0
     */

    private String sku;
    private int qty;

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSku() {
        return sku;
    }

    public int getQty() {
        return qty;
    }
}
