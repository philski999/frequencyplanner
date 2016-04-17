package me.phil.frequencyplanner.csvadapter;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.google.common.collect.Lists;

import me.phil.frequencyplanner.domain.Channel;
import me.phil.frequencyplanner.domain.FrequencyAssignment;
import me.phil.frequencyplanner.domain.Plan;


public class PlanningProblemReader {
	private static final String CHANNEL_BANDWIDTH_HEADING = "BandwidthInkHz";
	private static final String CHANNEL_NAME_HEADING = "Name";
	private static final String ASSIGNMENT_BANDWIDTH_HEADING = "BandwidthInkHz";

	/**
	 * Reads two csv streams and constructs an unsolved plan (i.e. one where frequency assignments have not been
	 * assigned to channels)
	 * 
	 * The frequencyassignments stream has one columns headed "BandwidthInkHz".
	 * The content is the amount of bandwidth to assign.
	 * 
	 * The channels stream has two columns headed "Name" and "BandwidthInkHz".
	 * The Name column contains the name of the channel
	 * The BandwidthInkHz column contains the BandwidthInkHz
	 *
	 * Returns an unsolved plan describing the data.
	 */
	public static Plan newPlanFromCSV(Reader assignmentsReader, Reader channelReader) {
		List<FrequencyAssignment> assignments = Lists.newArrayList();
		List<Channel> channels = Lists.newArrayList();

		
		try {
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines().
					parse(assignmentsReader);
			for (CSVRecord record : records) {
				String assignmentBandwidthInkHz = record.get(ASSIGNMENT_BANDWIDTH_HEADING);
				
				Integer assignmentBandwidthInkHzAsInt = null;
				try {
					assignmentBandwidthInkHzAsInt = Integer.parseInt(assignmentBandwidthInkHz);
				} catch (NumberFormatException e) {
					// Ignore format errors, deal with them as missing values
				}
				
				if (assignmentBandwidthInkHzAsInt != null && assignmentBandwidthInkHzAsInt > 0) {
					assignments.add(new FrequencyAssignment(assignmentBandwidthInkHzAsInt));
				}
			}
			
			records = CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines().parse(channelReader);
			for (CSVRecord record : records) {
				String name = record.get(CHANNEL_NAME_HEADING);
				String availableBandwidthInkHzAsString = record.get(CHANNEL_BANDWIDTH_HEADING);
				
				Integer availableBandwidthInkHz = null;
				try {
					availableBandwidthInkHz = Integer.parseInt(availableBandwidthInkHzAsString);
					
					// Final validation
					if (availableBandwidthInkHz > 0) {
						Channel c = new Channel(name, 0, availableBandwidthInkHz);
						channels.add(c);
					}
				} catch (NumberFormatException e) {
					// Ignore format errors
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Plan(assignments, channels);
	}
}
