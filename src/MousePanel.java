import processing.core.PVector;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class MousePanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Mouse> mouseList = new CopyOnWriteArrayList<>();
    // private List<Mouse> mouseList = new ArrayList<>();
	private Timer t;
	private Dimension size;
	public boolean c = true;
	private static ControlPanel cPanel;
	public static Dimension pnlSize;
	private int mouseCount;
	private int catCount;
	private static String status = "Status";
	private Color backgroudColor = Color.gray;

	private List<MouseFood> foodList = new CopyOnWriteArrayList<>();
	// private List<Cat> catList = new ArrayList<>();
    private List<Cat> catList = new CopyOnWriteArrayList<>();
    
    
	public MousePanel(ControlPanel p, Dimension Initialsize) {
		super();
		cPanel = p;
		pnlSize = new Dimension(1060,600);
		this.setPreferredSize(pnlSize);

		size = pnlSize;
		for(int i = 0;i<5;i++) {
			generateMouse();
		}
		
		for(int i = 0; i < 2; i++) {
			generateCat();
		}
		
		t = new Timer (33, this);
		t.start();
		
		addMouseListener(new MyMouseAdapter());
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((e) -> {
			if (e.getKeyCode() == KeyEvent.VK_D && e.isShiftDown() && e.getID() == KeyEvent.KEY_PRESSED) {
					for(Mouse mouse: mouseList) {
						mouse.setShowInfo(!mouse.getShowInfo());
					}
				for (Cat cat : catList) {
					cat.setShowInfo(!cat.getShowInfo());
				}

			}
			return false;
		});


		
		//this method allows u to call a new food under a certain time.
//		Runnable r = new Runnable() {
//
//			@Override
//			public void run() {
//				while(true) {
//				try {
//					Thread.sleep((long)Util.random(500,2000));
//					MouseFood food = new MouseFood((int)Util.random(10,size.width - 10),(int)Util.random(10,size.height - 10) , 
//							   Math.min(size.width,size.height)/(int)Util.random(20, 40));
//								foodList.add(food);	
//				}
//				catch(Exception e) {
//					break;
//				}
//				}
//			}
//			
//		};
//		(new Thread(r)).start();
	}

	public String getStatus() {
		Animal animal = GameManager.getInstance().getSelectedAnimal();
		if(animal != null){
			String[] statusInfo = animal.getStatus();
			return statusInfo[0] + "/" + statusInfo[1] + "/" + statusInfo[2];
		}
		else{
			return "Status";
		}

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		size = getSize();
		pnlSize = getSize();
		setBackground(getBackgroudColor());
		Graphics2D g2 = (Graphics2D) g;
		
		for(int i = 0; i< foodList.size(); i ++) {
			foodList.get(i).draw(g2);
		}
		for(Mouse mouse: mouseList){
		   mouse.draw(g2);
		}
		for(Cat cat: catList) {
			cat.draw(g2);
		}
		cPanel.updatePanel(this);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		catList.forEach(cat->cat.move());
		mouseList.forEach(mouse->mouse.move());

		//1. check cat/mouse collision, if find, remove mouse, and update cat energy, that will impact cat size and speed.
		for(Mouse mouse: mouseList){
			for(Cat cat: catList){
				if(cat.collide(mouse)){
					cat.takeFood(mouse);
					mouseList.remove(mouse);
					mouseCount --;
					generateMouse();
				}
			}
		}

		//2. check mouse or cat energy, if <0, remove mouse or cat.
		//iterator creates a list that continues scan for every mouse in the list. 
		for(Iterator<Mouse> it = mouseList.iterator();it.hasNext();){
			Mouse m = it.next();
			if(!m.isAlive()){
				mouseList.remove(m);
				mouseCount --;
                generateMouse();
			}
		}

		for(Iterator<Cat> it = catList.iterator();it.hasNext();){
			Cat c = it.next();
			if(!c.isAlive()){
				catList.remove(c);
				catCount --;
                generateCat();
			}
		}

		//3. check mouse/food collision, if find, update mouse energy, that will impact mouse size and speed.
		for(Food mouseFood: foodList){
			for(Mouse mouse: mouseList){
				if (mouse.collide(mouseFood)){
					mouse.takeFood(mouseFood);
					foodList.remove(mouseFood);
					foodList.add(new MouseFood((int)Util.random(10, size.width - 10), (int)Util.random(10, size.height - 10),
							     Math.min(size.width,size.height)/(int)Util.random(20, 40)));
				}
			}
		}

		// check touch the wall, if touched bounce immediately.
		for(Mouse m : mouseList){
			int x = getX();
			if(x<=0 || x>=pnlSize.getWidth()){

			}
		}

		//4. check cat/cat collision, if find change cat speed angle, if not go to 4.1
		//4.1 check cat chaseFood mouse, adjust speed angle.
		//5. check mouse/mouse collision, if find change mouse speed angel, if not go 5.1.
		//5. check mouse chaseFood food, adjust speed angle.
		for( int i = 0;i<catList.size();i++){
			catList.get(i).makeMoveDicision(null,catList.subList(i+1,catList.size()),mouseList);
		}
		for( int i = 0;i<mouseList.size();i++){
			mouseList.get(i).makeMoveDicision(catList,mouseList.subList(i+1,mouseList.size()),foodList);
		}
		//colliableOrChaseFood(catList,mouseList);
		//colliableOrChaseFood(mouseList,foodList);

		repaint();
	}
	//T refers to subclass of Food, colliableOrChaseFood method need two lists, the list of subclass of animal and the list of subclass of food
	private <T extends Food> void colliableOrChaseFood(List<? extends Animal> animals, List<T> foods){
		for(int i = 0;i<animals.size();i++){

			if(animals.get(i).bounceOnWall(pnlSize)){
				continue;
			}

			//for animal within animal size, if collision is false
			boolean iscollision = false;
			Animal animal1 = animals.get(i);
			for(int j = i+1;j<animals.size();j++){
				//for animal start after the current animal
				Animal animal2  = animals.get(j);
				//if animal1 collide to animal 2, they bounce , assign collision to true.
				if(animal1.collide(animal2)){
					if (animal1.bounceIfNeed(animal2)) {
						iscollision = true;
						break;
					}
					//jump out of the loop
				}
			}
			//if collision is not true,animal 1 finds the closest food and chase
			if(!iscollision){
				T food = animal1.findClosedFood(foods);
				if(food != null){
					animal1.chaseFood(food);
				}
			}
		}
	}

	private class MyMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.isControlDown()) {
				for (MouseFood food : foodList) {
					if (food.checkMouseHit(e)) {
						foodList.remove(food);
					}
				}
			} else if (e.isShiftDown()) {
				mouseList.forEach(m -> {
					if (m.checkMouseHit(e)) {
						GameManager.getInstance().setSeletedAnimal(m);
						m.setShowInfo(!m.getShowInfo());
					}
				});
				catList.forEach(c -> {
					if (c.checkMouseHit(e)) {
						GameManager.getInstance().setSeletedAnimal(c);
						c.setShowInfo(!c.getShowInfo());
					}
				});

			} else {
				MouseFood food = new MouseFood(e.getX(), e.getY(),
						Math.min(size.width, size.height) / (int) Util.random(20, 40));
				foodList.add(food);
			}
		}
	}

	public int countMouse(Class<?> className) {
		if (className == Mouse.class) return Util.countMouse(className, mouseList);
		return Util.countMouse(className, mouseList);
	}
	public int countCat(Class<?> className) {
		if (className == Cat.class) return Util.countCat(className, catList);
		return Util.countCat(className, catList);
	}
	
	//randomize the speed, color of the eye, and the initial weight of the mouse
	public void generateMouse() {
		mouseList.add(Util.randomMouse(pnlSize));
		mouseCount++;
	}
	
	public void generateCat() {
		catList.add(Util.randomCat(pnlSize));
		catCount ++;
	}
	
	public void accelerateMouseSpeed(float accel) {
		for(Iterator<Mouse> it = mouseList.iterator();it.hasNext();) {
			it.next().setAccSpeed(accel);
		}
	}
	
	public void changeMouseFoodColor(Color newColor) {
		for(Iterator<MouseFood> it = foodList.iterator();it.hasNext();) {
			it.next().setBodyC(newColor);
		}
	}

	public Color getBackgroudColor() {
		return backgroudColor;
	}

	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

}
	
