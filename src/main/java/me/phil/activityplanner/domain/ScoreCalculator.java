package me.phil.activityplanner.domain;

import java.util.Map;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import com.google.common.collect.Maps;

public class ScoreCalculator implements EasyScoreCalculator<Plan> {

	public HardSoftScore calculateScore(Plan solution) {
		int hardScore = 0 ;
		int softScore = 0 ;

		// Very simple score - the number of unassigned assignments
		for (Assignment assignment : solution.getAssignments()) {
			if (assignment.getDay() == null) {
				hardScore--;
			}
		}

		// And whether a day's slots have been exceeded
		for (Day day : solution.getAssignmentDays()) {
			int assignments = 0;

			for (Assignment assignment : solution.getAssignments()) {
				if (day.equals(assignment.getDay())) {
					assignments++;
				}
			}

			hardScore -= assignments > day.getAvailableAssignmentSlots() ? assignments - day.getAvailableAssignmentSlots() : 0;
		}

		// And whether there are any activities assigned to the day in ones.
		for (Day day : solution.getAssignmentDays()) {
			Map<Activity, Integer> scores = Maps.newHashMap();

			for (Assignment assignment : solution.getAssignments()) {
				if (day.equals(assignment.getDay())) {
					Integer assignmentCounter = scores.get(assignment.getAssignedActivity());
					if (assignmentCounter == null) {
						{
							scores.put(assignment.getAssignedActivity(),  1);
						}
					} else {
						scores.put(assignment.getAssignedActivity(), assignmentCounter + 1);
					} 
				}
			}

			for (Map.Entry<Activity, Integer> entry : scores.entrySet()) {
				if (entry.getValue() == 1)
					softScore--;
			}
		}		


		return HardSoftScore.valueOf(hardScore, softScore);
	}

}
