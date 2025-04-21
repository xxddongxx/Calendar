package com.xxddongxx.calendar.controller;

import com.xxddongxx.calendar.dto.HolidayDto;
import com.xxddongxx.calendar.service.HolidayService;
import com.xxddongxx.calendar.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/openapi/holiday")
@RequiredArgsConstructor
public class HolidayController {
    private final Logger logger = LoggerFactory.getLogger(HolidayController.class);
    private final HolidayService holidayService;

    @PostMapping
    public ResponseEntity<ResponseMessage<HolidayDto>> fetchAndSaveHoliday(
            @RequestParam(value = "Year", required = false, defaultValue = "") String year,
            @RequestParam(value = "Month", required = false, defaultValue = "") String month
    ) throws UnsupportedEncodingException {
        holidayService.saveHoliday(year, month);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMessage.success("이번달 공휴일이 업데이트 됬습니다.",null));
    }
}
