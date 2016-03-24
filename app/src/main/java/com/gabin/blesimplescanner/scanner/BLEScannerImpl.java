package com.gabin.blesimplescanner.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.support.annotation.NonNull;

import com.gabin.blesimplescanner.data.Device;

/**
 * Implementation of BLEScanner
 */
public class BLEScannerImpl implements BLEScanner {

    private final BluetoothAdapter bluetoothAdapter;

    private BluetoothLeScanner bluetoothLeScanner;

    private BLEScanner.DeviceDiscoveredCallback deviceDiscoveredCallback;

    private boolean isBTSupported = true;

    private boolean isScanning = false;

    public BLEScannerImpl(@NonNull Context context) {

        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            isBTSupported = false;
            return;
        }

        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    @Override
    public boolean isBTEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    @Override
    public boolean isBTSupported() {
        return isBTSupported;
    }

    @Override
    public void setDeviceDiscoveredCallback(DeviceDiscoveredCallback deviceDiscoveredCallback) {
        this.deviceDiscoveredCallback = deviceDiscoveredCallback;
    }

    @Override
    public void startScan() {
        if (!isBTSupported || !isBTEnabled())
            return;
        if (bluetoothAdapter != null && bluetoothLeScanner == null)
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        isScanning = true;
        bluetoothLeScanner.startScan(mLeScanCallback);
    }

    @Override
    public void stopScan() {
        if (!isScanning)
            return;
        isScanning = false;
        bluetoothLeScanner.stopScan(mLeScanCallback);
    }

    // Device scan callback
    private ScanCallback mLeScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice btDevice = result.getDevice();
            String deviceName = btDevice.getName();

            if (deviceName == null || deviceName.isEmpty()) {
                deviceName = "Unknown device";
            }

            String deviceId = btDevice.getAddress();

            Device device = new Device(deviceId, deviceName);
            if (deviceDiscoveredCallback != null) {
                deviceDiscoveredCallback.onDeviceDiscovered(device);
            }
        }
    };
}
