import org.newdawn.slick.Input;

/** class for shield power up*/
public class Shieldpup extends Sprite {
	/** shield powerup speed*/
	public static final float SPEED = 0.1f;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Shieldpup(float x, float y) {
		super("res/shield-powerup.png", x, y);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		moveToDest(DIR_DOWN,SPEED*delta);
	}
}