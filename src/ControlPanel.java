import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ControlPanel extends JPanel {
	private MousePanel mousePanel;
	private JLabel status;
	private Container bottomContainer;
	private JButton addMouse;
	private JButton addCat;
	private JButton insC;
	private JButton screenColor;
	private JButton foodColor;

	private Container topContainer;
	private JLabel mouseNumLabel;
	private JTextField mouseNumTextField;

	private JLabel catNumLabel;
	private JTextField catNumTextField;

	private Container centerContainer;

	public ControlPanel() {
		super();
		setComponentAttributes();
		setLayout(new BorderLayout());
		add(bottomContainer, BorderLayout.SOUTH);
		add(topContainer, BorderLayout.NORTH);
		add(centerContainer, BorderLayout.CENTER);
	}

	private void setComponentAttributes() {
		status = new JLabel("Status...");
		bottomContainer = new Container();
		// flow layout allow to add multiple component together
		bottomContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		bottomContainer.add(status);

		// 2nd step
		// add mouse
		addMouse = new JButton("Add a new Mouse");
		addMouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mousePanel.generateMouse();
			}
		});
		topContainer = new Container();
		topContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		topContainer.add(addMouse);

		// add cat
		addCat = new JButton("Add a new Cat");
		addCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mousePanel.generateCat();
			}
		});
		topContainer.add(addCat);

		// increase mouse speed
		insC = new JButton("Increase Mouse Speed");
		insC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mousePanel.accelerateMouseSpeed(1.1f);
			}
		});
		topContainer.add(insC);
		
		//screenColor
		screenColor = new JButton("Change Screen Color");
		screenColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mousePanel.setBackgroudColor(Util.randomColor());
			}
		});
		topContainer.add(screenColor);
		
		//food color
		foodColor = new JButton("Change Food Color");
		foodColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mousePanel.changeMouseFoodColor(Util.randomColor());
			}
		});
		topContainer.add(foodColor);

		// 3rd step
		mouseNumLabel = new JLabel("Number of Mouse:");
		mouseNumTextField = new JTextField(5);
		mouseNumTextField.setHorizontalAlignment(SwingConstants.CENTER);
		mouseNumTextField.setFocusable(false);
		centerContainer = new Container();
		centerContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		centerContainer.add(mouseNumLabel);
		centerContainer.add(mouseNumTextField);
		catNumLabel = new JLabel("Number of Cat:");
		catNumTextField = new JTextField(5);
		catNumTextField.setHorizontalAlignment(SwingConstants.CENTER);
		catNumTextField.setFocusable(false);
		centerContainer.add(catNumLabel);
		centerContainer.add(catNumTextField);
	}

	public void updatePanel(MousePanel p) {
		mousePanel = p;
		status.setText(p.getStatus());
		mouseNumTextField.setText(String.format("%d", p.countMouse(Mouse.class)));

		catNumTextField.setText(String.format("%d", p.countCat(Cat.class)));
	}

}
