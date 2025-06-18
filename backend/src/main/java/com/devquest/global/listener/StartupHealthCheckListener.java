package com.devquest.global.listener;

import java.time.Duration;
import java.time.Instant;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class StartupHealthCheckListener implements ApplicationListener<ApplicationReadyEvent> {
    Instant startTime = Instant.now();

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        checkStartupTime();
    }

    // Spring Boot 시작 시간
    private void checkStartupTime() {
        Instant endTime = Instant.now();
        long timeTaken = Duration.between(startTime, endTime).toMillis();
        System.out.println("[스프링부트 정상실행]: " + timeTaken + " 밀리초");
    }
}
