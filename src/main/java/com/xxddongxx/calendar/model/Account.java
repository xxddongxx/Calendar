package com.xxddongxx.calendar.model;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
}
