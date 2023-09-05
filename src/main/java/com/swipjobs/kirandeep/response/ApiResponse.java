package com.swipjobs.kirandeep.response;


import com.swipjobs.kirandeep.domain.Job;

import java.util.List;

public record ApiResponse(List<Job> jobs) {
}
