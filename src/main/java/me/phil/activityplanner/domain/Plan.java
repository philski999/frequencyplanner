package me.phil.activityplanner.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.google.common.collect.Lists;

@PlanningSolution
public class Plan implements Solution<HardSoftScore> {

	private List<Activity> activity;
	private List<Assignment> assignments;
	private List<Day> assignmentDays;
	private HardSoftScore score;
	
	public Plan() {
	}
	
	public Plan(List<Activity> activities,
			List<Assignment> assignments, List<Day> assignmentDays) {
		super();
		this.activity = activities;
		this.assignments = assignments;
		this.assignmentDays = assignmentDays;
	}

	@PlanningEntityCollectionProperty
	public List<Assignment> getAssignments() {
		return assignments;
	}

	@ValueRangeProvider(id="activeDays")
	public List<Day> getAssignmentDays() {
		return assignmentDays;
	}

	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}

	public Collection<? extends Object> getProblemFacts() {
		List<Object> facts = Lists.newArrayList();
		facts.addAll(activity);
		facts.addAll(assignmentDays);
		return facts;
	}
	
	public static Plan newUnsolvedPlan() {
		final int numberActivities = 10;
		
		List<Activity> activities = Lists.newArrayList();
		for (int i = 0 ; i < numberActivities ; i++) {
			activities.add(new Activity("Activity " + Integer.toString(i)));
		}
		
		List<Day> days = Lists.newArrayList() ;
		int totalSlots = 0;
		for (int i = 0 ; i < 100 ; i++) {
			int newSlots = (i % 5) + 1;
			days.add(new Day(LocalDate.now().plusDays(i), newSlots));
			totalSlots += newSlots;
		}
		
		System.out.println("Total available slots : " + Integer.toString(totalSlots));
		
		
		
		// Assignments are always associated with an activity but need to be assigned to a day
		List<Assignment> assignments = Lists.newArrayList();
		for (int i = 0 ; i < 250 ; i++) {
			assignments.add(new Assignment(activities.get(i % numberActivities)));
		}
		
		Plan plan = new Plan(activities, assignments, days);
		
		return plan;
	}

	public List<Activity> getActivities() {
		return activity;
	}

	public static Plan emptyPlan() {
		return new Plan();
	}
}
