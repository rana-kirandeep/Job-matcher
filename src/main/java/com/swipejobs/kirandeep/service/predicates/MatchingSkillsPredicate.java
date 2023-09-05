package com.swipejobs.kirandeep.service.predicates;

import com.swipejobs.kirandeep.domain.Job;
import com.swipejobs.kirandeep.domain.Worker;

import java.util.function.Predicate;

public class MatchingSkillsPredicate {

    public static Predicate<Job> getPredicate(Worker worker) {
        return (j) -> hasMatchingSkills(j, worker);
    }

    private static boolean hasMatchingSkills(Job job, Worker worker) {
        if (job == null || worker == null) {
            throw new RuntimeException("Invalid argument");
        }
        return worker.getSkills().contains(job.getJobTitle());
    }
}
