package com.swipjobs.kirandeep.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Availability {
    private String title;
    private int dayIndex;
}
