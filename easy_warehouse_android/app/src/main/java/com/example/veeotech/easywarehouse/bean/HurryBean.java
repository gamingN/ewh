package com.example.veeotech.easywarehouse.bean;

/**
 * Created by VeeoTech on 9/4/2018.
 */

public class HurryBean {

    /**
     * dn_no : zyc2016082377
     * sku : 812803013244
     * qty : 5
     * out_qty : 1
     */

    private String dn_no;
    private String sku;
    private String qty;
    private String out_qty;

    public void setDn_no(String dn_no) {
        this.dn_no = dn_no;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setOut_qty(String out_qty) {
        this.out_qty = out_qty;
    }

    public String getDn_no() {
        return dn_no;
    }

    public String getSku() {
        return sku;
    }

    public String getQty() {
        return qty;
    }

    public String getOut_qty() {
        return out_qty;
    }
}
