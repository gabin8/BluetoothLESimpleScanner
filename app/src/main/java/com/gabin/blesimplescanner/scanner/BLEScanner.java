package com.gabin.blesimplescanner.scanner;

import com.gabin.blesimplescanner.data.Device;

/**
 * BLEScanner provides all necessary methods for discovering BLE devices
 */
public interface BLEScanner {

    void startScan();

    void stopScan();

    boolean isBTSupported();

    boolean isBTEnabled();

    void setDeviceDiscoveredCallback(DeviceDiscoveredCallback callback);

    interface DeviceDiscoveredCallback {

        void onDeviceDiscovered(Device device);
    }

}
