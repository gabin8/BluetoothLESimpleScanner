package com.gabin.blesimplescanner.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Model class for device
 */
public final class Device {

    @NonNull
    private String mId;

    @Nullable
    private String mName;

    public Device(@NonNull String id, @Nullable String name) {
        this.mId = id;
        this.mName = name;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String mId) {
        this.mId = mId;
    }

    @Nullable
    public String getName() {
        return mName;
    }

    public void setName(@Nullable String mName) {
        this.mName = mName;
    }
}
