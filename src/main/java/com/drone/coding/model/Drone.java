package com.drone.coding.model;

import java.util.LinkedList;

import lombok.Getter;

@Getter
public class Drone {

	private String name;
	private Double maximumWeight;
	private LinkedList<Location> locations = new LinkedList<Location>();

	public Drone(String name, Double maximumWeight) {
		super();
		this.name = name;
		this.maximumWeight = maximumWeight;
	}

	public boolean add(Location location) {
		
		Double loadedWeight = getLoadedWeight();
		loadedWeight+=location.getPackageWeight();
		
		if (loadedWeight<= getMaximumWeight())
			return locations.add(location);

		return Boolean.FALSE;
		
	}

	public Double getLoadedWeight() {
		return locations.stream().mapToDouble(Location::getPackageWeight).sum();
	}

	public void unload() {
		locations.clear();
	}

}
