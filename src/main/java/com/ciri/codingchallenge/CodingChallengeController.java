package com.ciri.codingchallenge;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
//@RequestMapping(path = "?region=EU" für den Filter
public class CodingChallengeController {

    enum RegionShortcut {
        EU,
        US,
        AP,
        CN,
        SA,
        AF,
        CA,
        ALL
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String showresults(RestTemplate restTemplate, @RequestParam(name = "region") String region) {
        IpRange ipRange = restTemplate.getForObject(
                "https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class);

        assert ipRange != null;
        List<PrefixValue> prefixes = ipRange.getPrefixes();
        List<Ipv6PrefixValue> ipv6prefixes = ipRange.getIpv6_prefixes();
        if (!region.isEmpty()) {
            for (RegionShortcut regionShortcut : RegionShortcut.values()) {
                if (region.equals(regionShortcut.name()) && !region.equals("ALL")) {
                    List<AbstractPrefixValue> result = new ArrayList<>();
                    result.addAll(prefixes.stream().filter(r -> r.getRegion().toUpperCase(Locale.ROOT).startsWith(region)).collect(Collectors.toList()));
                    result.addAll(ipv6prefixes.stream().filter(r -> r.getRegion().toUpperCase(Locale.ROOT).startsWith(region)).collect(Collectors.toList()));
                    return result.stream().map(Object::toString).collect(Collectors.joining("\n"));
                } else if (region.equals(regionShortcut.name())) {
                    //TODO single line for each result value
                    return ipRange.toString();
                }
            }
        }
        return null;
    }
}
