package com.swipejobs.kirandeep.service;

import com.swipejobs.kirandeep.domain.*;
import com.swipjobs.kirandeep.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MatcherService.class})
class MatcherServiceTest {

    @Autowired
    private MatcherService matcherService;

    @MockBean
    private JobService jobService;

    @MockBean
    private WorkerService workerService;


    @Test
    public void findMatchingJobsTest() {

        List<Job> jobs = getJobs();
        Worker worker = getWorker();
        long workerId = 10;
        when(jobService.getJobs()).thenReturn(jobs);
        when(workerService.getWorker(workerId)).thenReturn(worker);


        List<Job> matchingJobs = matcherService.findMatchingJobs(workerId);
        assertThat(matchingJobs, hasSize(1));


    }

    private List<Job> getJobs() {
        List<Job> jobs = List.of(
                Job.builder()
                        .jobId(1)
                        .billRate(new BigDecimal(10.6))
                        .driverLicenseRequired(true)
                        .requiredCertificates(List.of("cert1", "cert3", "cert9"))
                        .jobTitle("title1")
                        .startDate(ZonedDateTime.parse("2015-11-10T16:07:25.62Z"))
                        .location(Location.builder()
                                .latitude("50.022868")
                                .longitude("14.316602")
                                .build())
                        .build(),
                Job.builder()
                        .jobId(2)
                        .billRate(new BigDecimal(12.6))
                        .driverLicenseRequired(true)
                        .requiredCertificates(List.of("cert2", "cert3", "cert9"))
                        .jobTitle("title11")
                        .startDate(ZonedDateTime.parse("2015-11-10T16:07:25.62Z"))
                        .location(Location.builder()
                                .latitude("50.022868")
                                .longitude("14.316602")
                                .build())
                        .build(),
                Job.builder()
                        .jobId(3)
                        .billRate(new BigDecimal(14.6))
                        .driverLicenseRequired(false)
                        .requiredCertificates(List.of("cert1", "cert5", "cert9"))
                        .jobTitle("title10")
                        .startDate(ZonedDateTime.parse("2015-10-10T16:07:25.62Z"))
                        .location(Location.builder()
                                .latitude("50.022868")
                                .longitude("14.316602")
                                .build())
                        .build(),
                Job.builder()
                        .jobId(4)
                        .billRate(new BigDecimal(9.5))
                        .driverLicenseRequired(false)
                        .requiredCertificates(List.of("cert1", "cert8", "cert9"))
                        .jobTitle("title3")
                        .startDate(ZonedDateTime.parse("2015-12-10T16:07:25.62Z"))
                        .location(Location.builder()
                                .latitude("50.022868")
                                .longitude("14.316602")
                                .build())
                        .build());
        return jobs;
    }

    private Worker getWorker() {
        Worker worker = Worker.builder()
                .active(true)
                .certificates(List.of("cert1", "cert3", "cert9", "cert5", "cert2", "cert7", "cert8"))
                .hasDriversLicense(true)
                .availability(List.of(Availability.builder().dayIndex(1).build(),
                        Availability.builder().dayIndex(2).build(),
                        Availability.builder().dayIndex(3).build()))
                .jobSearchAddress(JobSearchAddress.builder()
                        .latitude("50.022868")
                        .longitude("14.316602")
                        .build())
                .userId(10)
                .skills(List.of("title3", "title10", "title11","title10"))
                .build();

        return worker;
    }


}