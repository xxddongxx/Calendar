package com.xxddongxx.calendar.mapper;

import com.xxddongxx.calendar.model.Calendar;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalendarMapper {
    void insertCalendar(Calendar calendar);
}
