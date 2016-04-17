package me.phil.frequencyplanner.csvadapter;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import me.phil.frequencyplanner.csvadapter.PlanningProblemReader;
import me.phil.frequencyplanner.domain.Plan;

import org.junit.Test;

public class PlanningProblemReaderTest {

	@Test
	public void testNewPlanFromCSVFile() throws IOException {

		try (InputStream channelsStream = PlanningProblemReaderTest.class.getResourceAsStream("frequencyplanner-channels.csv");
			 InputStream assignmentsStream = PlanningProblemReaderTest.class.getResourceAsStream("frequencyplanner-assignments.csv")) {
			Plan unsolvedPlan = PlanningProblemReader.newPlanFromCSV(new InputStreamReader(assignmentsStream), 
					new InputStreamReader(channelsStream));

			assertThat("Plan contains assignments", unsolvedPlan.getFrequencyAssignments().size(), is(greaterThan(0)));
			assertThat("Plan contains channels", unsolvedPlan.getChannels().size(), is(greaterThan(0)));
		} 

	}

}
