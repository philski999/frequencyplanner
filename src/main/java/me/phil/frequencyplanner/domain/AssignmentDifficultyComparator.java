package me.phil.frequencyplanner.domain;

import java.util.Comparator;

public class AssignmentDifficultyComparator implements Comparator<FrequencyAssignment> {

	public int compare(FrequencyAssignment arg0, FrequencyAssignment arg1) {
		return arg0.getBandwidthInkHz() - arg1.getBandwidthInkHz();
	}
}
