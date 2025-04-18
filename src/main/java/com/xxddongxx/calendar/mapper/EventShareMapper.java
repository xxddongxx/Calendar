package com.xxddongxx.calendar.mapper;

import com.xxddongxx.calendar.model.EventShare;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventShareMapper {
    void createEventShare(EventShare eventShare);
}
