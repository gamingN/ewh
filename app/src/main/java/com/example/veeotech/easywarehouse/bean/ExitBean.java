package com.example.veeotech.easywarehouse.bean;

/**
 * Created by VeeoTech on 8/4/2018.
 */

public class ExitBean {

    /**
     * id : 6
     * dn_no : zyc2016082377
     * sku : 812803013244
     * qty : 5
     * out_qty : 4
     */

    private String id;
    private String dn_no;
    private String sku;
    private String qty;
    private String out_qty;
    private String order_date;

    public void setOrder_date(String order_date){
        this.order_date=order_date;
    }

    public String getOrder_date(){
        return order_date;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getId() {
        return id;
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
