import gui.ExistingCatPropertiesDialog;
import gui.ExistingFoodPropertiesDialog;
import gui.ExistingMousePropertiesDialog;
import gui.NewMousePropertiesDialog;
import module.ControlPanel;
import module.MousePanel;

import java.awt.*;

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



//module.Mouse Menu sub menu
		menuItem = new JMenuItem("Existing Mouse Properties");
		sysMenu.add(menuItem);
        menuItem.addActionListener((e)->{
			JDialog d = new ExistingMousePropertiesDialog(this,Dialog.ModalityType.APPLICATION_MODAL);
			d.setSize(300,300);
			d.setVisible(true);

        });

		menuItem = new JMenuItem("Generate Mouse Properties");
		menuItem.addActionListener((e)->{
			JDialog d = new NewMousePropertiesDialog(this,Dialog.ModalityType.APPLICATION_MODAL);
			d.setSize(300,300);
			d.setVisible(true);

		});
		sysMenu.add(menuItem);

//Build second menu in the menu bar.
		sysMenu = new JMenu("Cat");
		sysMenu.getAccessibleContext().setAccessibleDescription(
				"Cat system setup");
		menuBar.add(sysMenu);

// module.Cat menu sub menus
		menuItem = new JMenuItem("Existing Cat properties");
		menuItem.addActionListener((e)->{
			JDialog d2 = new ExistingCatPropertiesDialog(this,Dialog.ModalityType.APPLICATION_MODAL);
			d2.setSize(300,300);
			d2.setVisible(true);

        });
		sysMenu.add(menuItem);

		
		menuItem = new JMenuItem("Generate Cat Properties");
		sysMenu.add(menuItem);
		
// build module.Food menu
		sysMenu = new JMenu("Food");
		sysMenu.getAccessibleContext().setAccessibleDescription("Food system setup");
		menuBar.add(sysMenu);
		
		menuItem = new JMenuItem("Existing Food properties");
		menuItem.addActionListener((e)->{
			JDialog d2 = new ExistingFoodPropertiesDialog(this,Dialog.ModalityType.APPLICATION_MODAL);
			d2.setSize(300,300);
			d2.setVisible(true);

        });
		sysMenu.add(menuItem);
		
		menuItem = new JMenuItem("Generate Food properties");
		sysMenu.add(menuItem);

		this.setJMenuBar(menuBar);
	}


	


	public static void main(String[] args) {
		new MouseApp("My Mouse App");
	}
}
