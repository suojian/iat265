package module;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;

import processing.core.PVector;

public abstract class Animal implements Collide{

	private int x;
	private int y;
	private int sizeX;
	private int sizeY;
	private Color eyeColor;
	private Color bodyColore;
	private PVector pos, speed;
	private float scale;
	private double energy;
	protected float maxSpeed;
	private long previousCollabTime = 0;
	private long previousEscapeTime = 0;
	private boolean showInfo = true;
	private float accSpeed = 1;
	private static PVector verticalWall = new PVector(0,60f),horizontalWall = new PVector(60f,0);
	private Color originalBodyColor;
	private double energyDecreaseRate;

	private double fullEnergy = 1000;
	
	public Animal (int x, int y, int sizeX, int sizeY,int speedX, int speedY, Color eyeColor, Color bodyColore, float scale, float initialSpeed, double energyDecreaseRate) {
		this.pos = new PVector(x,y);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.bodyColore = bodyColore;
		originalBodyColor = bodyColore;
		this.eyeColor = eyeColor;
		this.setScale(scale);
		maxSpeed = initialSpeed;
		this.speed = Util.randomPVector(maxSpeed);
		this.energyDecreaseRate = energyDecreaseRate;
	}

	public void setSelected(Color selectedColor){
		this.bodyColore = selectedColor;
	}

