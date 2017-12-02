package module;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import processing.core.PVector;

public class Mouse extends Animal implements Food,Visionable {
	private Ellipse2D.Double body;
	private Ellipse2D.Double eyeLeft;
	private Ellipse2D.Double eyeRight;
	private Arc2D.Double earLeft;
	private Arc2D.Double earRight;
	private Line2D.Double mustacheL1;
	private Line2D.Double mustacheL2;
	private Line2D.Double mustacheR1;
	private Line2D.Double mustacheR2;
	private RoundRectangle2D.Double tail;
	private Arc2D.Double fov;
	private Area bBox;
	private Area visionBox;
    private boolean speedReduced = false;
    private double slowSpeedEngeryLevel = 0.3*getFullEnergy();
	private double speedMin;
	private double speedMax;
	private GameManager gameManager = GameManager.getInstance();
	private float visionRangeRate = GameManager.getInstance().getMouseVisionRangeRate();
    
    private boolean showStatus = true;

	private double mouseAsFoodRate = GameManager.getInstance().getMouseAsFoodConversionRate();

	private Color earColor;
	public Mouse (int x, int y, int sizeX, int sizeY,int speedX, int speedY, Color eyeColor) {
		super(x,y,sizeX,sizeY,speedX,speedY,eyeColor,GameManager.getInstance().getMouseBodyColorDefault(),0.2f,GameManager.getInstance().getMouseMaxSpeed(),  GameManager.getInstance().getMouseEnergyDecreaseRate());
		setShapeAttributes();
		setEnergy(GameManager.getInstance().getMouseFullEnergy());
	}
	
	private void setShapeAttributes() {
		 body = new Ellipse2D.Double(-getSizeX()/2, -getSizeY()/2, getSizeX(), getSizeY());
		 eyeLeft = new Ellipse2D.Double(-getSizeX()/4, -getSizeY()/2 + 20, getSizeX()/8, getSizeX()/8);
		 eyeRight = new Ellipse2D.Double(+getSizeX()/6,  -getSizeY()/2 + 20, getSizeX()/8, getSizeX()/8);
		 earLeft = new Arc2D.Double(-getSizeX()/2+5, -getSizeY()/5, getSizeX()/3, getSizeY()/5, 140, 190,Arc2D.PIE);
		 earRight = new Arc2D.Double(getSizeX()/2-25, -getSizeY()/5, getSizeX()/3, getSizeY()/5, 40, -190,Arc2D.PIE);
		 mustacheL1 = new Line2D.Double(-getSizeX()/2 +25, -getSizeY()/2 +5, -getSizeX()/2, -getSizeY()/2 - 5);
		 mustacheL2 = new Line2D.Double(-getSizeX()/2 +25, -getSizeY()/2 +5, -getSizeX()/2, -getSizeY()/2 + 5);
		 mustacheR1 = new Line2D.Double(+getSizeX()/2 -25, -getSizeY()/2 +5, +getSizeX()/2, -getSizeY()/2 - 5);
		 mustacheR2 = new Line2D.Double(+getSizeX()/2 -25, -getSizeY()/2 +5, +getSizeX()/2, -getSizeY()/2 + 5);
		 tail = new RoundRectangle2D.Double(-getSizeX()/10 +5, -getSizeY()/2 + 112, 5, 70, 3, 3);
		 
		 // float sight = (float)Math.sqrt(getScale()) * maxSpeed * 500f;
		 float scale = getScale();
		 float sight = scale* maxSpeed * 300f*visionRangeRate;
		 if (scale > 0.4) {
			 sight = scale* maxSpeed * 200f;
		 }
		 if (scale > 0.5) {
			 sight = scale* maxSpeed * 100f;
		 }
		 
		 
		 fov = new Arc2D.Double(-sight, -sight, sight*2, sight*2, getSpeed().heading()+40, 110, Arc2D.PIE);
	  
	      bBox = new Area(body);
	      bBox.add(new Area(tail));
		visionBox = new Area(fov);
//	      bBox.add(new Area(mustacheR1));
//	      bBox.add(new Area(mustacheR2));
//	      bBox.add(new Area(mustacheL1));
//	      bBox.add(new Area(mustacheL2));

	  }

