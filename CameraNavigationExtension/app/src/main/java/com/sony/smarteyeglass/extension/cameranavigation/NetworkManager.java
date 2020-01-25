package com.sony.smarteyeglass.extension.cameranavigation;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import org.zeromq.ZMQ;

import java.util.ArrayList;

// TODO: Deal will offline case

public class NetworkManager extends Thread {
    private DhcpInfo dhcpInfo;
    private WifiManager wifiManager;

    public NetworkManager(Context context) {
        wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        dhcpInfo = wifiManager.getDhcpInfo();
    }

    public void start() {
        Thread findDevices = new NetworkSearchThread(dhcpInfo);
        findDevices.start();
    }
}