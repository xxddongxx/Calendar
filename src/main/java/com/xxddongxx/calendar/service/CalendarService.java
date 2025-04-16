package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.config.exception.CustomException;
import com.xxddongxx.calendar.dto.CalendarCreateDto;
import com.xxddongxx.calendar.dto.CalendarDto;
import com.xxddongxx.calendar.mapper.CalendarMapper;
import com.xxddongxx.calendar.model.Calendar;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private static final Logger logger = LoggerFactory.getLogger(CalendarService.class);
    private final CalendarMapper calendarMapper;

    @Transactional
    public void createCalendar(CalendarCreateDto calendarCreateDto){
        if(calendarCreateDto.getTitle().isEmpty() || calendarCreateDto.getTitle() == null){
            throw new IllegalArgumentException("제목은 필수입니다.");
        }

        if(calendarCreateDto.getStartAt().isAfter(calendarCreateDto.getEndAt())){
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 이전이어야 합니다.");
        }

        if(calendarCreateDto.getUserId() == null){
            throw new IllegalArgumentException("사용자 정보가 필요합니다.");
        }

        calendarMapper.insertCalendar(calendarCreateDto.toModel());
    }

    @Transactional(readOnly = true)
    public Calendar findById(Long id) {
        return calendarMapper.findById(id).orElseThrow( () ->
                new CustomException(HttpStatus.NOT_FOUND, "등록된 일정이 없습니다."));
    }

    @Transactional(readOnly = true)
    public CalendarDto findCalendarOne(Long id){
        Calendar calendar = this.findById(id);

        return new CalendarDto().toDto(calendar);
    }

}
