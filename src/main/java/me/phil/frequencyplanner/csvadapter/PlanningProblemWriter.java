package me.phil.frequencyplanner.csvadapter;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import me.phil.frequencyplanner.domain.Channel;
import me.phil.frequencyplanner.domain.FrequencyAssignment;
import me.phil.frequencyplanner.domain.Plan;

public class PlanningProblemWriter {
	public static void print(Plan plan, Appendable output) throws IOException {
		CSVPrinter printer = CSVFormat.DEFAULT.withHeader("Channel", "AvailableBandwidthInkHz", "AssignedBandwidthInkHz").print(output);

		// Group the frequency assignments by channel
		ListMultimap<Channel, FrequencyAssignment> channelAssignments = ArrayListMultimap.create();
		for (FrequencyAssignment assignment : plan.getFrequencyAssignments()) {
        	channelAssignments.put(assignment.getChannel(), assignment);
        }
		
        for (Channel channel : plan.getChannels()) {
        	printer.print(channel.getName());
        	printer.print(channel.getAvailableBandwidthInkHz());

        	List<FrequencyAssignment> assignments = channelAssignments.get(channel);
        	Integer assignedBandwidth = 0;
        	for (FrequencyAssignment assignment : assignments) {
        		assignedBandwidth += assignment.getBandwidthInkHz();
        	}
        	printer.print(assignedBandwidth);
        	for (FrequencyAssignment assignment : assignments) {
        		printer.print(assignment.getBandwidthInkHz());
        	}
            
            printer.println();
        }
	}
}
