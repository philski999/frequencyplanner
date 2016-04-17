package me.phil.frequencyplanner.domain;

/**
 * A channel that frequency assignments can be assigned to
 */
public class Channel {
	private String name;
	private Integer startFrequencyInkHz;
	private Integer availableBandwidthInkHz;
	
	public Channel() {
	}
	
	public Channel(String channelName, Integer startFrequencyInkHz, Integer availableBandwidthInkHz) {
		super();
		this.name = channelName;
		this.startFrequencyInkHz = startFrequencyInkHz;
		this.availableBandwidthInkHz = availableBandwidthInkHz;
	}

	public String getName() {
		return name;
	}

	public int getStartFrequencyInkHz() {
		return startFrequencyInkHz;
	}
	
	public int getAvailableBandwidthInkHz() {
		return availableBandwidthInkHz;
	}
}
