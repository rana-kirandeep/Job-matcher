package com.swipjobs.kirandeep.controller;

import com.swipjobs.kirandeep.response.ApiResponse;
import com.swipjobs.kirandeep.service.MatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/workers")
@Validated
public class WorkerController {

    @Autowired
    private MatcherService matcherService;


    @GetMapping("/{workerId}")
    public ApiResponse getMatchingJobs(@PathVariable(value = "workerId") @PositiveOrZero(message = "{validation.error.invalid.workerId}") long workerId) {

        return new ApiResponse(matcherService.findMatchingJobs(workerId));
    }


}
