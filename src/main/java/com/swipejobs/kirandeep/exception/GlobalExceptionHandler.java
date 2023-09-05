package com.swipejobs.kirandeep.exception;


import com.swipejobs.kirandeep.response.ErrorResponse;
import com.swipejobs.kirandeep.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_GENERIC = "1000";
    public static final String ERROR_WORKER_NOT_FOUND = "1001";

    public static final String ERROR_WORKER_API = "1002";

    public static final String ERROR_WORKER_NOT_ACTIVE = "1003";

    public static final String ERROR_VALIDATION_FAIL = "1004";


    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(WorkerNotFoundException.class)
    public ResponseEntity handleApiException(WorkerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                ErrorResponse(LocalDateTime.now(), ERROR_WORKER_NOT_FOUND,
                messageSource.getMessage(ERROR_WORKER_NOT_FOUND, null, Locale.ENGLISH),
                ex.getErrorDetails(), ex.getTraceId()));
    }

    @ExceptionHandler(WorkerNotActiveException.class)
    public ResponseEntity handleApiException(WorkerNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                ErrorResponse(LocalDateTime.now(), ERROR_WORKER_NOT_ACTIVE,
                messageSource.getMessage(ERROR_WORKER_NOT_ACTIVE, null, Locale.ENGLISH),
                ex.getErrorDetails(), ex.getTraceId()));
    }

    @ExceptionHandler(WorkerApiException.class)
    public ResponseEntity handleApiException(WorkerApiException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                ErrorResponse(LocalDateTime.now(), ERROR_WORKER_API,
                messageSource.getMessage(ERROR_WORKER_API, null, Locale.ENGLISH),
                ex.getErrorDetails(), ex.getTraceId()));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException ex) {
        var traceId = WorkerUtil.getUUID();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                ErrorResponse(LocalDateTime.now(), ERROR_VALIDATION_FAIL,
                messageSource.getMessage(ERROR_VALIDATION_FAIL, null, Locale.ENGLISH),
                "Invalid valid value for: " + ex.getName(), traceId));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleMethodArgumentNotValidException(ConstraintViolationException ex) {
        var traceId = WorkerUtil.getUUID();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                ErrorResponse(LocalDateTime.now(), ERROR_VALIDATION_FAIL,
                messageSource.getMessage(ERROR_VALIDATION_FAIL, null, Locale.ENGLISH),
                ex.getConstraintViolations().stream().map(i -> i.getMessage()).collect(Collectors.joining()), traceId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenericException(Exception ex) {
        var traceId = WorkerUtil.getUUID();
        log.error("traceId [{}]: Something went wrong", traceId, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                ErrorResponse(LocalDateTime.now(), ERROR_GENERIC,
                messageSource.getMessage(ERROR_GENERIC, null, Locale.ENGLISH),
                messageSource.getMessage(ERROR_GENERIC, null, Locale.ENGLISH), traceId));
    }

}
