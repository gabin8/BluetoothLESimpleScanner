package com.gabin.blesimplescanner.scanner;

import android.support.annotation.NonNull;

import com.gabin.blesimplescanner.data.Device;

/**
 * Interface that specifies the contract between the view and the presenter.
 */
public interface BLEScannerContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showError(@NonNull String error);

        void addDeviceToList(@NonNull Device device);

        void clearDevices();
    }

    interface ActionsListener {

        void startScan();

        void stopScan();

        boolean isBluetoothSupported();

        boolean isBluetoothEnabled();
    }
}