package com.ciri.codingchallenge;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping(path = "?region=EU" für den Filter
public class CodingChallengeController {

    @GetMapping
    public List<String> showresults(){
        return List.of("This", "is", "a", "test-list");
    }
}
