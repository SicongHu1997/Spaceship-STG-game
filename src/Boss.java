import org.newdawn.slick.Input;

/** class for enemy Boss */
public class Boss extends Sprite {
	/** speed of enemy in stage 0 */
	public static final float ENEMY_SPEED = 0.05f;
	/** speed of enemy in stage 3 */
	public static final float ENEMY_SPEED_TWO = 0.2f;
	/** speed of enemy in stage 6 */
	public static final float ENEMY_SPEED_THREE = 0.1f;
	/** point of boss*/
	public static final int ENEMY_POINT = 5000;
	/** hp of boss*/
	public static final int ENEMY_HP = 60;
	/** max x coordinate when choose destination*/
	public static final int BOSS_MAX_X = 896;
	/** min x coordinate when choose destination*/
	public static final int BOSS_MIN_X = 128;
	/** time wait in stage 1*/
	public static final int WAIT_ONE = 5000;
	/** time wait in stage 4*/
	public static final int WAIT_TWO = 2000;
	/** time wait in stage 7*/
	public static final int WAIT_THREE = 3000;
	/** fire interval*/
	public static final int FIRE_TIME = 200;
	private int stage = 0;
	private float destination;
	private int fireTimer;
	/** constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param delay enemy delay
	 */
	public Boss(float x, float y, int delay) {
		super("res/boss.png", x, y, ENEMY_POINT, delay);
		this.fireTimer = FIRE_TIME;
		this.sethp(ENEMY_HP);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void update(Input input, int delta) {
		//the action of boss will be different in different stage 
		if (stage==0){
			moveToDest(DIR_DOWN,ENEMY_SPEED*delta);
			if(this.gety()>72){
				this.sety(72);
				this.stage=1;
			}
			return;
		}
		if (stage==1){
			this.setTimer(this.getTimer()+delta);
			if(this.getTimer()>WAIT_ONE){
				this.setTimer(0);
				stage = 2;
			}
			return;
		}
		if (stage==2){
			destination = (float)(BOSS_MIN_X + Math.random() * (BOSS_MAX_X - BOSS_MIN_X));
			stage = 3;
		}
		if (stage==3){
			if(destination>this.getx()){
				moveToDest(DIR_RIGHT,ENEMY_SPEED_TWO*delta);
				if(destination<this.getx()){
					this.setx(destination);
					stage = 4;
				}
			}
			else{
				moveToDest(DIR_LEFT,ENEMY_SPEED_TWO*delta);
				if(destination>this.getx()){
					this.setx(destination);
					stage = 4;
				}
			}
			return;
		}
		if (stage==4){
			this.setTimer(this.getTimer()+delta);
			if(this.getTimer()>WAIT_TWO){
				this.setTimer(0);
				stage = 5;
			}
			return;
		}
		if (stage==5){
			destination = (float)(BOSS_MIN_X + Math.random() * (BOSS_MAX_X - BOSS_MIN_X));
			stage = 6;
		}
		if (stage==6){
			this.setTimer(this.getTimer()+delta);
			fireTimer=fireTimer+delta;
			this.setFire(false);
			if(fireTimer>=FIRE_TIME){
				this.setFire(true);
				fireTimer=fireTimer-FIRE_TIME;
			}
			if(destination>this.getx()){
				moveToDest(DIR_RIGHT,ENEMY_SPEED_THREE*delta);
				this.setTimer(this.getTimer()+delta);
				if(destination<this.getx()){
					this.setx(destination);
					stage = 7;
				}
			}
			else{
				moveToDest(DIR_LEFT,ENEMY_SPEED_THREE*delta);
				this.setTimer(this.getTimer()+delta);
				if(destination>this.getx()){
					this.setx(destination);
					stage = 7;
				}
			}
			return;
		}
		if (stage==7){
			fireTimer=fireTimer+delta;
			this.setFire(false);
			if(fireTimer>=FIRE_TIME){
				this.setFire(true);
				fireTimer=fireTimer-FIRE_TIME;
			}
			this.setTimer(this.getTimer()+delta);
			if(this.getTimer()>WAIT_THREE){
				this.setTimer(0);
				this.setFire(false);
				stage = 1;
			}
			return;
		}
	}
}