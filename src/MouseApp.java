import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
//bonus animal gracefully turn around towards the new food source


//bonus the animal has a idea size, the smaller and larger scale has a slower speed, the medium scale has a faster speed
//bonus the animal will grow up after it reaches the threshold of 110 energy, and it has a ideal weight
public class MouseApp extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4878571304544463979L;

	public MouseApp (String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		createMenu();
		ControlPanel cPanel = new ControlPanel();
		MousePanel mainPanel = new MousePanel(cPanel, this.getSize());
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(cPanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);

	}

	private void createMenu(){
		JMenuBar menuBar;
		JMenu sysMenu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
		menuBar = new JMenuBar();

//Build the first menu.
		sysMenu = new JMenu("Mouse");
		sysMenu.getAccessibleContext().setAccessibleDescription(
				"Mouse system setup");
		menuBar.add(sysMenu);



//Mouse Menu sub menu
		menuItem = new JMenuItem("Existing Mouse Properties");
		sysMenu.add(menuItem);

		menuItem = new JMenuItem("Generate Mouse Properties");
		sysMenu.add(menuItem);

//Build second menu in the menu bar.
		sysMenu = new JMenu("Cat Menu");
		sysMenu.getAccessibleContext().setAccessibleDescription(
				"Cat system setup");
		menuBar.add(sysMenu);

// Cat menu sub menus
		menuItem = new JMenuItem("Existing Cat properties");
		sysMenu.add(menuItem);
		
		menuItem = new JMenuItem("Generate Cat Properties");
		sysMenu.add(menuItem);
		
// build Food menu
		sysMenu = new JMenu("Food Menu");
		sysMenu.getAccessibleContext().setAccessibleDescription("Food system setup");
		menuBar.add(sysMenu);
		
		menuItem = new JMenuItem("Existing Food properties");
		sysMenu.add(menuItem);
		
		menuItem = new JMenuItem("Generate Food properties");
		sysMenu.add(menuItem);

		this.setJMenuBar(menuBar);
	}


	


	public static void main(String[] args) {
		new MouseApp("My Mouse App");
	}
}
