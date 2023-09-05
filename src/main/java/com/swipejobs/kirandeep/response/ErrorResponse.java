package com.swipejobs.kirandeep.response;


import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, String code, String errorMessage, String details, String traceId) {


}