    @Override
    //Synchronized method, make sure speed only be reduced or recovered once.
    protected synchronized void energyChanged(double newEnergy) {
    	//if the new energy is larger than energy limit while speed reduced is ture, multiply the speed by 2
        if (newEnergy > slowSpeedEngeryLevel && speedReduced ){
            getSpeed().mult(2f);
            speedReduced = false;
            System.out.println("speed up");
        }
        // if the new energy is lower than the energy limit while speed reduce is false, decrease the speed by 0.5
        else if(newEnergy < slowSpeedEngeryLevel && !speedReduced){
            getSpeed().mult(0.5f);
            speedReduced = true;
            System.out.println("speed down");
        }
    }

    @Override
	public void draw(Graphics2D g ) {
		AffineTransform af = g.getTransform();
		g.translate((int)getPos().x, (int)getPos().y);
		g.rotate(Math.toRadians(90));
		g.rotate(getSpeed().heading());
		
		g.scale(getScale(), getScale());
		if(getSpeed().x < 0) g.scale(-1, 1);
		
		setShapeAttributes();
		//draw body
		g.setColor(getBodyColore());
		g.fill(body);
		
		//draw ear
		g.setColor(earColor);
		g.fill(earLeft);
		g.fill(earRight);
		//draw tail
		g.setColor(getBodyColore());
		g.fill(tail);
		
		//draw eye
		g.setColor(getEyeColor());
		g.fill(eyeLeft);
		g.fill(eyeRight);
		//draw mustache
		g.setColor(new Color(255,0,0));
		
		g.fill(mustacheL1);
		g.fill(mustacheL2);
		g.fill(mustacheR1);
		g.fill(mustacheR2);
		
		g.setColor(Color.red);
		g.draw(fov);
		
		g.setTransform(af);
		drawInfo(g);
		
//		g.setColor(Color.PINK);
//	    g.draw(getBoundary().getBounds2D());
	}

	@Override
	public void takeFood(Food food) {
		double currentEnergy = getEnergy();
		double newEnergy = currentEnergy + food.getFoodEnergy();
		if (newEnergy > getFullEnergy()*1.1) {
			setScale(getScale() + 0.1f);
            newEnergy = 900;
		}
		setEnergy(newEnergy);
		PVector speed = getSpeed();
		float scale = getScale();

		//decrease speed base on size
		float ratio = 1;
		if (scale < .5f) {
			ratio = 0.3f + 1.8f*scale;
		}
		else if (scale < 2) {
			ratio = 1f - 1.6f*(scale-0.6f);
		}
		else {
			ratio = .2f;
		}

		speed.mult(ratio);
		// System.out.println("Size: " + scale + "/ ratio: "+ ratio + "/ Speed: " + speed.mag());
	}


	public Shape getBoundary() {
	      AffineTransform at = new AffineTransform();
	      at.translate(getPos().x, getPos().y);
	      at.rotate(getSpeed().heading());
	      at.scale(getScale(), getScale());
	      return at.createTransformedShape(bBox);
	  }
	
	//amount energy that attract by the cat, cat maybe attract by mouse with larger energy
	@Override
	public double getAttractionValue() {
		return getFoodEnergy();
	}
	
	//food energy when mouse as a food
	@Override
	public double getFoodEnergy() {
		return getEnergy()*mouseAsFoodRate;
	}

	@Override
	public Shape getVision() {
		AffineTransform at = new AffineTransform();
		at.translate(getPos().x, getPos().y);
		at.rotate(getSpeed().heading());
		at.scale(getScale(), getScale());
		return at.createTransformedShape(visionBox);
	}

	public double getMouseAsFoodRate() {
		return mouseAsFoodRate;
	}

	public void setMouseAsFoodRate(double mouseAsFoodRate) {
		this.mouseAsFoodRate = mouseAsFoodRate;
	}
}
