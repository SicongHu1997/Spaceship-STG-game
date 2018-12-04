import org.newdawn.slick.Input;

/** the class for background */
public class Background extends Sprite {
	// assume background as one kind of sprite which is moving downward
	/** speed of background */
	public static final float BACKGROUND_SPEED = 0.2f;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Background(float x, float y) {
		super("res/space.png", x, y);
	}
	public void update(Input input, int delta) {
		moveToDest(DIR_DOWN,BACKGROUND_SPEED*delta);
	}
}