package com.xxddongxx.calendar.mapper;

import com.xxddongxx.calendar.model.Calendar;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CalendarMapper {
    void insertCalendar(Calendar calendar);
    Optional<Calendar> findById(Long id);
    int updateCalendar(Calendar calendar);
}
