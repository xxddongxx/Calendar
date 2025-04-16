package com.xxddongxx.calendar.controller;

import com.xxddongxx.calendar.dto.CalendarCreateDto;
import com.xxddongxx.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
    private final CalendarService calendarService;

    @PostMapping
    @Operation(summary = "일정 등록", description = "새로운 일정을 등록합니다.")
    public ResponseEntity<Void> createCalendar(@RequestBody @Valid CalendarCreateDto calendarCreateDto){
        logger.info("create calendar!");
        calendarService.createCalendar(calendarCreateDto);
        return ResponseEntity.ok().build();
    }
}
