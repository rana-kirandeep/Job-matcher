package com.swipjobs.kirandeep.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Job {
    private boolean driverLicenseRequired;
    private List<String> requiredCertificates;
    private Location location;
    @JsonDeserialize(using = CustomAmountBigDecimalDeserializer.class)
    @JsonSerialize(using = CustomAmountBigDecimalSerializer.class)
    private BigDecimal billRate;
    private int workersRequired;
    private ZonedDateTime startDate;
    private String about;
    private String jobTitle;
    private String company;
    private String guid;
    private int jobId;
}



