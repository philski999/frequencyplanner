package me.phil.frequencyplanner.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * A block of spectrum that has been assigned
 */
@PlanningEntity(difficultyComparatorClass = AssignmentDifficultyComparator.class)
public class FrequencyAssignment {
	private Integer bandwidthInkHz = null;
	private Integer startFrequencyInkHz = null;
	private Channel channel = null;
	
	public FrequencyAssignment() {
	}
	
	public FrequencyAssignment(Integer bandwidthInkHz) {
		super();
		this.bandwidthInkHz = bandwidthInkHz;
	}

	public Integer getStartFrequencyInkHz() {
		return startFrequencyInkHz;
	}

	public void setStartFrequencyInkHz(Integer frequencyInkHz) {
		this.startFrequencyInkHz = frequencyInkHz;
	}

	public Integer getBandwidthInkHz() {
		return bandwidthInkHz;
	}

	@PlanningVariable(valueRangeProviderRefs = {"activeChannels"})
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
