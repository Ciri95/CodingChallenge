package com.ciri.codingchallenge;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
//@RequestMapping(path = "?region=EU" für den Filter
public class CodingChallengeController {

    enum Region {
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

    @GetMapping
    public List<String> showresults(RestTemplate restTemplate, @RequestParam(name = "region") String region) {
        DatasouceObject datasouceObject = restTemplate.getForObject(
                "https://ip-ranges.amazonaws.com/ip-ranges.json", DatasouceObject.class);

        assert datasouceObject != null;
        List<PrefixValue> prefixes = datasouceObject.getPrefixes();
        List<Ipv6PrefixValue> ipv6prefixes = datasouceObject.getIpv6_prefixes();
        if (!region.isEmpty()) {
            for (Region regionShortcut : Region.values()) {
                if (region.equals(regionShortcut.name()) && !region.equals("ALL")) {
                    List<AbstractPrefixValue> result = new ArrayList<>();
                    result.addAll(prefixes.stream().filter(r -> r.getRegion().toUpperCase(Locale.ROOT).startsWith(region)).collect(Collectors.toList()));
                    result.addAll(ipv6prefixes.stream().filter(r -> r.getRegion().toUpperCase(Locale.ROOT).startsWith(region)).collect(Collectors.toList()));
                    return result.stream().map(Object::toString).collect(Collectors.toList());
                } else if (region.equals(regionShortcut.name())) {
                    return List.of(datasouceObject.toString());
                }
            }
        }
        return Collections.emptyList();
    }
}
