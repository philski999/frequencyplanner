package me.phil.activityplanner.domain;

import java.time.LocalDate;

/**
 * A day when activity will take place
 */
public class Day {
	private LocalDate date;
	private int availableAssignmentSlots;
	
	public Day() {
	}
	
	public Day(LocalDate date, int availableAssignmentSlots) {
		super();
		this.date = date;
		this.availableAssignmentSlots = availableAssignmentSlots;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getAvailableAssignmentSlots() {
		return availableAssignmentSlots;
	}
}
