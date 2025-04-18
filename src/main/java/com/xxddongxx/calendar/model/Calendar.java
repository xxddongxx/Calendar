package com.xxddongxx.calendar.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
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
    private List<Long> sharedUserIdList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
