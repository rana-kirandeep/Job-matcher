package com.swipjobs.kirandeep.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Name {
    private String last;
    private String first;
}
