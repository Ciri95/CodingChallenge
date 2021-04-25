package com.ciri.codingchallenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasouceObject {

    private List<PrefixValue> prefixes;
    private List<Ipv6PrefixValue> ipv6_prefixes;

    public DatasouceObject(){

    }

    public List<PrefixValue> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(List<PrefixValue> prefixes) {
        this.prefixes = prefixes;
    }

    public List<Ipv6PrefixValue> getIpv6_prefixes() {
        return ipv6_prefixes;
    }

    public void setIpv6_prefixes(List<Ipv6PrefixValue> ipv6_prefixes) {
        this.ipv6_prefixes = ipv6_prefixes;
    }

    @Override
    public String toString() {
        return "Test{" +
                "prefixes='" + prefixes +
                ", ipv6_prefixes=" + ipv6_prefixes +
                '}';
    }

}
