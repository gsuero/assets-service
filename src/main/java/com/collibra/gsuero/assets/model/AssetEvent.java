package com.collibra.gsuero.assets.model;

import java.time.Instant;

public class AssetEvent {
    private Operation operation;
    private Asset data;
    private Instant timestamp;

    public AssetEvent() {
        super();
    }

    public AssetEvent(Operation operation, Asset data) {
        this.operation = operation;
        this.data = data;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Asset getData() {
        return data;
    }

    public void setData(Asset data) {
        this.data = data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
