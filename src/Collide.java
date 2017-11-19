import java.awt.Shape;

public interface Collide {
	public Shape getBoundary();
    public default boolean collide(Collide c) {
		return getBoundary().intersects(c.getBoundary().getBounds2D());
	}
}
