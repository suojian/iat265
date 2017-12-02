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
    private Color mouseBodyColorDefault = new Color(50,50,50);
    private Color catBodyColorDefault = new Color(150,50,50);
    private Color foodColorDefault = new Color(255,183,0);
    private MousePanel mousePanel;
    private double[] maxMouseSpeedRange = {3.0,5.0};
    private double[] maxCatSpeedRange = {3.0,5.0};
    private int mouseFullEnergy = 1000;
    private int catFullEnergy = 1000;
    private float mouseVisionRangeRate = 1.0f;
    private double catEnergyDecreaseRate = 0.003;

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

    public void setMouseEnergyDecreaseRate(double mouseEnergyDecreaseRate) {
        this.mouseEnergyDecreaseRate = mouseEnergyDecreaseRate;
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
    

    public double[] getMaxMouseSpeedRange() {
        return maxMouseSpeedRange;
    }

    public void setMaxMouseSpeedRange(double[] maxMouseSpeedRange) {
        this.maxMouseSpeedRange = maxMouseSpeedRange;
    }

    public double[] getMaxCatSpeedRange() {
        return maxCatSpeedRange;
    }

    public void setMaxCatSpeedRange(double[] maxCatSpeedRange) {
        this.maxCatSpeedRange = maxCatSpeedRange;
    }

    public float getMouseMaxSpeed(){
        return Util.random(maxMouseSpeedRange[0],maxMouseSpeedRange[1]);
    }

    public float getCatMaxSpeed(){
        return Util.random(maxCatSpeedRange[0],maxCatSpeedRange[1]);
    }


    public int getMouseFullEnergy() {
        return mouseFullEnergy;
    }

    public void setMouseFullEnergy(int mouseFullEnergy) {
        this.mouseFullEnergy = mouseFullEnergy;
    }

    public int getCatFullEnergy() {
        return catFullEnergy;
    }

    public void setCatFullEnergy(int catFullEnergy) {
        this.catFullEnergy = catFullEnergy;
    }

    public float getMouseVisionRangeRate() {
        return mouseVisionRangeRate;
    }

    public void setMouseVisionRangeRate(float mouseVisionRangeRate) {
        this.mouseVisionRangeRate = mouseVisionRangeRate;
    }

    public double getCatEnergyDecreaseRate() {
        return catEnergyDecreaseRate;
    }

    public void setCatEnergyDecreaseRate(double catEnergyDecreaseRate) {
        this.catEnergyDecreaseRate = catEnergyDecreaseRate;
    }
}


