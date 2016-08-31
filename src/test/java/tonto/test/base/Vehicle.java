package tonto.test.base;

import java.util.List;

public class Vehicle {
	
	private String name;
	
	private List<Wheel> wheels;
	
	private Engin engin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Wheel> getWheels() {
		return wheels;
	}

	public void setWheels(List<Wheel> wheels) {
		this.wheels = wheels;
	}

	public Engin getEngin() {
		return engin;
	}

	public void setEngin(Engin engin) {
		this.engin = engin;
	}
	
	
	
}
