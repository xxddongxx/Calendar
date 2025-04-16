package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.dto.CalendarCreateDto;
import com.xxddongxx.calendar.mapper.CalendarMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private CalendarMapper calendarMapper;

    @Test
    void createCalendarSuccess() {
        // given
        CalendarCreateDto calendarCreateDto = CalendarCreateDto.builder()
                .title("저녁 약속")
                .location("여의도")
                .isImportant(true)
                .isLunar(true)
                .startAt(LocalDateTime.of(2025, 4, 15, 18, 30))
                .endAt(LocalDateTime.of(2025, 4, 15, 20, 0))
                .isAllDay(false)
                .isRepeat(true)
                .repeatType("DAILY")
                .isPrivate(true)
                .color("#FF00AA")
                .description("맛있는 저녁!!")
                .userId(1L)
                .build();

        // when
        calendarService.createCalendar(calendarCreateDto);

        // then
        verify(calendarMapper, times(1)).insertCalendar(any());
    }

    @DisplayName("실패 - 제목 없음")
    @Test
    void createCalendarFailNoneTitle() {
        // given
        CalendarCreateDto calendarCreateDto = CalendarCreateDto.builder()
                .title("")
                .location("여의도")
                .isImportant(true)
                .isLunar(true)
                .startAt(LocalDateTime.of(2025, 4, 15, 18, 30))
                .endAt(LocalDateTime.of(2025, 4, 15, 20, 0))
                .isAllDay(false)
                .isRepeat(true)
                .repeatType("DAILY")
                .isPrivate(true)
                .color("#FF00AA")
                .description("맛있는 저녁!!")
                .userId(1L)
                .build();

        // when & then
        assertThatThrownBy(() -> calendarService.createCalendar(calendarCreateDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("제목은 필수입니다.");

    }

    @DisplayName("실패 - 시작 시간보다 종료시간이 더 앞일 경우")
    @Test
    void createCalendarFailBeforeEndTime() {
        // given
        Long userId = 1L;
        CalendarCreateDto calendarCreateDto = CalendarCreateDto.builder()
                .title("저녁 약속")
                .location("여의도")
                .isImportant(true)
                .isLunar(true)
                .startAt(LocalDateTime.of(2025, 4, 15, 18, 30))
                .endAt(LocalDateTime.of(2025, 4, 15, 17, 0))
                .isAllDay(false)
                .isRepeat(true)
                .repeatType("DAILY")
                .isPrivate(true)
                .color("#FF00AA")
                .description("맛있는 저녁!!")
                .userId(1L)
                .build();

        // when & then
        assertThatThrownBy(() -> calendarService.createCalendar(calendarCreateDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("시작 시간은 종료 시간보다 이전이어야 합니다.");

    }

    @DisplayName("실패 - 사용자가 없을 경우")
    @Test
    void createCalendarFailWithoutUserId() {
        // given
        Long userId = 1L;
        CalendarCreateDto calendarCreateDto = CalendarCreateDto.builder()
                .title("저녁 약속")
                .location("여의도")
                .isImportant(true)
                .isLunar(true)
                .startAt(LocalDateTime.of(2025, 4, 15, 18, 30))
                .endAt(LocalDateTime.of(2025, 4, 15, 20, 0))
                .isAllDay(false)
                .isRepeat(true)
                .repeatType("DAILY")
                .isPrivate(true)
                .color("#FF00AA")
                .description("맛있는 저녁!!")
                .userId(null)
                .build();

        // when & then
        assertThatThrownBy(() -> calendarService.createCalendar(calendarCreateDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("사용자 정보가 필요합니다.");

    }
}