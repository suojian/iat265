package gui;

import module.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.Dialog.ModalityType;

public class ExistingCatPropertiesDialog extends JDialog {
    private String[] labels = {"Maximum Cat allowed: ", "Cat body color: ", "Enery decrease reate: "};
    private JTextField[] fields = {new JTextField(7),new JTextField(7),new JTextField(7),new JTextField(7)};
    private GameManager gameManager = GameManager.getInstance();
    public ExistingCatPropertiesDialog(Window owner, ModalityType modalityType){
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
        fields[0].setText(String.valueOf(gameManager.getMaxCatAllowed()));
        Color c = gameManager.getCatBodyColorDefault();
        //R G B
        fields[1].setText(Integer.toHexString(c.getRed())+Integer.toHexString(c.getGreen())+Integer.toHexString(c.getBlue()));
        fields[2].setText(String.valueOf(gameManager.getCatEnergyDecreaseRate()));
        SpringUtil.makeCompactGrid(p,
                labels.length, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);

}
    private void onApply() {
        gameManager.setMaxCatAllowed(Integer.valueOf(fields[0].getText()));
        String newColorStr = fields[1].getText();
        gameManager.updateCatBodyColor(new Color(Integer.parseInt(newColorStr.substring(0,2),16),Integer.parseInt(newColorStr.substring(2,4),16),Integer.parseInt(newColorStr.substring(4),16)));
        gameManager.updateCatEnergyDecreaseRate(Double.valueOf((fields[2].getText())));

    }

    private void onClose(){
        dispose();
    }
}
