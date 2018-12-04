import org.newdawn.slick.Input;

/** class for player*/
public class Player extends Sprite {
	/** player's speed*/
	public static final float PLAYER_SPEED = 0.5f;
	/** fire interval*/
	public static final int FIRE_TIME = 350;
	private int ftime;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Player(float x, float y) {
		super("res/spaceship.png", x, y);
		ftime = FIRE_TIME;
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		int dir = DIR_NONE;
		this.setFire(false);
		if(this.getTimer()<ftime){
			this.setTimer(this.getTimer()+delta);
		}
		else{
			this.setTimer(ftime);
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			dir = DIR_LEFT;
		}
		else if (input.isKeyDown(Input.KEY_RIGHT)) {
			dir = DIR_RIGHT;
		}
		else if (input.isKeyDown(Input.KEY_UP)) {
			dir = DIR_UP;
		}
		else if (input.isKeyDown(Input.KEY_DOWN)) {
			dir = DIR_DOWN;
		}
		if((input.isKeyDown(Input.KEY_SPACE))&&(this.getTimer()>=ftime)) {
			this.setFire(true);
			this.setTimer(0);
		}
		if (input.isKeyPressed(Input.KEY_SPACE)){
			this.setFire(true);
		}
		moveToDest(dir,PLAYER_SPEED*delta);
	}
	public void setFtime(int ftime){
		this.ftime = ftime;
	}
}