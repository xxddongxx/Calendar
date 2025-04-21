package com.xxddongxx.calendar.mapper;

import com.xxddongxx.calendar.model.Holiday;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HolidayMapper {
    void insertHoliday(List<Holiday> listParam);
}
