package module;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class Cat extends Animal {

	private Ellipse2D.Double head;
	private RoundRectangle2D.Double body;
	private RoundRectangle2D.Double tail;
	private Ellipse2D.Double earL;
	private Ellipse2D.Double earR;
	private Area bBox;
	private static Color normalBodyColor = new Color(150, 50, 50);
	private static Color sickBodyColor = new Color(150, 50, 150);
	public Cat(int x, int y, int sizeX, int sizeY, int speedX, int speedY, Color eyeColor) {
		super(x, y, sizeX, sizeY, speedX, speedY, eyeColor, normalBodyColor, Util.random(0.15d, 0.35d));
		setShapeAttributes();
		setEnergy(FULL_ENERGY);
	}
    private boolean speedReduced = false;
    private double slowSpeedEngeryLevel = 0.3*FULL_ENERGY;

	@Override
	protected synchronized void energyChanged(double newEnergy) {
    	//if the new energy is larger than energy limit while speed reduced is ture, multiply the speed by 2
        if (newEnergy > slowSpeedEngeryLevel && speedReduced ){
            getSpeed().mult(2f);
            speedReduced = false;
            setBodyColore(normalBodyColor);
            System.out.println("speed up");
        }
        // if the new energy is lower than the energy limit while speed reduce is false, decrease the speed by 0.5
        else if(newEnergy < slowSpeedEngeryLevel && !speedReduced){
            getSpeed().mult(0.5f);
            speedReduced = true;
            setBodyColore(sickBodyColor);
            System.out.println("speed down");
        }
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform af = g.getTransform();
		g.translate((int) getPos().x, (int) getPos().y);
		g.rotate(Math.toRadians(90));
		g.rotate(getSpeed().heading());

		g.scale(getScale(), getScale());
		
		if (getSpeed().x < 0)
			g.scale(-1, 1);

		setShapeAttributes();

		g.setColor(getBodyColore());
		g.fill(body);
		g.fill(head);
		g.fill(earR);
		g.fill(earL);

		// g.setColor(new Color(255,0,0));
		g.fill(tail);
		g.setTransform(af);
		// g.setColor(Color.RED);
	    // g.draw(getBoundary().getBounds2D());
		
		drawInfo(g);

	}

	@Override
	public void takeFood(Food food) {
		double currentEnergy = getEnergy();
		double newEnergy = currentEnergy + food.getFoodEnergy();
		setEnergy(newEnergy);

	}

	public void setShapeAttributes() {
		body = new RoundRectangle2D.Double(-getSizeX() / 2 -10, -getSizeY(), 160, 230, 200, 200);
		head = new Ellipse2D.Double(-getSizeX() + 40, -getSizeY() - 50, getSizeX() * 1.5, getSizeY() * 1.2);
		earL = new Ellipse2D.Double(-getSizeX() + 45, getSizeY() - 330, getSizeX() / 2.3, getSizeY() * 0.8);
		earR = new Ellipse2D.Double(-getSizeX() + 165, getSizeY() - 330, getSizeX() / 2.3, getSizeY() * 0.8);
		tail = new RoundRectangle2D.Double(-getSizeX() / 2 + 40, -getSizeY() + 220, 50, 200, 70, 1000);

		bBox = new Area(body);
		bBox.add(new Area(head));
		bBox.add(new Area(earL));
		bBox.add(new Area(earR));
		bBox.add(new Area(tail));
	}

	@Override
	public Shape getBoundary() {
		AffineTransform at = new AffineTransform();
	      at.translate(getPos().x, getPos().y);
	      at.rotate(getSpeed().heading());
	      at.scale(getScale(),getScale());
	      return at.createTransformedShape(bBox);
	}
	
	@Override
	protected void decreaseEnergy() {
		double energy = getEnergy();
		double scale = getScale();
		float speed = getSpeed().mag();
		//reduce energy is base on amount of scale and speed. the larger the scale and higher the speed cause more energy
		double reducedEnergy = 3*scale + 2*speed;
		setEnergy(energy - reducedEnergy);
	}

}
