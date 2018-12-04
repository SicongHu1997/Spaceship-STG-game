import org.newdawn.slick.Input;

/** class for enemy laser */
public class EnemyLaser extends Sprite {
	/** enemy laser's speed*/
	public static final float ENEMY_LASER_SPEED = 0.7f;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public EnemyLaser(float x, float y) {
		super("res/enemy-shot.png", x, y);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		moveToDest(DIR_DOWN,ENEMY_LASER_SPEED*delta);
	}
}