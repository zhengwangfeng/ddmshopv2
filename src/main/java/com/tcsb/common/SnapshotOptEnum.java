package com.tcsb.common;

public enum SnapshotOptEnum {
    ADD(0),UPDATE(2),DELETE(1);
    private int status;

    SnapshotOptEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
