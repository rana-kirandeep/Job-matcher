package com.swipejobs.kirandeep.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Worker {
    private int rating;
    @JsonProperty("isActive")
    private boolean active;
    private List<String> certificates;
    private List<String> skills;
    private JobSearchAddress jobSearchAddress;
    private String transportation;
    private boolean hasDriversLicense;
    private List<Availability> availability;
    private String phone;
    private String email;
    private Name name;
    private int age;
    private String guid;
    private long userId;
}









