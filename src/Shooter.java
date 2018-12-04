import org.newdawn.slick.Input;

/** class for enemy shooter*/
public class Shooter extends Sprite {
	/** shooter's speed*/
	public static final float ENEMY_SPEED = 0.2f;
	/** shooter's point*/
	public static final int ENEMY_POINT = 200;
	/** shooter's max y-coordinate when choose destination*/
	public static final int SHOOTER_MAX_Y = 464;
	/** shooter's min y-coordinate when choose destination*/
	public static final int SHOOTER_MIN_Y = 48;
	/** shooter's fire interval*/
	public static final int SHOOTER_FIRE = 3500;
	/** shooter's hp*/
	public static final int ENEMY_HP = 1;
	//y-coordinate of the destination
	private float destination;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param delay enemy delay
	 */
	public Shooter(float x, float y, int delay) {
		super("res/basic-shooter.png", x, y, ENEMY_POINT, delay);
		destination = (float)(SHOOTER_MIN_Y + Math.random() * (SHOOTER_MAX_Y - SHOOTER_MIN_Y));
		this.setTimer(SHOOTER_FIRE);
		this.sethp(ENEMY_HP);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		if(this.gety()<destination){
			moveToDest(DIR_DOWN,ENEMY_SPEED*delta);
		}
		if(this.gety()>=destination){
			this.setFire(false);
			this.sety(destination);
			this.setTimer(this.getTimer()+delta);
			if(this.getTimer()>=SHOOTER_FIRE){
				this.setFire(true);
				this.setTimer(this.getTimer()-SHOOTER_FIRE);
			}
		}
	}
}