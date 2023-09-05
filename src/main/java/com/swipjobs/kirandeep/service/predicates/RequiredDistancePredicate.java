package com.swipjobs.kirandeep.service.predicates;

import com.swipjobs.kirandeep.domain.Job;
import com.swipjobs.kirandeep.domain.JobSearchAddress;
import com.swipjobs.kirandeep.domain.Worker;

import java.util.function.Predicate;

public class RequiredDistancePredicate {

    public static Predicate<Job> getPredicate(Worker worker) {

        return (j) -> isInRequiredDistance(j, worker);
    }

    private static boolean isInRequiredDistance(Job job, Worker worker) {

        JobSearchAddress workerJob = worker.getJobSearchAddress();

        double lon1 = Math.toRadians(Double.valueOf(workerJob.getLongitude()));
        double lon2 = Math.toRadians(Double.valueOf(job.getLocation().getLongitude()));
        double lat1 = Math.toRadians(Double.valueOf(workerJob.getLatitude()));
        double lat2 = Math.toRadians(Double.valueOf(job.getLocation().getLatitude()));

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;
        double distanceInKm = c * r;
        // calculate the result
        return distanceInKm <= workerJob.getMaxJobDistance();
    }

}
