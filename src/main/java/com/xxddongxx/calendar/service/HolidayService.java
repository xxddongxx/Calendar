package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.config.HolidayComponent;
import com.xxddongxx.calendar.mapper.HolidayMapper;
import com.xxddongxx.calendar.model.Holiday;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final Logger logger = LoggerFactory.getLogger(HolidayService.class);
    private final HolidayMapper holidayMapper;

    @Autowired
    private HolidayComponent holidayComponent;

    public void saveHoliday() throws UnsupportedEncodingException {
        String url = holidayComponent.getPath().toString();
        List<Holiday> holidayList = holidayComponent.getHoliday(url);
        if(!holidayList.isEmpty()){
            holidayMapper.insertHoliday(holidayList);
        }
    }

    public void saveHoliday(String year, String month) throws UnsupportedEncodingException {
        String url = holidayComponent.getPath(year, month).toString();
        List<Holiday> holidayList = holidayComponent.getHoliday(url);
        if(!holidayList.isEmpty()){
            holidayMapper.insertHoliday(holidayList);
        }
    }


}
