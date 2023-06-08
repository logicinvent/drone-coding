package com.drone.coding;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import com.drone.coding.model.Drone;
import com.drone.coding.model.Location;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
	
	static int total = 0;
	
	//joga o locaton na lista do drone ate acabar espaço
	// se faltar pouco percorre algum que possa ser adicionado a mais
	// verifica qual dropne coube mais
	// remove da lista os enviados e recomeça o processo

	public static void main(String[] args) {

		LinkedList<Drone> drones = getDrones();
		LinkedList<Location> locations = getLocations();

		while(trip(drones, locations));
		
	}
	
	private static boolean trip(LinkedList<Drone> drones, LinkedList<Location> locations) {
		
		for (Drone drone : drones) {

			if (drone.getLoadedWeight() <= drone.getMaximumWeight()) {

				for (int i = 0; i < locations.size(); i++) {
					
					for (int j = 0; j < locations.size(); j++) {
						if(!addPackage(locations.getFirst(), drone)) {
							break;
						}
						locations.remove(locations.getFirst());
					}
					
				}
			}
		}
		
		report(drones);
		unloadDrones(drones);
		
		return !locations.isEmpty();
	
	}
	
	private static void unloadDrones(LinkedList<Drone> drones) {
		drones.forEach(d -> d.unload());
	}

	private static void report(LinkedList<Drone> drones) {
		
		drones.forEach(d -> {
			
			if (!d.getLocations().isEmpty()) {
				log.info(d.getName() + " TOTAL DE PACOTES DO DRONE " + d.getLoadedWeight() + " TOTAL SUPORTADO " + d.getMaximumWeight());
				total++;
			}
			
			d.getLocations().stream().forEach(e -> {
				
					log.info("     -> " + e.getName() + " TOTAL DO PACOTE " + e.getPackageWeight());
				
			});
		});
		
		log.info("########## ROTAS: " +total);
		
	}

	static boolean addPackage(Location location, Drone drone) {

		if (drone.add(location))
			return Boolean.TRUE;

		return Boolean.FALSE;
	}

	static LinkedList<Location> getLocations() {

		return Arrays.asList(
				new Location("LocationA", 200.0), 
				new Location("LocationB", 150.0),
				new Location("LocationC", 50.0), 
				new Location("LocationD", 150.0), 
				new Location("LocationE", 100.0),
				new Location("LocationF", 200.0), 
				new Location("LocationG", 50.0), 
				new Location("LocationH", 80.0),
				new Location("LocationI", 70.0), 
				new Location("LocationJ", 50.0), 
				new Location("LocationK", 30.0),
				new Location("LocationL", 20.0), 
				new Location("LocationM", 50.0), 
				new Location("LocationN", 30.0),
				new Location("LocationO", 20.0), 
				new Location("LocationP", 90.0))
				.stream().sorted((a, b) -> a.getPackageWeight().compareTo(b.getPackageWeight()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	static LinkedList<Drone> getDrones() {

		return Arrays.asList(
				new Drone("DroneA", 200.0), 
				new Drone("DroneB", 250.0), 
				new Drone("DroneC", 100.0))
				.stream().sorted((a, b) -> b.getMaximumWeight().compareTo(a.getMaximumWeight()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

}
