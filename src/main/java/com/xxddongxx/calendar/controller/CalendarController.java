package com.xxddongxx.calendar.controller;

import com.xxddongxx.calendar.dto.CalendarCreateDto;
import com.xxddongxx.calendar.dto.CalendarDto;
import com.xxddongxx.calendar.dto.CalendarUpdateDto;
import com.xxddongxx.calendar.service.CalendarService;
import com.xxddongxx.calendar.util.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
    private final CalendarService calendarService;

    @PostMapping
    @Operation(summary = "일정 등록", description = "새로운 일정을 등록합니다.")
    public ResponseEntity<ResponseMessage<Void>> createCalendar(@RequestBody @Valid CalendarCreateDto calendarCreateDto){
        logger.info("create calendar!");
        calendarService.createCalendar(calendarCreateDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMessage.success());
    }

    @GetMapping("/{id}")
    @Operation(summary = "일정 상세 조회", description = "등록된 일정을 상세조회합니다.")
    public ResponseEntity<ResponseMessage<CalendarDto>> findCalendarOne(@PathVariable("id") Long id){
        logger.info("find Calendar One");
        CalendarDto calendarDto = calendarService.findCalendarOne(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMessage.success(calendarDto));
    }

    @GetMapping("/day")
    @Operation(summary = "일별 일정 조회", description = "일별 등록된 일정")
    public ResponseEntity<ResponseMessage<List<CalendarDto>>> findCalendarByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        logger.info("find calendar by day");
        List<CalendarDto> calendarDtoList = calendarService.findCalendarByDay(date);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMessage.success(calendarDtoList));
    }


    @PutMapping("/{id}")
    @Operation(summary = "일정 수정", description = "등록된 일정을 수정합니다.")
    public ResponseEntity<ResponseMessage<CalendarDto>> updateCalendar(
            @PathVariable("id") Long id,
            @RequestBody @Valid CalendarUpdateDto calendarUpdateDto
    ) {
        logger.info("update Calendar");

        CalendarDto calendarDto = calendarService.updateCalendar(id, calendarUpdateDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMessage.success(calendarDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "일정 삭제", description = "등록된 일정 삭제")
    public ResponseEntity<ResponseMessage<Void>> deleteCalendar(@PathVariable("id") Long id){
        logger.info("delete Calendar");

        calendarService.deleteCalendar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMessage.success());
    }
}
