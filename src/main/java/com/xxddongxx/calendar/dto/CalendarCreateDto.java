package com.xxddongxx.calendar.dto;

import com.xxddongxx.calendar.model.Calendar;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일정 등록 요청 DTO")
public class CalendarCreateDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Schema(description = "일정 제목", example = "저녁 약속")
    private String title;

    @Schema(description = "중요 여부(중요: true, 일반: false)", example = "false")
    private boolean isImportant;

    @Schema(description = "장소", example = "여의도")
    private String location;

    @Schema(description = "양력 여부(양력: true, 음력: false)", example = "true")
    private boolean isLunar;

    @NotNull(message = "시작 시간은 필수입니다.")
    @Schema(description = "시작 시간", example = "2025-04-15T18:00:00")
    private LocalDateTime startAt;

    @NotNull(message = "종료 시간은 필수입니다.")
    @Schema(description = "종료 시간", example = "2025-04-15T20:00:00")
    private LocalDateTime endAt;

    @Schema(description = "종일 여부(종일: true, 일반: false)", example = "false")
    private boolean isAllDay;

    @Schema(description = "반복 여부(반복: true, 일반: false)", example = "false")
    private boolean isRepeat;

    @Schema(description = "반복 주기(DAILY, WEEKLY, MONTHLY, YEARLY)", example = "DAILY")
    private String repeatType;

    @Schema(description = "공개 여부(공개: true, 개인: false)", example = "true")
    private boolean isPrivate;

    @Schema(description = "번주(색상)", example = "#FF00AA")
    private String color;

    @Schema(description = "일정 설명", example = "맛있는 저녁!!")
    private String description;

    @NotNull(message = "사용자 아이디는 필수입니다.")
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    public Calendar toModel() {
        return Calendar.builder()
                .title(title)
                .isImportant(isImportant)
                .location(location)
                .isLunar(isLunar)
                .startAt(startAt)
                .endAt(endAt)
                .isAllDay(isAllDay)
                .isRepeat(isRepeat)
                .repeatType(repeatType)
                .isPrivate(isPrivate)
                .color(color)
                .description(description)
                .userId(userId)
                .build();
    }
}
