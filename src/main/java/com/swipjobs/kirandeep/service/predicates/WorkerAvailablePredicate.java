package com.swipjobs.kirandeep.service.predicates;

import com.swipjobs.kirandeep.domain.Job;
import com.swipjobs.kirandeep.domain.Worker;

import java.util.function.Predicate;

public class WorkerAvailablePredicate {

    public static Predicate<Job> getPredicate(Worker worker) {

        return (j) -> isWorkerAvailableOnTheDay(j, worker);
    }

    private static boolean isWorkerAvailableOnTheDay(Job job, Worker worker) {
        return worker.getAvailability().stream()
                .anyMatch(availability ->
                        availability.getDayIndex() == job.getStartDate().getDayOfWeek().getValue());
    }
}
