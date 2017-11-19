import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
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
		ControlPanel cPanel = new ControlPanel();		
		MousePanel mainPanel = new MousePanel(cPanel, this.getSize());
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(cPanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}
	


	public static void main(String[] args) {
		new MouseApp("My Mouse App");
	}
}
