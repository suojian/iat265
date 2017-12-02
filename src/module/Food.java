package module;

import processing.core.PVector;

public interface Food extends Collide {
	public PVector getPos();
	public double getAttractionValue();
	public double getFoodEnergy();
}
