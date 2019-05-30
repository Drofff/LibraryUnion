package com.appserve.Library.entity;

public enum Subscription {
    REGULAR, PRIMARY;

    public String getCurrentSubscription() {
        return this.name();
    }
}
