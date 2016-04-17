package me.phil.frequencyplanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

import me.phil.frequencyplanner.csvadapter.PlanningProblemReader;
import me.phil.frequencyplanner.csvadapter.PlanningProblemWriter;
import me.phil.frequencyplanner.domain.Channel;
import me.phil.frequencyplanner.domain.FrequencyAssignment;
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
       
		Integer availableChannelBandwidth = 0;
		for (Channel channel : unsolvedPlan.getChannels()) {
			availableChannelBandwidth += channel.getAvailableBandwidthInkHz();
		}
		
		Integer bandwidthToAssign = 0 ;
		for (FrequencyAssignment assignment : unsolvedPlan.getFrequencyAssignments()) {
			bandwidthToAssign += assignment.getBandwidthInkHz();
		}
		
		System.out.println("Problem loaded.");
		System.out.println(Integer.toString(unsolvedPlan.getFrequencyAssignments().size()) + " assignments, " + 
				Integer.toString(unsolvedPlan.getChannels().size()) + " channels, " +
				availableChannelBandwidth.toString() + " available bandwidth (kHz), " +
				bandwidthToAssign.toString() + " bandwidth to assign (kHz)");
		System.out.println("Solving...");
		
        // Solve the problem
		SolverListener listener = new SolverListener();
		solver.addEventListener(listener);
		solver.solve(unsolvedPlan);
		
        Plan solvedPlan = (Plan) solver.getBestSolution();
        
		System.out.println("Solving complete, " + listener.getImprovementCount().toString() + " improvements made, " +
				listener.getMillisToFindBest().toString() + "mS to find the best solution.");	
        System.out.println("Final score = " + solvedPlan.getScore());

        PlanningProblemWriter.print(solvedPlan, System.out);
    }
    
    /**
     * Count the number of plan improvements made
     */
    private static class SolverListener implements SolverEventListener<Plan>
	{
		private Integer improvements = 0 ;
		private Long milliSecondsToFindBest = -1L;
		
		@Override
		public void bestSolutionChanged(BestSolutionChangedEvent<Plan> event) {
			milliSecondsToFindBest = event.getTimeMillisSpent();
			improvements++;
		}
		
		Integer getImprovementCount() {
			return improvements;
		}
		
		Long getMillisToFindBest() {
			return milliSecondsToFindBest;
		}
	}
}
