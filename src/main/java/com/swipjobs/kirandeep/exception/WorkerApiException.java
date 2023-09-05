package com.swipjobs.kirandeep.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WorkerApiException extends RuntimeException {

    private String errorDetails;
    private String traceId;

}
