import org.newdawn.slick.Input;

/** class for basic enemy */
public class Enemy extends Sprite {
	/** enemy's speed*/
	public static final float ENEMY_SPEED = 0.2f;
	/** enemy's point*/
	public static final int ENEMY_POINT = 50;
	/** enemy's hp*/
	public static final int ENEMY_HP = 1;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param delay enemy delay
	 */
	public Enemy(float x, float y, int delay) {
		super("res/basic-enemy.png", x, y, ENEMY_POINT, delay);
		this.sethp(ENEMY_HP);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		moveToDest(DIR_DOWN,ENEMY_SPEED*delta);
	}
}