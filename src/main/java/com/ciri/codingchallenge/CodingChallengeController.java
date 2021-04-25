package com.ciri.codingchallenge;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
//@RequestMapping(path = "?region=EU" für den Filter
public class CodingChallengeController {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping
    public List<String> showresults(RestTemplate restTemplate){

        DatasouceObject datasouceObject = restTemplate.getForObject(
                "https://ip-ranges.amazonaws.com/ip-ranges.json", DatasouceObject.class);
        assert datasouceObject != null;
        return List.of(datasouceObject.toString());
    }
}
