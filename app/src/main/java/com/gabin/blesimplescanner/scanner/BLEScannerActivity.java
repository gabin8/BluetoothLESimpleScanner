package com.gabin.blesimplescanner.scanner;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gabin.blesimplescanner.R;
import com.gabin.blesimplescanner.adapter.DevicesAdapter;
import com.gabin.blesimplescanner.data.Device;
import com.gabin.blesimplescanner.view.DividerItemDecoration;

/**
 * Activity for ble scanner. Displays the list of found BLE devices
 */

public class BLEScannerActivity extends AppCompatActivity implements BLEScannerContract.View {

    private static final int REQUEST_ENABLE_BT = 1000;

    private BLEScannerContract.ActionsListener mActionsListener;

    private DevicesAdapter mDevicesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                R.color.border_grey, R.dimen.divider_height));
        mDevicesAdapter = new DevicesAdapter();
        mRecyclerView.setAdapter(mDevicesAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.root_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark));

        mActionsListener = new BLEScannerPresenter(Injection.provideBLEScanner(this), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mActionsListener.isBluetoothEnabled() && mActionsListener.isBluetoothSupported()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }
        mActionsListener.startScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActionsListener.stopScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(@NonNull String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addDeviceToList(@NonNull Device device) {
        if (mDevicesAdapter != null) {
            mDevicesAdapter.addDevice(device);
        }
    }

    @Override
    public void clearDevices() {
        if (mDevicesAdapter != null) {
            mDevicesAdapter.clearDevices();
        }
    }
}
