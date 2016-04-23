package com.gabin.blesimplescanner.scanner;

import android.content.Context;

/**
 * Enables injection of mock implementations for {@link BLEScannerPresenter} at compile time.
 */
public class Injection {

    /**
     * Provides fake BLE scanner instance
     * @param context
     * @return BLEScanner fake implementation
     */
    public static BLEScanner provideBLEScanner(Context context) {
        return new FakeBLEScannerImpl();
    }
}
