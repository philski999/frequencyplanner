package me.phil.frequencyplanner.domain;

import java.util.Map;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import com.google.common.collect.Maps;

public class ScoreCalculator implements EasyScoreCalculator<Plan> {

	public HardSoftScore calculateScore(Plan solution) {
		int hardScore = 0 ;
		int softScore = 0 ;

		// Very simple score - the number of unassigned assignments
		for (FrequencyAssignment assignment : solution.getFrequencyAssignments()) {
			if (assignment.getChannel() == null) {
				hardScore--;
			}
		}

		// And whether a channel's bandwidth has been exceeded
		for (Channel channel : solution.getChannels()) {
			Integer assignedBandwidth = 0;

			for (FrequencyAssignment assignment : solution.getFrequencyAssignments()) {
				if (channel.equals(assignment.getChannel())) {
					assignedBandwidth += assignment.getBandwidthInkHz();
				}
			}

			hardScore -= assignedBandwidth > channel.getAvailableBandwidthInkHz() ? assignedBandwidth - channel.getAvailableBandwidthInkHz() : 0;
		}

		return HardSoftScore.valueOf(hardScore, softScore);
	}

}
