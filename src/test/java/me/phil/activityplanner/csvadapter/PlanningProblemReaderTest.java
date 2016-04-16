package me.phil.activityplanner.csvadapter;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import me.phil.activityplanner.domain.Plan;

import org.junit.Test;

public class PlanningProblemReaderTest {

	@Test
	public void testNewPlanFromCSVFile() throws IOException {
		try (InputStream availabilityStream = getClass().getResourceAsStream("activityplanner-availability.csv");
			 InputStream targetsStream = getClass().getResourceAsStream("activityplanner-targets.csv")) {
			Plan plan = PlanningProblemReader.newPlanFromCSV(new InputStreamReader(targetsStream), 
					new InputStreamReader(availabilityStream));
			
			assertThat("Plan contains availability", plan.getAssignmentDays().size(), is(greaterThan(0)));
			assertThat("Plan contains targets", plan.getActivities().size(), is(greaterThan(0)));
		}
	}

}
