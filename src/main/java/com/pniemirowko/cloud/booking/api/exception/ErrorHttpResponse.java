package com.pniemirowko.cloud.booking.api.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorHttpResponse {

    String path;
    HttpStatus status;
    String message;
}

