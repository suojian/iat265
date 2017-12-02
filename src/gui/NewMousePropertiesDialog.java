package gui;

import module.GameManager;

import javax.swing.*;
import java.awt.*;


public class NewMousePropertiesDialog extends JDialog {
    private String[] labels = {"Mouse min speed: ", "Mouse max speed:", "Mouse max energy:" , "Mouse vision range rate: ", "Mouse as food conversion rate: ", "Mouse body color: ", "Energy decrease reate: "};
    private JTextField[] fields = {new JTextField(7),new JTextField(7),new JTextField(7),new JTextField(7),new JTextField(7),new JTextField(7),new JTextField(7)};
    private GameManager gameManager = GameManager.getInstance();
    public NewMousePropertiesDialog(Window owner, ModalityType modalityType){
        super(owner,modalityType);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel pcontrol = new JPanel();
        pcontrol.setLayout(new FlowLayout());
        JButton saveButton = new JButton("Apply");
        JButton closeButton = new JButton("Close");
        saveButton.addActionListener((e)->{
            onApply();
        });
        closeButton.addActionListener(e->{
            onClose();
        });
        pcontrol.add(saveButton);
        pcontrol.add(closeButton);
        JPanel p = new JPanel();
        p.setLayout(new SpringLayout());
        add(p,BorderLayout.CENTER);
        add(pcontrol,BorderLayout.SOUTH);

        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            l.setLabelFor(fields[i]);
            p.add(fields[i]);

        }
        double[] maxSpeedRange = gameManager.getMaxMouseSpeedRange();
        fields[0].setText(String.valueOf(maxSpeedRange[0]));
        fields[1].setText(String.valueOf(maxSpeedRange[1]));
        fields[2].setText(String.valueOf(gameManager.getMouseFullEnergy()));
        fields[3].setText(String.valueOf(gameManager.getMouseVisionRangeRate()));
        fields[4].setText(String.valueOf(gameManager.getMouseAsFoodConversionRate()));
        Color c = gameManager.getMouseBodyColorDefault();
        fields[5].setText(Integer.toHexString(c.getRed())+Integer.toHexString(c.getGreen())+Integer.toHexString(c.getBlue()));
        fields[6].setText(String.valueOf(gameManager.getMouseEnergyDecreaseRate()));
        SpringUtil.makeCompactGrid(p,
                labels.length, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);

    }

    private void onApply() {
        gameManager.setMaxMouseSpeedRange(new double[]{Double.valueOf(fields[0].getText()),Double.valueOf(fields[1].getText())});
        gameManager.setMouseFullEnergy(Integer.valueOf(fields[2].getText()));
        gameManager.setMouseVisionRangeRate(Float.valueOf(fields[3].getText()));
        gameManager.setMouseAsFoodConversionRate(Double.valueOf(fields[4].getText()));
        String newColorStr = fields[5].getText();
        gameManager.setMouseBodyColorDefault(new Color(Integer.parseInt(newColorStr.substring(0,2),16),Integer.parseInt(newColorStr.substring(2,4),16),Integer.parseInt(newColorStr.substring(4),16)));
        gameManager.setMouseEnergyDecreaseRate(Double.valueOf((fields[6].getText())));

    }

    private void onClose(){
        dispose();
    }


}

