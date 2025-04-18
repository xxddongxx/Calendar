package com.xxddongxx.calendar.dto;

import com.xxddongxx.calendar.model.EventShare;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShareDto {
    private Long id;
    private Long eventId;
    private Long sharedUserId;
    private List<Long> sharedUserIdList;

    public EventShareDto toDto(EventShare eventShare) {
        return EventShareDto.builder()
                .id(eventShare.getId())
                .eventId(eventShare.getEventId())
                .sharedUserId(eventShare.getSharedUserId())
                .build();
    }

    public EventShare toModel() {
        return EventShare.builder()
                .eventId(eventId)
                .sharedUserIdList(sharedUserIdList)
                .build();
    }
}
