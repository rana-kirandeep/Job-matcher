package com.swipejobs.kirandeep.service.predicates;

import com.swipejobs.kirandeep.domain.Job;
import com.swipejobs.kirandeep.domain.Worker;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class MatchingSkillsPredicateTest {


    @Test
    void whenWorkerHasMatchingSkillThenPass() {

        Predicate<Job> matchingSkillsPredicate = MatchingSkillsPredicate.getPredicate(Worker.builder()
                .skills(List.of("skill1"))
                .build());
        assertTrue(matchingSkillsPredicate.test(Job.builder().jobTitle("skill1").build()));
    }

    @Test
    void whenWorkerDoesntHasMatchingSkillThenFail() {

        Predicate<Job> matchingSkillsPredicate = MatchingSkillsPredicate.getPredicate(Worker.builder()
                .skills(List.of("skill2"))
                .build());
        assertFalse(matchingSkillsPredicate.test(Job.builder().jobTitle("skill1").build()));
    }

    @Test
    void whenWorkerIsNullThenThrowRuntimeException() {
        Predicate<Job> matchingSkillsPredicate = MatchingSkillsPredicate.getPredicate(null);
        assertThrows(RuntimeException.class, () -> matchingSkillsPredicate.test(Job.builder().jobTitle("skill1").build()));
    }

    @Test
    void whenJobIsNullThenThrowRuntimeException() {
        Predicate<Job> matchingSkillsPredicate = MatchingSkillsPredicate.getPredicate(Worker.builder()
                .skills(List.of("skill2"))
                .build());
        assertThrows(RuntimeException.class, () -> matchingSkillsPredicate.test(null));
    }

}