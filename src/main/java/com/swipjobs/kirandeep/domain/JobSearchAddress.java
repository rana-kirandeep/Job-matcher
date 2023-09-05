package com.swipjobs.kirandeep.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobSearchAddress {
    private String unit;
    private int maxJobDistance;
    private String longitude;
    private String latitude;
}
