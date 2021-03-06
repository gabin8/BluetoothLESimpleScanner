package com.gabin.blesimplescanner.scanner;

import android.content.Context;

/**
 * Enables injection of production implementation for {@link BLEScannerPresenter}
 */
public class Injection {

    /**
     * Provides BLE scanner instance
     * @param context
     * @return BLEScanner implementation
     */
    public static BLEScanner provideBLEScanner(Context context) {
        return new BLEScannerImpl(context);
    }

}
