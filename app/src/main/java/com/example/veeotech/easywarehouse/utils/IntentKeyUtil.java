package com.example.veeotech.easywarehouse.utils;

/**
 * Created by VeeoTech on 29/3/2018.
 */

public class IntentKeyUtil {
    public static final String KEY_ID = "ID";
    public static final String KEY_DNNO = "DNNO";
    public static final String KEY_SKU = "SKU";
    public static final String KEY_QTY = "QTY";
    public static final String KEY_OUTQTY = "OUTQTY";
    public static final String KEY_RECQTY="RECQTY";
    public static final String KEY_UID = "UID";
    public static final String KEY_BRAND="BRAND";

    public static final String KEY_AID = "AID";

    public static final String KEY_ZONEID = "ZONEID";
    public static final String KEY_ISURGENT = "ISURGENT";

    public static final String KEY_COMMIT_FLAG="COMMIT";

    public static int FLAG_COMMIT=0;

    public static final String KEY_IF_ZONE="IFZONE";

    public static final String KEY_SP_IF_ZONE="SPZONE";

    public volatile static String ZONEID="null";
    public volatile static String ISERGENT="null";

   // public static final String REQUEST_URI="http://easy-logistics.com.hk/api/";
    //public static final String REQUEST_URI="http://192.168.0.96:10000/EasyWarehouse/";
    public static final String REQUEST_URI="http://192.168.0.59:8080/warehouse/";
}
