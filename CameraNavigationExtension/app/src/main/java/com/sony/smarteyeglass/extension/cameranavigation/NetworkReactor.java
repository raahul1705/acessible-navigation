package com.sony.smarteyeglass.extension.cameranavigation;

import java.util.ArrayList;

public class NetworkReactor {
    private static ArrayList<String> serverList;

    // TODO: Research structure of server list
    public static void initialize() {
        serverList = new ArrayList<>();
    }

    public static void addConnection(String connection) {
        serverList.add(connection);
    }

    public static void removeConnection(String connection) {
        serverList.remove(connection);
    }

    public static String getConnection(int index) {
        return serverList.get(index);
    }


}
