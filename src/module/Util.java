package module;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import processing.core.PVector;

public class Util {

	public static double random(int min, int max) {
		return Math.random()*(max-min)+min;
	}
	
	public static double random(int max) {
		return Math.random()*max;
	}
	
	public static float random(double min, double max) {
		return (float) (Math.random()*(max-min)+min);
	}
	
	public static float random(double max) {
		return (float) (Math.random()*max);
	}
	
	public static Color randomColor() {
		int r = (int) random(255);
		int g = (int) random(255);
		int b = (int) random(255);
		
		return new Color(r,g,b);
	}
	
	public static PVector randomPVector(int maxX, int maxY) {		
		return new PVector((float)random(maxX), (float)random(maxY));	
	}
	
	public static PVector randomPVector(float magnitude) {
		return PVector.random2D().mult(magnitude);
	}
	
	public static Mouse randomMouse(Dimension pnlSize) {
		return new Mouse((int)Util.random(10, pnlSize.width - 10), (int)Util.random(10, pnlSize.height - 10), 
				Math.min(pnlSize.width,pnlSize.height)/(int)Util.random(5, 15), Math.min(pnlSize.width,pnlSize.height)/6, 
				(int)Util.random(-10, 10), (int)Util.random(-10, 10), 
				Util.randomColor());
	}
	
	public static Cat randomCat(Dimension pnlSize) {
		return new Cat((int)Util.random(10, pnlSize.width - 10), (int)Util.random(10, pnlSize.height - 10), 
				Math.min(pnlSize.width, pnlSize.height)/5, Math.min(pnlSize.width, pnlSize.height)/5,
				(int)Util.random(-10,10), (int)Util.random(-10,10),
				Util.randomColor());
	}
	public static int countMouse(Class<?> className, List<Mouse> mouseList) {
		int i = 0;
		for (Mouse obj:mouseList) if (className.isInstance(obj)) i++;
		return i;
	}
	public static int countCat(Class<?> className, List<Cat> catList) {
		int i = 0;
		for (Cat obj:catList) if (className.isInstance(obj)) i++;
		return i;
	}

}
