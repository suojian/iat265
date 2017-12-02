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


