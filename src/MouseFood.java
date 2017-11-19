import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import processing.core.PVector;

public class MouseFood implements Food {

	private PVector pos;

	private Color bodyC = new Color(255,183,0);
	Color brown;
	private Ellipse2D.Double body;
	private RoundRectangle2D.Double root;
	Area bBox;
	private int size;
	//create a energy rate to change the amount of energy  according to the food size 
	private final static double ENERGY_CONVERT_RATE = 6;
	
	public MouseFood (int x, int y, int size) {
		this.pos = new PVector(x,y);
		this.size = size;
		this.setBodyC(bodyC);
		this.brown = new Color(190,159,0);
		setShapeAttributes();
	}
	
	private void setShapeAttributes() {
		 body = new Ellipse2D.Double(-size/2, -size/2, size, size);
		 root = new RoundRectangle2D.Double(-size/2 + 15, -size/2 -15, 5, 20, 4, 4);
	
	      bBox = new Area(body);
	      bBox.add(new Area(root));
	  }
	
	public void draw(Graphics2D g) {
		AffineTransform af = g.getTransform();
		
		g.translate((int)pos.x, (int)pos.y);
		setShapeAttributes();

		g.setColor(getBodyC());
		//draw body
		g.fill(body);
		g.setColor(brown);
		g.fill(root);
		
		g.setTransform(af);
	}
	
	
	public boolean checkMouseHit(MouseEvent e) {
		return (Math.abs(e.getX() - pos.x) < size/2) &&
				(Math.abs(e.getY() - pos.y) < size/2);
	}
	
	public PVector getPos() {
		return pos;
	}

	@Override
	public double getAttractionValue() {
		return getFoodEnergy();
	}

	//change the energy according the the food size
	public double getFoodEnergy() {
		return size * ENERGY_CONVERT_RATE;
	}

	@Override
	public Shape getBoundary() {
		AffineTransform at = new AffineTransform();
	    at.translate(getPos().x, getPos().y);
	    return at.createTransformedShape(bBox);
	}

	public Color getBodyC() {
		return bodyC;
	}

	public void setBodyC(Color bodyC) {
		this.bodyC = bodyC;
	}
	
}
