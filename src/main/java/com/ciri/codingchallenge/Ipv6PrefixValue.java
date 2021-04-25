package com.ciri.codingchallenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ipv6PrefixValue extends AbstractPrefixValue {
    private String ipv6_prefix;

    public Ipv6PrefixValue() {

    }

    public String getIpv6_prefix() {
        return ipv6_prefix;
    }

    public void setIpv6_prefix(String ipv6_prefix) {
        this.ipv6_prefix = ipv6_prefix;
    }


    @Override
    public String toString() {
        return "Ipv6PrefixValue{" +
                "ipv6_prefix=" + ipv6_prefix +
                ", region=" + getRegion() +
                ", service=" + getService() +
                ", network_border_group='" + getNetwork_border_group() + '\'' +
                '}';
    }
}
