package me.phil.activityplanner.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * A period spent on an activity on a particular day (which may be unassigned).
 */
@PlanningEntity(difficultyComparatorClass = AssignmentDifficultyComparator.class)
public class Assignment {
	private Activity assignedActivity;
	private Day day;
	
	public Assignment() {
	}
	
	public Assignment(Activity assignedActivity) {
		super();
		this.assignedActivity = assignedActivity;
	}

	@PlanningVariable(valueRangeProviderRefs = {"activeDays"})
	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Activity getAssignedActivity() {
		return assignedActivity;
	}
	
	
}
