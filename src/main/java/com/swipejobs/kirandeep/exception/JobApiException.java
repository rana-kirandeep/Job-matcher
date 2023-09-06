package com.swipejobs.kirandeep.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobApiException extends RuntimeException {

    private String errorDetails;
    private String traceId;

}
