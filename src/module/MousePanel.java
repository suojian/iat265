package module;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    // private List<module.Mouse> mouseList = new ArrayList<>();
	private Timer t;
	private Dimension size;
	public boolean c = true;
	private static ControlPanel cPanel;
	public static Dimension pnlSize;
	private int catCount;
	private static String status = "Status";
	private Color backgroudColor = Color.gray;

	private List<MouseFood> foodList = new CopyOnWriteArrayList<>();
	// private List<module.Cat> catList = new ArrayList<>();
    private List<Cat> catList = new CopyOnWriteArrayList<>();
    
    
	public MousePanel(ControlPanel p, Dimension Initialsize) {
		super();
		cPanel = p;
		pnlSize = new Dimension(1060,600);
		this.setPreferredSize(pnlSize);

		size = pnlSize;
		for(int i = 0;i<GameManager.getInstance().getMaxMouseAllowed();i++) {
			generateMouse();
		}
		
		for(int i = 0; i < GameManager.getInstance().getMaxCatAllowed(); i++) {
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
//					Thread.sleep((long)module.Util.random(500,2000));
//					module.MouseFood food = new module.MouseFood((int)module.Util.random(10,size.width - 10),(int)module.Util.random(10,size.height - 10) ,
//							   Math.min(size.width,size.height)/(int)module.Util.random(20, 40));
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
		GameManager.getInstance().setMousePanel(this);
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
		repaint();
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
		while (mouseList.size()<GameManager.getInstance().getMaxMouseAllowed()) {
			mouseList.add(Util.randomMouse(pnlSize));
		}
	}
	
	public void generateCat() {
		while(catList.size()<GameManager.getInstance().getMaxCatAllowed()) {
		catList.add(Util.randomCat(pnlSize));
		catCount ++;
		}
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

	public void updateMouseAsFoodRate( double rate){
		mouseList.forEach(m->m.setMouseAsFoodRate(rate));
	}

	public void updateMouseBodyColor( Color color){
		mouseList.forEach(m->m.setBodyColore(color));
	}
	
	public void updateCatBodyColor( Color color) {
		catList.forEach(m->m.setBodyColore(color));
	}
	
	public void updateFoodBodyColor( Color color) {
		foodList.forEach(m->m.setBodyC(color));
	}

	public void updateMouseEnergyDecreaseRate( double rate){
		mouseList.forEach(m->m.setEnergyDecreaseRate(rate));
	}
	
	public void updateCatEnergyDecreaseRate( double rate) {
		catList.forEach(m->m.setEnergyDecreaseRate(rate));
	}
	
}
	
