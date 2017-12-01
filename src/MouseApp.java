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
		sysMenu = new JMenu("Game");
		sysMenu.setMnemonic(KeyEvent.VK_G);
		sysMenu.getAccessibleContext().setAccessibleDescription(
				"The game system setup");
		menuBar.add(sysMenu);



//a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item",
				KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		sysMenu.add(menuItem);

		menuItem = new JMenuItem("Both text and icon",
				new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		sysMenu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		sysMenu.add(menuItem);

//a group of radio button menu items
		sysMenu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		sysMenu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		sysMenu.add(rbMenuItem);

//a group of check box menu items
		sysMenu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		sysMenu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		sysMenu.add(cbMenuItem);

//a submenu
		sysMenu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		sysMenu.add(submenu);

//Build second menu in the menu bar.
		sysMenu = new JMenu("Another Menu");
		sysMenu.setMnemonic(KeyEvent.VK_N);
		sysMenu.getAccessibleContext().setAccessibleDescription(
				"This menu does nothing");
		menuBar.add(sysMenu);


		this.setJMenuBar(menuBar);
	}


	


	public static void main(String[] args) {
		new MouseApp("My Mouse App");
	}
}
