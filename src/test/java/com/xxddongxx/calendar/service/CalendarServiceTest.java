package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.config.exception.CustomException;
import com.xxddongxx.calendar.dto.CalendarCreateDto;
import com.xxddongxx.calendar.dto.CalendarDto;
import com.xxddongxx.calendar.dto.CalendarUpdateDto;
import com.xxddongxx.calendar.mapper.CalendarMapper;
import com.xxddongxx.calendar.model.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private CalendarMapper calendarMapper;

    @DisplayName("성공 - 일정 등록")
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

    @DisplayName("성공 - 일정 상세 조회")
    @Test
    void findCalendarOneSuccess() {
        // given
        Long calendarId = 1L;
        Calendar calendar = Calendar.builder()
                .id(calendarId)
                .title("회의")
                .startAt(LocalDateTime.of(2025, 4, 15, 18, 30))
                .endAt(LocalDateTime.of(2025, 4, 15, 20, 0))
                .location("강남")
                .userId(1L)
                .build();

        when(calendarMapper.findById(calendarId)).thenReturn(Optional.of(calendar));

        // when
        CalendarDto result = calendarService.findCalendarOne(calendarId);

        // then
        assertThat(result.getTitle()).isEqualTo("회의");
        assertThat(result.getLocation()).isEqualTo("강남");
    }

    @DisplayName("실패 - 일정 상세 조회(존재하지 않는 ID)")
    @Test
    void findCalendarOneWithoutId(){
        // given
        Long calendarId = 999L;

        // when
        when(calendarMapper.findById(calendarId)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> calendarService.findCalendarOne(calendarId))
                .isInstanceOf(CustomException.class)
                .hasMessage("등록된 일정이 없습니다.");

        verify(calendarMapper).findById(calendarId);
    }

    @DisplayName("성공 - 일정 수정")
    @Test
    void updateCalendarSuccess(){
        // given
        Long calendarId = 1L;
        Calendar calendar = Calendar.builder()
                .id(calendarId)
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

        CalendarUpdateDto calendarUpdateDto = CalendarUpdateDto.builder()
                .id(1L)
                .title("저녁 약속!")
                .location("여의도!")
                .isImportant(false)
                .isLunar(false)
                .startAt(LocalDateTime.of(2025, 4, 15, 10, 30))
                .endAt(LocalDateTime.of(2025, 4, 15, 15, 0))
                .isAllDay(false)
                .isRepeat(false)
                .repeatType("WEEKLY")
                .isPrivate(false)
                .color("#FF00AB")
                .description("맛있는 저녁!?")
                .userId(1L)
                .build();

        // mocking
        given(calendarMapper.findById(calendarId)).willReturn(Optional.of(calendar));
        given(calendarMapper.updateCalendar(any(Calendar.class))).willReturn(1);

        // when
        calendarService.updateCalendar(calendarId, calendarUpdateDto);

        // then
        verify(calendarMapper, times(2)).findById(calendarId);
        verify(calendarMapper).updateCalendar(any(Calendar.class));
    }

    @DisplayName("실패 - 일정 수정(없는 ID)")
    @Test
    void updateCalendarFail(){
        // given
        Long calendarId = 999L;

        CalendarUpdateDto calendarUpdateDto = CalendarUpdateDto.builder()
                .id(1L)
                .title("저녁 약속!")
                .location("여의도!")
                .isImportant(false)
                .isLunar(false)
                .startAt(LocalDateTime.of(2025, 4, 15, 10, 30))
                .endAt(LocalDateTime.of(2025, 4, 15, 15, 0))
                .isAllDay(false)
                .isRepeat(false)
                .repeatType("WEEKLY")
                .isPrivate(false)
                .color("#FF00AB")
                .description("맛있는 저녁!?")
                .userId(1L)
                .build();

        // mocking
        given(calendarMapper.findById(calendarId)).willReturn(Optional.empty());

        // when & then
        assertThrows(CustomException.class, () ->
                calendarService.updateCalendar(calendarId, calendarUpdateDto)
        );

        // then
        verify(calendarMapper).findById(calendarId);
        verify(calendarMapper, never()).updateCalendar(any(Calendar.class));
    }
}