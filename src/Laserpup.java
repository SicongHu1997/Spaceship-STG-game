import org.newdawn.slick.Input;

/** class for laser powerup */
public class Laserpup extends Sprite {
	/** laser powerup's speed*/
	public static final float SPEED = 0.1f;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Laserpup(float x, float y) {
		super("res/shotspeed-powerup.png", x, y);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		moveToDest(DIR_DOWN,SPEED*delta);
	}
}