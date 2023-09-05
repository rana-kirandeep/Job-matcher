package com.swipjobs.kirandeep.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {
    private String longitude;
    private String latitude;
}
