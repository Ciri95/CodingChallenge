package com.ciri.codingchallenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrefixValue extends AbstractPrefixValue {
    private String ip_prefix;

    public PrefixValue() {

    }

    public String getIp_prefix() {
        return ip_prefix;
    }

    public void setIp_prefix(String ip_prefix) {
        this.ip_prefix = ip_prefix;
    }


    @Override
    public String toString() {
        return "{" +
                "ip_prefix=" + ip_prefix +
                ", region=" + getRegion() +
                ", service=" + getService() +
                ", network_border_group=" + getNetwork_border_group() +
                '}';
    }

}
