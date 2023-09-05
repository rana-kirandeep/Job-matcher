package com.swipjobs.kirandeep.service.predicates;

import com.swipjobs.kirandeep.domain.Job;
import com.swipjobs.kirandeep.domain.Worker;

import java.util.function.Predicate;

public class DriverLicensePredicate {

    public static Predicate<Job> getPredicate(Worker worker) {
        return (j) -> isDriverLicenseRequired(j, worker);
    }

    private static boolean isDriverLicenseRequired(Job job, Worker worker) {
        if (job.isDriverLicenseRequired()) {
            return worker.isHasDriversLicense();
        }
        return true;
    }
}
