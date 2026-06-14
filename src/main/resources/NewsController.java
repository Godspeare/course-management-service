package com.aptech.courseservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.Random;

@RestController
public class NewsController {

    private final Random random = new Random();
    private final String[] companies = {"Apple", "Google", "Tesla", "Microsoft"};
    private final String[] events = {"stock drops 5%", "announces new product", "hires new CEO", "reports record profits"};

    @GetMapping(value = "/api/news/live", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLiveInfiniteNews() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> {
                    String company = companies[random.nextInt(companies.length)];
                    String event = events[random.nextInt(events.length)];
                    return "Alert " + (tick + 1) + ": " + company + " " + event;
                });
    }

    @GetMapping(value = "/api/news/weather")
    public Flux<String> getWeatherStream() {
        Flux<String> cities = Flux.just("London", "New York", "Tokyo", "Lagos");
        Flux<Integer> temps = Flux.just(15, 22, 28, 33);
        return Flux.zip(cities, temps).map(t -> t.getT1() + ": " + t.getT2() + "°C");
    }
}