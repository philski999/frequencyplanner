package me.phil.activityplanner.csvadapter;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.google.common.collect.Lists;

import me.phil.activityplanner.domain.Activity;
import me.phil.activityplanner.domain.Assignment;
import me.phil.activityplanner.domain.Day;
import me.phil.activityplanner.domain.Plan;


public class PlanningProblemReader {
	private static final String SLOTS_HEADING = "Slots";
	private static final String DATE_HEADING = "Date";
	private static final String TARGET_SESSIONS_HEADING = "TargetSessions";
	private static final String ACTIVITY_NAME_HEADING = "ActivityName";

	/**
	 * Reads two csv streams and constructs an unsolved plan (i.e. one where activities have not been assigned days)
	 * 
	 * The activity stream has two columns headed "ActivityName" and "TargetSessions".
	 * The name is just a name, the target sessions is the number of sessions of that activity to assign
	 * 
	 * The available dates stream has two columns headed "Date" and "Slots".
	 * The Date column contains dates in YYYY-MM-DD format and identify a day when slots are available.
	 * The Slots column contains the number of slots available on the specified day.
	 *
	 * Returns an unsolved plan describing the data.
	 */
	public static Plan newPlanFromCSV(Reader activityReader, Reader availableDatesReader) {
		List<Activity> activities = Lists.newArrayList();
		List<Assignment> assignments = Lists.newArrayList();
		List<Day> assignmentDays = Lists.newArrayList();

		
		try {
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines().
					parse(activityReader);
			for (CSVRecord record : records) {
				String activityName = record.get(ACTIVITY_NAME_HEADING);
				String targetSessions = record.get(TARGET_SESSIONS_HEADING);
				
				Integer targetSessionAsInt = null;
				try {
					targetSessionAsInt = Integer.parseInt(targetSessions);
				} catch (NumberFormatException e) {
					// Ignore format errors, deal with them as missing values
				}
				
				if (activityName != null && activityName.length() > 0 &&
					targetSessionAsInt != null && targetSessionAsInt > 0) {
					Activity activity = new Activity(activityName); 
					activities.add(activity);
					for (int i = 0; i < targetSessionAsInt; i++) {
						assignments.add(new Assignment(activity));
					}
				}
			}
			
			records = CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines().parse(availableDatesReader);
			for (CSVRecord record : records) {
				String dateAsString = record.get(DATE_HEADING);
				String availableSlotsAsString = record.get(SLOTS_HEADING);
				
				LocalDate date = null;
				Integer availableSlots = null;
				try {
					date = LocalDate.parse(dateAsString);
					availableSlots = Integer.parseInt(availableSlotsAsString);
					
					// Final validation
					if (availableSlots > 0) {
						Day d = new Day(date, availableSlots);
						assignmentDays.add(d);
					}
				} catch (DateTimeParseException e) {
					// Ignore format errros
				} catch (NumberFormatException e) {
					// Ignore format errors
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Plan(activities, assignments, assignmentDays);
	}
}
