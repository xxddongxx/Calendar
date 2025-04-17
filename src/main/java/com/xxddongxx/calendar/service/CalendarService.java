package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.config.exception.CustomException;
import com.xxddongxx.calendar.dto.CalendarCreateDto;
import com.xxddongxx.calendar.dto.CalendarDto;
import com.xxddongxx.calendar.dto.CalendarUpdateDto;
import com.xxddongxx.calendar.mapper.CalendarMapper;
import com.xxddongxx.calendar.model.Calendar;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private static final Logger logger = LoggerFactory.getLogger(CalendarService.class);
    private final CalendarMapper calendarMapper;
    private final Long USER_ID = 1L;

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

    @Transactional(readOnly = true)
    public List<CalendarDto> findCalendarByDay(LocalDate date){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", USER_ID);
        paramMap.put("date", date);

        List<Calendar> calendarList = calendarMapper.findCalendarByDay(paramMap);

        return calendarList.stream().map( calendar -> new CalendarDto().toDto(calendar)).collect(Collectors.toList());
    }

    @Transactional
    public CalendarDto updateCalendar(Long id, CalendarUpdateDto calendarUpdateDto){
        Calendar calendar = this.findById(id);

        if(!calendarUpdateDto.getId().equals(calendar.getId())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }

        if(!calendar.getUserId().equals(calendarUpdateDto.getUserId())){
            throw new IllegalArgumentException("작성자가 아니므로 수정이 불가능합니다.");
        }

        if(calendarUpdateDto.getTitle().isEmpty() || calendarUpdateDto.getTitle() == null){
            throw new IllegalArgumentException("제목은 필수입니다.");
        }

        if(calendarUpdateDto.getStartAt().isAfter(calendarUpdateDto.getEndAt())){
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 이전이어야 합니다.");
        }

        if(calendarUpdateDto.getUserId() == null){
            throw new IllegalArgumentException("사용자 정보가 필요합니다.");
        }

        int result = calendarMapper.updateCalendar(calendarUpdateDto.toModel());

        if(result == 0){
            throw new CustomException(HttpStatus.BAD_REQUEST, "일정 수정에 실패했습니다.");
        }

        return new CalendarDto().toDto(this.findById(id));
    }

    @Transactional
    public void deleteCalendar(Long id){
        Calendar calendar = this.findById(id);

        if(Integer.parseInt(calendar.getId().toString()) != id){
            throw new CustomException(HttpStatus.NOT_FOUND, "등록된 일정이 없습니다.");
        }

        calendarMapper.deleteCalendar(id);
    }
}
