package com.swipjobs.kirandeep.service.predicates;

import com.swipjobs.kirandeep.domain.Job;
import com.swipjobs.kirandeep.domain.Worker;

import java.util.function.Predicate;

public class RequiredCertificatesPredicate {

    public static Predicate<Job> getPredicate(Worker worker) {
        return (j) -> hasRequiredCertificates(j, worker);
    }


    private static boolean hasRequiredCertificates(Job job, Worker worker) {

        return worker.getCertificates().containsAll(job.getRequiredCertificates());

    }
}
