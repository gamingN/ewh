package com.example.veeotech.easywarehouse.bean;

/**
 * Created by VeeoTech on 4/4/2018.
 */

public class Zonebean {

    /**
     * zone_id :  en
     * is_urgent : 1
     */

    private String zone_id;
    private String is_urgent;

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    public void setIs_urgent(String is_urgent) {
        this.is_urgent = is_urgent;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getIs_urgent() {
        return is_urgent;
    }
}
