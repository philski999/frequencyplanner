package me.phil.frequencyplanner.domain;

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

	private List<FrequencyAssignment> assignments;
	private List<Channel> channels;
	private HardSoftScore score;
	
	public Plan() {
	}
	
	public Plan(List<FrequencyAssignment> assignments, List<Channel> channels) {
		super();
		this.assignments = assignments;
		this.channels = channels;
	}

	@PlanningEntityCollectionProperty
	public List<FrequencyAssignment> getFrequencyAssignments() {
		return assignments;
	}

	@ValueRangeProvider(id="activeChannels")
	public List<Channel> getChannels() {
		return channels;
	}

	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}

	public Collection<? extends Object> getProblemFacts() {
		List<Object> facts = Lists.newArrayList();
		facts.addAll(assignments);
		facts.addAll(channels);
		return facts;
	}

	public static Plan emptyPlan() {
		return new Plan();
	}
}
