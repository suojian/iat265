package module;

import java.awt.*;

/**
 * Created by jsuo on 2017-11-30.
 */
public class GameManager {
    private static GameManager manager = new GameManager();
    private Animal selectedAnimal;
    private int maxMouseAllowed = 5;
    private double mouseAsFoodConversionRate = 0.5;
    private double mouseEnergyDecreaseRate = 0.003;
    private Color mouseBodyColorDefault = new Color(50,50,50);
    private MousePanel mousePanel;


    public static GameManager getInstance(){
        return manager;
    }

    public void setMousePanel(MousePanel mousePanel){
        this.mousePanel = mousePanel;
    }

    public void setSeletedAnimal(Animal animal){
        if(this.selectedAnimal != null){
            this.selectedAnimal.deselected();
        }
        this.selectedAnimal = animal;
        this.selectedAnimal.setSelected(Color.RED);

    }

    public Animal getSelectedAnimal(){
        return selectedAnimal;
    }


    public int getMaxMouseAllowed() {
        return maxMouseAllowed;
    }

    public void setMaxMouseAllowed(int maxMouseAllowed) {
        this.maxMouseAllowed = maxMouseAllowed;
    }

    public double getMouseAsFoodConversionRate() {
        return mouseAsFoodConversionRate;
    }

    public void setMouseAsFoodConversionRate(double mouseAsFoodConversionRate) {
        this.mouseAsFoodConversionRate = mouseAsFoodConversionRate;
    }

    public void updateMouseAsFoodRate(double rate){
        mousePanel.updateMouseAsFoodRate(rate);
    }

    public double getMouseEnergyDecreaseRate() {
        return mouseEnergyDecreaseRate;
    }

    public void setMouseEnergyDecreaseRate(double mouseEnergyDecreaseRate) {
        this.mouseEnergyDecreaseRate = mouseEnergyDecreaseRate;
    }

    public void updateMouseBodyColor(Color color){
        mousePanel.updateMouseBodyColor(color);
    }

    public void updateMouseEnergyDecreaseRate(double rate){
        mousePanel.updateMouseEnergyDecreaseRate(rate);
    }


    public Color getMouseBodyColorDefault() {
        return mouseBodyColorDefault;
    }

    public void setMouseBodyColorDefault(Color mouseBodyColorDefault) {
        this.mouseBodyColorDefault = mouseBodyColorDefault;
    }
}


