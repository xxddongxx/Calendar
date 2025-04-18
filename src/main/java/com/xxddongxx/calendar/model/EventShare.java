package com.xxddongxx.calendar.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShare {
    private Long id;
    private Long eventId;
    private Long sharedUserId;
    private List<Long> sharedUserIdList;
}
