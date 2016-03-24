package com.gabin.blesimplescanner.scanner;

import android.content.Context;

/**
 * Enables injection of production implementation for {@link BLEScannerPresenter}
 */
public class Injection {

    public static BLEScanner provideBLEScanner(Context context) {
        return new BLEScannerImpl(context);
    }

}
