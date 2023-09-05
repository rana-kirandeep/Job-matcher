package com.swipjobs.kirandeep.service;


import com.swipjobs.kirandeep.domain.Worker;
import com.swipjobs.kirandeep.exception.WorkerApiException;
import com.swipjobs.kirandeep.exception.WorkerNotFoundException;
import com.swipjobs.kirandeep.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class WorkerService {

    @Value("${api.workers.uri}")
    private String URI_WORKERS;

    @Autowired
    private WebClient webClient;

    public Worker getWorker(long workerId) {
        Mono<List<Worker>> result = webClient.get().uri(URI_WORKERS)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Worker>>() {})
                .onErrorMap(errorHandler)
                .doOnSuccess(onSuccessHandler);

        List<Worker> workers = result.block();
        Optional<Worker> worker = workers.stream()
                .filter(w -> w.getUserId() == workerId).findFirst();

        return worker.orElseThrow(() -> {
            log.error("Worker not found with id: {}", workerId);
            throw new WorkerNotFoundException(workerId, "Worker not found with ID:"+workerId, WorkerUtil.getUUID());
        });
    }


    Function<Throwable, Throwable> errorHandler = (ex) -> {
        log.error("error while fetching workers", ex);
        return new WorkerApiException("Something went wrong with workers API", WorkerUtil.getUUID());
    };

    Consumer<List<Worker>> onSuccessHandler = (res) -> {
        log.info(res.toString());
    };

}
