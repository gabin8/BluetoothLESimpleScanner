package com.gabin.blesimplescanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabin.blesimplescanner.R;
import com.gabin.blesimplescanner.data.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter for displaying devices
 */
public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {

    private final List<Device> deviceList;

    public DevicesAdapter() {
        this(new ArrayList<Device>());
    }

    public DevicesAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View deviceView = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(deviceView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        if (pos == RecyclerView.NO_POSITION)
            return;
        Device device = deviceList.get(pos);
        holder.idTextView.setText(device.getId());
        holder.nameTextView.setText(device.getName());
    }

    public void addDevice(Device device) {
        deviceList.add(device);
        notifyItemInserted(deviceList.size() - 1);
    }

    public void clearDevices() {
        deviceList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView idTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.device_name);
            idTextView = itemView.findViewById(R.id.device_id);
        }
    }
}
