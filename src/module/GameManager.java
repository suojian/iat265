package module;

import java.awt.*;

public class GameManager {
    private static GameManager manager = new GameManager();
    private Animal selectedAnimal;
    private int maxMouseAllowed = 5;
    private int maxCatAllowed = 2;
    private double mouseAsFoodConversionRate = 0.5;
    private int FoodConversionRate = 6;
    private double mouseEnergyDecreaseRate = 0.003;
    private double catEnergyDecreaseRate = 0.003;
    private Color mouseBodyColorDefault = new Color(50,50,50);
    private Color catBodyColorDefault = new Color(150,50,50);
    private Color foodColorDefault = new Color(255,183,0);
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
    
    public int getMaxCatAllowed() {
    		return maxCatAllowed;
    }
    
    public void setMaxCatAllowed(int maxCatAllowed) {
    		this.maxCatAllowed = maxCatAllowed;
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
    
    public double getCatEnergyDecreaseRate() {
    		return catEnergyDecreaseRate;
    }

    public void setMouseEnergyDecreaseRate(double mouseEnergyDecreaseRate) {
        this.mouseEnergyDecreaseRate = mouseEnergyDecreaseRate;
    }
    
    public void setCatEnergyDecreaseRate(double catEnergyDecreaseRate) {
    		this.catEnergyDecreaseRate = catEnergyDecreaseRate;
    }

    public void updateMouseBodyColor(Color color){
        mousePanel.updateMouseBodyColor(color);
    }
    
    public void updateCatBodyColor(Color color) {
    		mousePanel.updateCatBodyColor(color);
    }
    
    public void updateFoodBodyColor(Color color) {
    		mousePanel.updateFoodBodyColor(color);
    }

    public void updateMouseEnergyDecreaseRate(double rate){
        mousePanel.updateMouseEnergyDecreaseRate(rate);
    }
    
    public void updateCatEnergyDecreaseRate(double rate) {
    		mousePanel.updateCatEnergyDecreaseRate(rate);
    }


    public Color getMouseBodyColorDefault() {
        return mouseBodyColorDefault;
    }
    
    public Color getCatBodyColorDefault() {
    		return catBodyColorDefault;
    }
    
    public Color getFoodBodyColorDefault() {
    		return foodColorDefault;
    }

    public void setMouseBodyColorDefault(Color mouseBodyColorDefault) {
        this.mouseBodyColorDefault = mouseBodyColorDefault;
    }
    
    public void setCatBodyColorDefault(Color catBodyColorDefault)	 {
    		this.catBodyColorDefault = catBodyColorDefault;
    }
    
    public void setFoodBodyColorDefault(Color foodColorDefault) {
    		this.foodColorDefault = foodColorDefault;
    }
    
}


