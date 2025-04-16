package com.xxddongxx.calendar.dto;

import com.xxddongxx.calendar.model.Calendar;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {
    private Long id;
    private String title;
    private boolean isImportant;
    private String location;
    private boolean isLunar;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private boolean isAllDay;
    private boolean isRepeat;
    private String repeatType;
    private boolean isPrivate;
    private String color;
    private String description;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CalendarDto toDto(Calendar calendar) {
        return CalendarDto.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .isImportant(calendar.isImportant())
                .location(calendar.getLocation())
                .isLunar(calendar.isLunar())
                .startAt(calendar.getStartAt())
                .endAt(calendar.getEndAt())
                .isAllDay(calendar.isAllDay())
                .isRepeat(calendar.isRepeat())
                .repeatType(calendar.getRepeatType())
                .isPrivate(calendar.isPrivate())
                .color(calendar.getColor())
                .description(calendar.getDescription())
                .userId(calendar.getUserId())
                .createdAt(calendar.getCreatedAt())
                .updatedAt(calendar.getUpdatedAt())
                .build();
    }
}
