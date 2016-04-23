package com.gabin.blesimplescanner.scanner;

/**
 * Fake implementation of {@link BLEScanner}
 */

public class FakeBLEScannerImpl implements BLEScanner {

    boolean isBLESupported;
    boolean isBTEnabled;

    @Override
    public void startScan() {

    }

    @Override
    public void stopScan() {

    }

    @Override
    public boolean isBTSupported() {
        return isBLESupported;
    }

    @Override
    public boolean isBTEnabled() {
        return isBTEnabled;
    }

    @Override
    public void setDeviceDiscoveredCallback(DeviceDiscoveredCallback callback) {

    }
}
