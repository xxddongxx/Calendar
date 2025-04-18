package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.dto.EventShareDto;
import com.xxddongxx.calendar.mapper.EventShareMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventShareService {
    private final Logger logger = LoggerFactory.getLogger(EventShareService.class);
    private final EventShareMapper eventShareMapper;

    public void createEventShare(EventShareDto eventShareDto) {
        eventShareMapper.createEventShare(eventShareDto.toModel());
    }
}
