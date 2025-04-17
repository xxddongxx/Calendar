package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.dto.AccountDto;
import com.xxddongxx.calendar.mapper.AccountMapper;
import com.xxddongxx.calendar.model.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @DisplayName("성공 - 사용자 조회")
    @Test
    void findByEmailSuccess() {
        // given
        String email = "test1@test.com";
        Account account = Account.builder()
                .id(1L)
                .email("test1@test.com")
                .name("test")
                .build();

        // Mocking
        given(accountMapper.findByEmail(email)).willReturn(Optional.of(account));

        // when
        AccountDto result = accountService.findByEmail(email);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("test1@test.com");
        assertThat(result.getName()).isEqualTo("test");

        verify(accountMapper).findByEmail(email);
    }
}