package br.com.magazineluiza.storeprintml.models;

import android.net.nsd.NsdServiceInfo;

/**
 * Created by matheusmendes on 14/03/17.
 */

public class PrinterVO {

    private NsdServiceInfo serviceInfo;

    private String name;
    private String ip;

    public PrinterVO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public NsdServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(NsdServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }
}
