package com.appserve.Library.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlockedIp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ip;

    private String reason;

    public BlockedIp() {}

    public BlockedIp(Long id, String ip, String reason) {
        this.id = id;
        this.ip = ip;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
