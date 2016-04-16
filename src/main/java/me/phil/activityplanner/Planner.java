package me.phil.activityplanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import me.phil.activityplanner.csvadapter.PlanningProblemReader;
import me.phil.activityplanner.csvadapter.PlanningProblemWriter;
import me.phil.activityplanner.domain.Plan;

public class Planner {
    public static void main(String[] args) throws IOException {
        // Build the Solver
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(
                "me/phil/activityplanner/solverConfig.xml");
        Solver solver = solverFactory.buildSolver();

        Plan unsolvedPlan = null;
		try (InputStream availabilityStream = Planner.class.getResourceAsStream("activityplanner-availability.csv");
			 InputStream targetsStream = Planner.class.getResourceAsStream("activityplanner-targets.csv")) {
			unsolvedPlan = PlanningProblemReader.newPlanFromCSV(new InputStreamReader(targetsStream), 
					new InputStreamReader(availabilityStream));
		}        
       
		System.out.println("Problem loaded, solving...");
		
        // Solve the problem
        solver.solve(unsolvedPlan);
        Plan solvedPlan = (Plan) solver.getBestSolution();
        
        System.out.println("Final score = " + solvedPlan.getScore());

        PlanningProblemWriter.print(solvedPlan, System.out);
    }
}
