package com.gabin.blesimplescanner.scanner;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.gabin.blesimplescanner.data.Device;

/**
 * Listens to user actions from the UI ({@link BLEScannerActivity}),
 * retrieves the data and updates the UI as required.
 */

public class BLEScannerPresenter implements BLEScannerContract.ActionsListener, BLEScanner.DeviceDiscoveredCallback {

    public static final int SCAN_TIMEOUT_MILLIS = 30_000;

    @NonNull
    private final BLEScanner mBleScanner;

    @NonNull
    private final BLEScannerContract.View mBleScannerView;

    private Handler timeoutHandler;

    private Runnable timeoutRunnable;

    public BLEScannerPresenter(@NonNull BLEScanner bleScanner,
                               @NonNull BLEScannerContract.View bleScannerView) {
        mBleScanner = bleScanner;
        mBleScannerView = bleScannerView;
        if (!mBleScanner.isBTSupported()) {
            bleScannerView.showError("BLE is not supported");
            return;
        }
        mBleScanner.setDeviceDiscoveredCallback(this);
        timeoutHandler = new Handler();
        timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                stopScan();
            }
        };
    }

    @Override
    public boolean isBluetoothEnabled() {
        return mBleScanner.isBTEnabled();
    }

    @Override
    public void startScan() {
        mBleScannerView.showProgress();
        mBleScannerView.clearDevices();
        mBleScanner.startScan();
        timeoutHandler.postDelayed(timeoutRunnable, SCAN_TIMEOUT_MILLIS);
    }

    @Override
    public void stopScan() {
        mBleScanner.stopScan();
        mBleScannerView.hideProgress();
        timeoutHandler.removeCallbacks(timeoutRunnable);
    }

    @Override
    public boolean isBluetoothSupported() {
        return mBleScanner.isBTSupported();
    }

    @Override
    public void onDeviceDiscovered(Device device) {
        mBleScannerView.addDeviceToList(device);
    }
}
