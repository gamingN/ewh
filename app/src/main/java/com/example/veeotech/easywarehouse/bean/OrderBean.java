package com.example.veeotech.easywarehouse.bean;

/**
 * Created by VeeoTech on 2018/3/27.
 */

public class OrderBean {

    /**
     * id : 3
     * dn_no : zyc2016082377
     * sku : 812803013244
     * qty : 10
     * out_qty : 10
     */

    private String id;
    private String dn_no;
    private String sku;
    private String qty;
    private String rec_qty;
    private String order_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date=order_date;
    }

    public String getDn_no() {
        return dn_no;
    }

    public void setDn_no(String dn_no) {
        this.dn_no = dn_no;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRec_qty() {
        return rec_qty;
    }

    public void setRec_qty(String rec_qty) {
        this.rec_qty = rec_qty;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "dn_no='" + dn_no + '\'' +
                ", sku='" + sku + '\'' +
                ", qty='" + qty + '\'' +
                ", rec_qty='" + rec_qty + '\'' +
                '}';
    }
}
