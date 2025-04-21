package com.xxddongxx.calendar.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor@AllArgsConstructor
public class HolidayDto {
    private Long id;
    private LocalDate holidate;
    private String dateName;
    private boolean isHoliday;
}
