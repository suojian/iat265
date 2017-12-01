import java.awt.*;

/**
 * Created by jsuo on 2017-11-30.
 */
public interface Visionable {

    public Shape getVision();
    public default boolean canSee(Collide c){
        return getVision().intersects(c.getBoundary().getBounds2D());
    }
}
