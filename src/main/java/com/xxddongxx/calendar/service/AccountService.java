package com.xxddongxx.calendar.service;

import com.xxddongxx.calendar.config.exception.CustomException;
import com.xxddongxx.calendar.dto.AccountDto;
import com.xxddongxx.calendar.mapper.AccountMapper;
import com.xxddongxx.calendar.model.Account;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountMapper accountMapper;

    @Transactional(readOnly = true)
    public AccountDto findByEmail(String email) {
        Account account = accountMapper.findByEmail(email).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "해당 사용자가 없습니다.")
        );

        return new AccountDto().toDto(account);
    }
}
