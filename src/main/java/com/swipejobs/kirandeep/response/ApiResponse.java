package com.swipejobs.kirandeep.response;


import com.swipejobs.kirandeep.domain.Job;

import java.util.List;

public record ApiResponse(List<Job> jobs) {
}
