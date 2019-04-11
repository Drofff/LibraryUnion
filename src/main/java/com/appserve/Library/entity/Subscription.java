package com.appserve.Library.entity;

import javax.persistence.Entity;

public enum Subscription {
    REGULAR, PRIMARY;

    public String getCurrentSubscription() {
        return this.name();
    }
}
