package com.xxddongxx.calendar.controller;

import com.xxddongxx.calendar.dto.AccountDto;
import com.xxddongxx.calendar.service.AccountService;
import com.xxddongxx.calendar.util.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "사용자 조회", description = "해당 사용자가 있는지 조회")
    public ResponseEntity<ResponseMessage<AccountDto>> findByEmail(@RequestParam("email") String email) {
        logger.info("find by email");
        AccountDto accountDto = accountService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMessage.success(accountDto));
    }
}
