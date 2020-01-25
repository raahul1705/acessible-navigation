package com.sony.smarteyeglass.extension.cameranavigation;

import android.util.Log;
import android.net.DhcpInfo;

import com.sonyericsson.extras.liveware.aef.control.Control;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class NetworkSearchThread extends Thread {
    private DhcpInfo dhcpInfo;
    private InetAddress host;

    public NetworkSearchThread(DhcpInfo dhcpInfo) {
        this.dhcpInfo = dhcpInfo;
    }

    public void run() {
        if (dhcpInfo != null) {
            try {
                host = InetAddress.getByName(convertIntToIp(dhcpInfo.dns1));
                byte[] ip = host.getAddress();

                for (int i = 1; i < 255; i++) {
                    ip[3] = (byte) i;
                    InetAddress address = InetAddress.getByAddress(ip);

                    if (pingMachinePort(address, 9003, 10)) {
                        Log.e(Constants.NETWORK_MANAGER_TAG, "Found machine at " + address);
//                        NetworkManager.connections.add(address.toString());
                    }

//                    else if (!address.getHostAddress().equals(address.getHostName())) {
//                        String msg = "Found inactive machine: " + address;
//                        Log.e(Constants.NETWORK_MANAGER_TAG, msg);
//                    }
                }
            }
            catch (UnknownHostException e) {
                Log.e(Constants.NETWORK_MANAGER_TAG, "Cannot find network host");
            }
        }

//        NetworkManager.connections = connections;
    }

    private boolean pingMachinePort(InetAddress address, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(address, port), timeout);
            socket.close();
            return true;
        }
        catch (ConnectException ce) {
            Log.v(Constants.NETWORK_MANAGER_TAG, "Cannot connect to " + address);
            return false;
        }
        catch (Exception e) {
            Log.v(Constants.NETWORK_MANAGER_TAG, "No machine at " + address);
            return false;
        }
    }

    public String convertIntToIp(int i) {
        return (i & 0xFF) + "."
                + ((i >> 8) & 0xFF) + "."
                + ((i >> 16) & 0xFF) + "."
                + ((i >> 24) & 0xFF);
    }
}