	public void deselected(){
		this.bodyColore = originalBodyColor;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public Color getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(Color eyeColor) {
		this.eyeColor = eyeColor;
	}

	public Color getBodyColore() {
		return bodyColore;
	}

	public void setBodyColore(Color bodyColore) {
		this.bodyColore = bodyColore;
	}

	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getSpeed() {
		return speed;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
		energyChanged(energy);
	}
	
	public void setShowInfo(final boolean showInfo) {
		this.showInfo = showInfo;
	}
	
	public boolean getShowInfo() {
		return showInfo;
	}
	
	protected abstract void energyChanged(double newEnergy);
	
	public void move() {
		getPos().add(getSpeed().mult(accSpeed));
		decreaseEnergy();
	}
	
	protected void decreaseEnergy() {
		setEnergy(energy - fullEnergy*energyDecreaseRate);
	}
	
	public boolean isAlive() {
		return energy > 0;
	}
	
	public abstract void draw(Graphics2D g);


	public abstract void takeFood(Food food);
	
	
	public <T extends Food> T findClosedFood(List<T> foodList) {
		if (foodList.size() == 0 ) {
			return null;
		}
		//get the current food as closestfood
		T closestFood = foodList.get(0);
		
		//set maxAF = the distance between current closest food and the mouse
		float maxAf = getFoodAf(closestFood, this);

		for (T f : foodList) {
		
			//set af - the distance between food in the foodlist and the mouse
			float af = getFoodAf(f, this);
		
			//compare distance between maxAf and af, if the maxAf's distance is less than af's distance, change the closest food to this food, change maxAf to this af
			if (maxAf < af) {
				closestFood = f;
				maxAf = af;
			}
		}
		return closestFood;
	}
	
	public void chaseFood(Food f) {
		PVector path = PVector.sub(f.getPos(), getPos());
		PVector accel = path.normalize();
		float mag = speed.mag();
		speed.add(accel);
		speed.setMag(mag);
	}

	//bounce function 
	public boolean bounceIfNeed(Animal animal) {
		boolean reflected = false;
		//now is the current time in millisecond
		long now = System.currentTimeMillis();
		//if this animal is smaller than the animal aside, and its not reflected within 1 second.
		if (this.getScale() <= animal.getScale() && now - previousCollabTime > 1000) {
			//set the colab time to now
			previousCollabTime = now;
			//reflect
			reflect(this.speed,animal.getSpeed());
			reflected = true;
		}
		return reflected;
	}


	public boolean bounceOnWall(Dimension activityRange){
		if(pos.x<=0 || pos.x >= activityRange.getWidth()){
			reflect(this.speed,Animal.verticalWall);
			return true;
		}
		else if(pos.y <=0 || pos.y>=activityRange.getHeight()){
			reflect(this.speed,Animal.horizontalWall);
			return true;
		}
		else {
			return false;
		}
	}


	//reflect when bounce, target is the speed that will be reflected, mirror is the speed that would not change
	private void reflect(PVector target, PVector mirror){
		float targetHeading = target.heading();
		float mirrorHeading = mirror.heading();
		//determine the angle of reflection.
		float delta = mirrorHeading - targetHeading;
		//rotate the target by 2 times delta
		target.rotate(2*delta);
	}
	
	
	private float getFoodAf(Food food,Animal animal) {
		return (float)food.getAttractionValue() / PVector.dist(animal.getPos(), food.getPos());
	}
	
	private String animalType() {
		String type = "unknown animal";
		if (this instanceof Mouse) type = "module.Mouse";
		else if (this instanceof Cat) type = "module.Cat";
		return type;
	}

	public String[] getStatus(){
		return new String[]{"Size     : " + String.format("%.2f", scale), "Speed  : " + String.format("%.2f", speed.mag()),"Energy : " + String.format("%.2f", energy) };
	}
	protected void drawInfo(Graphics2D g) {
		
		if(showInfo == true) {
			AffineTransform at = g.getTransform();
			g.translate(pos.x, pos.y);

			String[] statusInfo = getStatus();

			Font f = new Font("Courier", Font.PLAIN, 12);
			FontMetrics metrics = g.getFontMetrics(f);
			
			float textWidth = metrics.stringWidth(statusInfo[2]);
			float textHeight = metrics.getHeight();
			float margin = 12, spacing = 6;
			
			g.setColor(new Color(255,255,255,60));
			g.fillRect((int)(-textWidth/2-margin), 
					(int)(-getSizeY()*getScale()*.75f - textHeight*5f - spacing*4f - margin*2f), 
					(int)(textWidth + margin*2f), 
					(int)(textHeight*5f + spacing*4f + margin*2f));
			
			g.setColor(Color.blue.darker());
			g.drawString(this.animalType(), -metrics.stringWidth(this.animalType())/2,  -getSizeY()*getScale()*.75f - margin - (textHeight + spacing)*4f);
			g.setColor(Color.black);
			g.drawString(statusInfo[0], -textWidth/2, -getSizeY()*getScale()*.75f - margin - (textHeight + spacing)*2f);
			g.drawString(statusInfo[1], -textWidth/2, -getSizeY()*getScale()*.75f - margin - (textHeight + spacing)*1f);
			if (energy < fullEnergy*.3f) g.setColor(Color.red);
			g.drawString(statusInfo[2], -textWidth/2, -getSizeY()*getScale()*.75f - margin);
			g.setTransform(at);
		}else if(showInfo == false) {
//			AffineTransform at = g.getTransform();
//			g.translate(pos.x+50000, pos.y+50000);
//
//			String st0 = "Size     : " + String.format("%.2f", scale);
//			String st1 = "Speed  : " + String.format("%.2f", speed.mag());
//			String st2 = "Energy : " + String.format("%.2f", energy);
//
//			Font f = new Font("Courier", Font.PLAIN, 12);
//			FontMetrics metrics = g.getFontMetrics(f);
//
//			float textWidth = metrics.stringWidth(st2);
//			float textHeight = metrics.getHeight();
//			float margin = 12, spacing = 6;
//
//			g.setColor(new Color(255,255,255,60));
//			g.fillRect((int)(-textWidth/2-margin),
//					(int)(-getSizeY()*getScale()*.75f - textHeight*5f - spacing*4f - margin*2f),
//					(int)(textWidth + margin*2f),
//					(int)(textHeight*5f + spacing*4f + margin*2f));
//
//			g.setColor(Color.blue.darker());
//			g.drawString(this.animalType(), -metrics.stringWidth(this.animalType())/2,  -getSizeY()*getScale()*.75f - margin - (textHeight + spacing)*4f);
//			g.setColor(Color.black);
//			g.drawString(st0, -textWidth/2, -getSizeY()*getScale()*.75f - margin - (textHeight + spacing)*2f);
//			g.drawString(st1, -textWidth/2, -getSizeY()*getScale()*.75f - margin - (textHeight + spacing)*1f);
//			if (energy < FULL_ENERGY*.3f) g.setColor(Color.red);
//			g.drawString(st2, -textWidth/2, -getSizeY()*getScale()*.75f - margin);
//			g.setTransform(at);
		}
	}

	public float getAccSpeed() {
		return accSpeed;
	}

	public void setAccSpeed(float accSpeed) {
		this.accSpeed = accSpeed;
	}


	public boolean checkMouseHit(MouseEvent e) {
		return (Math.abs(e.getX() - pos.x) < sizeX/2) &&
				(Math.abs(e.getY() - pos.y) < sizeY/2);
	}

	public void makeMoveDicision( List<? extends Animal> predatorList,List<? extends Animal> otherAnimalList, List<? extends Food> foodList){
		if (bounceOnWall(MousePanel.pnlSize)){
			return;
		}
		if(predatorList!=null && predatorList.size() > 0 && this instanceof Visionable && System.currentTimeMillis() - previousEscapeTime > 1000){
			Visionable v = (Visionable)this;
			for (Animal pd: predatorList) {
				if(v.canSee(pd)){
					speed.rotate((float)Math.PI);
					previousEscapeTime = System.currentTimeMillis();
					return;
				}
			}
		}

		boolean iscollision = false;
		if(otherAnimalList != null && otherAnimalList.size() > 0){
			for(Animal animal2: otherAnimalList){
				//for animal start after the current animal
				//if animal1 collide to animal 2, they bounce , assign collision to true.
				if(this.collide(animal2)){
					if (this.bounceIfNeed(animal2)) {
						iscollision = true;
						break;
					}
					//jump out of the loop
				}
			}
		}

		if(!iscollision){
			Food food = findClosedFood(foodList);
			if(food != null){
				chaseFood(food);
			}
		}
	}

	public double getEnergyDecreaseRate() {
		return energyDecreaseRate;
	}

	public void setEnergyDecreaseRate(double energyDecreaseRate) {
		this.energyDecreaseRate = energyDecreaseRate;
	}

	public double getFullEnergy() {
		return fullEnergy;
	}

	public void setFullEnergy(double fullEnergy) {
		this.fullEnergy = fullEnergy;
	}
}
