package com.xxddongxx.calendar.mapper;

import com.xxddongxx.calendar.model.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AccountMapper {
    Optional<Account> findByEmail(String email);
}
