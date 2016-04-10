package me.phil.activityplanner;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import me.phil.activityplanner.domain.Assignment;
import me.phil.activityplanner.domain.Day;
import me.phil.activityplanner.domain.Plan;

public class Planner {
    public static void main(String[] args) {
        // Build the Solver
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(
                "me/phil/activityplanner/solverConfig.xml");
        Solver solver = solverFactory.buildSolver();

        Plan unsolvedPlan = Plan.newUnsolvedPlan();
        
        // Solve the problem
        solver.solve(unsolvedPlan);
        Plan solvedPlan = (Plan) solver.getBestSolution();
        
        System.out.println("Final score = " + solvedPlan.getScore());

        for (Day day : solvedPlan.getAssignmentDays()) {
        	System.out.println(day.getDate().toString() + ": availableSlots=" + day.getAvailableAssignmentSlots());
            for (Assignment assignment : solvedPlan.getAssignments()) {
            	if (day.equals(assignment.getDay())) {
                	System.out.println("> " + assignment.getAssignedActivity().getName() + "(" + assignment.getDay().getDate() + ")");
            	}
            } 	
        }
    }
}
