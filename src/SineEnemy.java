import org.newdawn.slick.Input;

/** class for sine enemy*/
public class SineEnemy extends Sprite {
	/** sine enemy's speed*/
	public static final float ENEMY_SPEED = 0.15f;
	/** sine enemy's point*/
	public static final int ENEMY_POINT = 100;
	/** amplitude in the equation for sine enemy*/
	public static final int AMPLITUDE = 96;
	/** period in the equation for sine enemy*/
	public static final int PERIOD = 1500;
	/** sine enemy's hp*/
	public static final int ENEMY_HP = 1;
	public final float x_start;
	
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param delay enemy delay
	 */
	public SineEnemy(float x, float y, int delay) {
		super("res/sine-enemy.png", x, y, ENEMY_POINT, delay);
		this.x_start = x;
		this.sethp(ENEMY_HP);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		moveToDest(DIR_DOWN,ENEMY_SPEED*delta);
		this.setTimer(this.getTimer()+delta);
		this.setx((float)(x_start+AMPLITUDE*Math.sin(2*Math.PI*this.getTimer()/PERIOD)));
	}
}