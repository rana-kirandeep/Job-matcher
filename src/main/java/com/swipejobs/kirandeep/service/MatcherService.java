package com.swipejobs.kirandeep.service;

import com.swipejobs.kirandeep.domain.Job;
import com.swipejobs.kirandeep.domain.Worker;
import com.swipejobs.kirandeep.exception.WorkerNotActiveException;
import com.swipejobs.kirandeep.exception.WorkerNotFoundException;
import com.swipejobs.kirandeep.service.predicates.*;
import com.swipejobs.kirandeep.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatcherService {

    @Value("${job.match.limit}")
    private int jobMatchLimit;
    @Autowired
    private WorkerService workerService;

    @Autowired
    private JobService jobService;

    private Comparator<Job> billRateComparator = Comparator.comparing(Job::getBillRate).reversed();

    public List<Job> findMatchingJobs(long workerId) {

        log.info("get suitable jobs for worker: {} ", workerId);
        //Fetch worker
        Worker worker = workerService.getWorker(workerId);
        log.debug("Get worker: {} ", worker);

        //Validate worker
        validateWorker(workerId, worker);

        //Fetch Jobs
        List<Job> jobs = jobService.getJobs();
        log.debug("total jobs:" + jobs);

        List<Job> matchingJobs = jobs.stream()
                .filter(DriverLicensePredicate.getPredicate(worker)
                        .and(RequiredCertificatesPredicate.getPredicate(worker))
                        .and(MatchingSkillsPredicate.getPredicate(worker))
                        .and(WorkerAvailablePredicate.getPredicate(worker))
                        .and(RequiredDistancePredicate.getPredicate(worker)))
                .sorted(billRateComparator)
                .limit(jobMatchLimit)
                .collect(Collectors.toList());

        log.info("matchingJobs jobs:" + matchingJobs);


        return matchingJobs;
    }

    private void validateWorker(long workerId, Worker worker) {
        if (worker == null) {
            log.error("Worker not found with id: {}", workerId);
            throw new WorkerNotFoundException(workerId, "Worker not found with ID:" + workerId, WorkerUtil.getUUID());
        }
        if (!worker.isActive()) {
            log.error("Worker is not active with id: {}" + workerId);
            throw new WorkerNotActiveException(workerId, "Worker not active with ID:" + workerId, WorkerUtil.getUUID());
        }
    }


}
