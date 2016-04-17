package me.phil.frequencyplanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import me.phil.frequencyplanner.csvadapter.PlanningProblemReader;
import me.phil.frequencyplanner.csvadapter.PlanningProblemWriter;
import me.phil.frequencyplanner.domain.Plan;

public class Planner {
    public static void main(String[] args) throws IOException {
        // Build the Solver
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(
                "me/phil/frequencyplanner/solverConfig.xml");
        Solver solver = solverFactory.buildSolver();

        Plan unsolvedPlan = null;
		try (InputStream channelsStream = Planner.class.getResourceAsStream("frequencyplanner-channels.csv");
			 InputStream assignmentsStream = Planner.class.getResourceAsStream("frequencyplanner-assignments.csv")) {
			unsolvedPlan = PlanningProblemReader.newPlanFromCSV(new InputStreamReader(assignmentsStream), 
					new InputStreamReader(channelsStream));
		}        
       
		System.out.println("Problem loaded, " + Integer.toString(unsolvedPlan.getFrequencyAssignments().size()) +
				" assignments, " + Integer.toString(unsolvedPlan.getChannels().size()) +
				" channels, solving...");
		
        // Solve the problem
        solver.solve(unsolvedPlan);
        Plan solvedPlan = (Plan) solver.getBestSolution();
        
        System.out.println("Final score = " + solvedPlan.getScore());

        PlanningProblemWriter.print(solvedPlan, System.out);
    }
}
