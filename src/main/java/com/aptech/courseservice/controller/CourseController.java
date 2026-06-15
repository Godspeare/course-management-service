package com.aptech.courseservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import java.time.Duration;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @GetMapping(value = "/live", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLiveNews() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Live Course Update #" + sequence + " - Streaming Successfully!");
    }
}
