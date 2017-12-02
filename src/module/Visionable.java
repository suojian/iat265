package module;

import java.awt.*;


public interface Visionable {

    public Shape getVision();
    public default boolean canSee(Collide c){
        return getVision().intersects(c.getBoundary().getBounds2D());
    }
}
