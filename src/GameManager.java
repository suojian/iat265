import java.awt.*;

/**
 * Created by jsuo on 2017-11-30.
 */
public class GameManager {
    private static GameManager manager = new GameManager();
    private Animal selectedAnimal;

    public static GameManager getInstance(){
        return manager;
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

}
