package com.swipejobs.kirandeep.controller;

import com.swipejobs.kirandeep.configuration.AppConfiguration;
import com.swipejobs.kirandeep.domain.Job;
import com.swipejobs.kirandeep.domain.Location;
import com.swipejobs.kirandeep.service.MatcherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {WorkerController.class, AppConfiguration.class})
class WorkerControllerTest {


    public static final String URI = "/api/workers/10";
    @Autowired
    private MockMvc mvc;

    @MockBean
    MatcherService matcherService;

    @Autowired
    private MessageSource messageSource;

    @Test
    void whenWorkerIdIsValidThegetProductsReturnJob() throws Exception {
        when(matcherService.findMatchingJobs(10)).thenReturn(getJobs());
        mvc.perform(MockMvcRequestBuilders.get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.jobs").isNotEmpty());


    }


    @Test
    void whenGetProductsThrowExceptionThenReturnError() throws Exception {
        when(matcherService.findMatchingJobs(10)).thenThrow(new RuntimeException("exception"));

        mvc.perform(MockMvcRequestBuilders.get(URI))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("traceId")
                        .exists()).andExpect(jsonPath("code")
                        .value("1000"))
                .andExpect(jsonPath("details")
                        .value(messageSource.getMessage("1000", null, Locale.ENGLISH)));

    }

    private List<Job> getJobs() {
        List<Job> jobs = List.of(Job.builder().jobId(1).billRate(new BigDecimal(10.6)).driverLicenseRequired(true).requiredCertificates(List.of("cert1", "cert3", "cert9")).jobTitle("title1").startDate(ZonedDateTime.parse("2015-11-10T16:07:25.62Z")).location(Location.builder().latitude("50.022868").longitude("14.316602").build()).build(), Job.builder().jobId(2).billRate(new BigDecimal(12.6)).driverLicenseRequired(true).requiredCertificates(List.of("cert2", "cert3", "cert9")).jobTitle("title11").startDate(ZonedDateTime.parse("2015-11-10T16:07:25.62Z")).location(Location.builder().latitude("50.022868").longitude("14.316602").build()).build(), Job.builder().jobId(3).billRate(new BigDecimal(14.6)).driverLicenseRequired(false).requiredCertificates(List.of("cert1", "cert5", "cert9")).jobTitle("title10").startDate(ZonedDateTime.parse("2015-10-10T16:07:25.62Z")).location(Location.builder().latitude("50.022868").longitude("14.316602").build()).build(), Job.builder().jobId(4).billRate(new BigDecimal(9.5)).driverLicenseRequired(false).requiredCertificates(List.of("cert1", "cert8", "cert9")).jobTitle("title3").startDate(ZonedDateTime.parse("2015-12-10T16:07:25.62Z")).location(Location.builder().latitude("50.022868").longitude("14.316602").build()).build());
        return jobs;
    }

}