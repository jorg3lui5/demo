package com.test.demo.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.test.demo.server.models.Error;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@Setter
public class FailureException extends RuntimeException {

    private final Error failure;
    private final int errorCode;
    private final HttpStatus status;
}
