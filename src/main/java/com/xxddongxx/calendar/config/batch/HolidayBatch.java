package com.xxddongxx.calendar.config.batch;

import com.xxddongxx.calendar.config.HolidayComponent;
import com.xxddongxx.calendar.mapper.HolidayMapper;
import com.xxddongxx.calendar.model.Holiday;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HolidayBatch {
    private final Logger logger = LoggerFactory.getLogger(HolidayBatch.class);
    private final HolidayComponent holidayComponent;
    private final HolidayMapper holidayMapper;

    // 매년 1월 1일 00시 실행 (cron: 초 분 시 일 월 요일)
    @Scheduled(cron = "0 0 0 1 1 *")
    public void fetchAndSaveHolidaysNewYear() {
        try {
            String year = String.valueOf(LocalDate.now().getYear());
            String url = holidayComponent.getPath(year, "").toString();
            List<Holiday> holidayList = holidayComponent.getHoliday(url);

            if(!holidayList.isEmpty()){
                holidayMapper.insertHoliday(holidayList);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("[공휴일 배치] URL 생성 오류: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("[공휴일 배치] 공휴일 저장 중 오류 발생: {}", e.getMessage(), e);
        }

    }

    // 해당 월 업데이트
    // 매월 1일 00시 실행 (cron: 초 분 시 일 월 요일)
    @Scheduled(cron = "0 0 0 1 * *")
    public void fetchAndSaveHolidays() {
        try {
            String url = holidayComponent.getPath().toString();
            List<Holiday> holidayList = holidayComponent.getHoliday(url);

            if(!holidayList.isEmpty()){
                holidayMapper.insertHoliday(holidayList);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("[공휴일 배치] URL 생성 오류: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("[공휴일 배치] 공휴일 저장 중 오류 발생: {}", e.getMessage(), e);
        }

    }
}
