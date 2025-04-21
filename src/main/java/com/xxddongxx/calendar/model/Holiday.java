package com.xxddongxx.calendar.model;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
    private Long id;
    private LocalDate holidate;
    private String dateName;
    private boolean isHoliday;
}
