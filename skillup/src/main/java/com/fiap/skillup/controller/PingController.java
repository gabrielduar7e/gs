package com.fiap.skillup.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/ping")
public class PingController {

    private final MessageSource messageSource;

    public PingController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> ping() {
        String msg = messageSource.getMessage("api.ping", null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "message", msg,
                "timestamp", OffsetDateTime.now().toString()
        ));
    }
}
