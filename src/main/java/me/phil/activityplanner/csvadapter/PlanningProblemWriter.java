package me.phil.activityplanner.csvadapter;

import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import me.phil.activityplanner.domain.Assignment;
import me.phil.activityplanner.domain.Day;
import me.phil.activityplanner.domain.Plan;

public class PlanningProblemWriter {
	public static void print(Plan plan, Appendable output) throws IOException {
		CSVPrinter printer = CSVFormat.DEFAULT.withHeader("Date", "AvailableSlots", "Session1",
				"Session2", "Session3", "Session4", "Session5").print(output);
		
        for (Day day : plan.getAssignmentDays()) {
        	printer.print(day.getDate().toString());
        	printer.print(day.getAvailableAssignmentSlots());
            for (Assignment assignment : plan.getAssignments()) {
            	if (day.equals(assignment.getDay())) {
            		printer.print(assignment.getAssignedActivity().getName());
            	}
            }
            printer.println();
        }
	}
}
