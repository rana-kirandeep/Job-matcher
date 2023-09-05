package com.swipjobs.kirandeep.service;

import com.swipjobs.kirandeep.domain.Job;
import com.swipjobs.kirandeep.exception.WorkerApiException;
import com.swipjobs.kirandeep.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Slf4j
public class JobService {

    @Value("${api.jobs.uri}")
    private String URI_JOBS;

    @Autowired
    private WebClient webClient;

    public List<Job> getJobs() {
        //TODO try to make common place to hit
        Mono<List<Job>> result = webClient.get().uri(URI_JOBS)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Job>>() {
                })
                .onErrorMap(errorHandler)
                .doOnSuccess(onSuccessHandler);

        List<Job> jobs = result.block();

        return jobs;
    }

    private Function<Throwable, Throwable> errorHandler = (ex) -> {
        log.error("error while fetching Jobs", ex);
        return new WorkerApiException("Something went wrong with Job API", WorkerUtil.getUUID());
    };

    private Consumer<List<Job>> onSuccessHandler = (res) -> {
        log.info(res.toString());
    };
}