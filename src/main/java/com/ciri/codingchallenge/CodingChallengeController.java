package com.ciri.codingchallenge;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
//@RequestMapping(path = "?region=EU" für den Filter
public class CodingChallengeController {

    public enum RegionShortcut {
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

        Objects.requireNonNull(ipRange);
        List<PrefixValue> prefixes = ipRange.getPrefixes();
        List<Ipv6PrefixValue> ipv6prefixes = ipRange.getIpv6_prefixes();
        List<AbstractPrefixValue> prefixValues = Stream.concat(prefixes.stream(), ipv6prefixes.stream())
                .collect(Collectors.toList());
        if (region!=null && !region.isEmpty()) {
            for (RegionShortcut regionShortcut : RegionShortcut.values()) {
                if (region.equalsIgnoreCase(regionShortcut.name())) {
                    if("ALL".equalsIgnoreCase(region)) {
                        return prefixValues.stream().map(Objects::toString).collect(Collectors.joining("\n"));
                    }
                    List<AbstractPrefixValue> result = prefixValues.stream().filter(r -> r.getRegion().toUpperCase(Locale.ROOT).startsWith(region)).collect(Collectors.toList());
                    return result.stream().map(Object::toString).collect(Collectors.joining("\n"));
                }
            }
        }
        return null;
    }
}
