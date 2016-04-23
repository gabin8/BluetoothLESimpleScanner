package com.gabin.blesimplescanner.scanner;

import android.os.Handler;

import com.gabin.blesimplescanner.data.Device;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test for BLEScanner presenter
 */
@RunWith(MockitoJUnitRunner.class)
public class BLEScannerPresenterTest {

    private BLEScannerPresenter mBleScannerPresenter;
    private FakeBLEScannerImpl mFakeBLEScanner;

    @Mock
    private BLEScannerContract.View mBLEScannerView;

    @Before
    public void createBleScannerPresenter() {
        MockitoAnnotations.initMocks(this);

        mFakeBLEScanner = new FakeBLEScannerImpl();
        mBleScannerPresenter = new BLEScannerPresenter(mFakeBLEScanner, mBLEScannerView);
    }

    @Test
    public void scanAndVerifyViewTest() {
        final Handler handler = mock(Handler.class);
        doReturn(false).when(handler).postDelayed(any(Runnable.class), anyLong());
        doNothing().when(handler).removeCallbacks(any(Runnable.class));

        mBleScannerPresenter.timeoutHandler = handler;

        mBleScannerPresenter.startScan();
        verify(mBLEScannerView).showProgress();
        mBleScannerPresenter.stopScan();
        verify(mBLEScannerView).hideProgress();
    }

    @Test
    public void onDeviceDiscoveredTest() {
        List<Device> devices = new LinkedList<>();
        devices.add(new Device("00:11:22:33:44", "Device 1"));
        devices.add(new Device("01:11:22:33:44", "Device 2"));
        devices.add(new Device("02:11:22:33:44", "Device 3"));
        for (Device device : devices) {
            mBleScannerPresenter.onDeviceDiscovered(device);
        }
        verify(mBLEScannerView, times(3)).addDeviceToList(any(Device.class));
    }

    @Test
    public void verifyBLENotSupported() {
        mFakeBLEScanner.isBLESupported = false;
        verify(mBLEScannerView).showError(anyString());
    }
}
