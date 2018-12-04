import org.newdawn.slick.Input;

/** class for player's laser */
public class Laser extends Sprite {
	/** laser's speed*/
	public static final float LASER_SPEED = 3;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Laser(float x, float y) {
		super("res/shot.png", x, y);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		moveToDest(DIR_UP,LASER_SPEED*delta);
	}
}