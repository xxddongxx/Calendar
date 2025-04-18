package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.dto.EventShareDto;
import com.xxddongxx.calendar.mapper.EventShareMapper;
import com.xxddongxx.calendar.model.EventShare;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventShareServiceTest {
    @InjectMocks
    private EventShareService eventShareService;

    @Mock
    private EventShareMapper eventShareMapper;

    @DisplayName("성공 - 일정 공유 사용자")
    @Test
    void createEventShareSuccess() {
        // given
        Long eventId = 1L;
        List<Long> accountList = List.of(2L, 3L);
        EventShareDto eventShareDto = EventShareDto.builder()
                .eventId(eventId)
                .sharedUserIdList(accountList)
                .build();

        // when & then
        eventShareService.createEventShare(eventShareDto);

        verify(eventShareMapper).createEventShare(any(EventShare.class));
    }
}