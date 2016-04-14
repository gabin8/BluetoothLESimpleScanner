package com.gabin.blesimplescanner.scanner;

/**
 * Fake implementation of {@link BLEScanner}
 */

public class FakeBLEScannerImpl implements BLEScanner {

    @Override
    public void startScan() {

    }

    @Override
    public void stopScan() {

    }

    @Override
    public boolean isBTSupported() {
        return true;
    }

    @Override
    public boolean isBTEnabled() {
        return true;
    }

    @Override
    public void setDeviceDiscoveredCallback(DeviceDiscoveredCallback callback) {

    }
}
