package com.xxddongxx.calendar.dto;

import com.xxddongxx.calendar.model.Account;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;

    public AccountDto toDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .name(account.getName())
                .build();
    }
}
