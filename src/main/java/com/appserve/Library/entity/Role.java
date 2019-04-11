package com.appserve.Library.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

public enum Role implements GrantedAuthority {
    ADMIN, USER, LIBRARY;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
