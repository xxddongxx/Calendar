package com.xxddongxx.calendar.util;

import lombok.Builder;
import lombok.Getter;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseMessage<T> {
    private final HttpStatus status;
    private final String message;
    private final T data;

    @Builder
    public ResponseMessage(HttpStatus status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public static <T> ResponseMessage<T> success() {
        return ResponseMessage.<T>builder()
                .status(HttpStatus.OK)
                .message("요청이 성공적으로 처리되었습니다.")
                .build();
    }

    public static <T> ResponseMessage<T> success(T data) {
        return ResponseMessage.<T>builder()
                .status(HttpStatus.OK)
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();
    }

    public static <T> ResponseMessage<T> success(String message, T data) {
        return ResponseMessage.<T>builder()
                .status(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseMessage<T> error(HttpStatus status, String message) {
        return ResponseMessage.<T>builder()
                .status(status)
                .message(message)
                .build();

    }
}
