import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/** the parent class for any type of sprite in this game */
public abstract class Sprite {
	/** no direction*/
	public static final int DIR_NONE = 0;
	/** direction left*/
	public static final int DIR_LEFT = 1;
	/** direction right*/
	public static final int DIR_RIGHT = 2;
	/** direction up*/
	public static final int DIR_UP = 3;
	/** direction down*/
	public static final int DIR_DOWN = 4;
	/** size of the background*/
	public static final int BACKGROUND_SIZE = 512;
	
	private Image image = null;
	private float x;
	private float y;
	private int point;
	private int timer;
	private Boolean fire;
	private int hp;
	private int delay;
	/**
	 * constructor
	 * @param imageSrc the link to the image
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Sprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.fire = false;
		this.timer = 0;
		this.hp = 1;
		this.delay = 0;
		this.point = 0;
	}
	/**
	 * constructor
	 * @param imageSrc the link to the image
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param point enemy point
	 * @param delay enemy delay
	 */
	public Sprite(String imageSrc, float x, float y, int point, int delay) {
		this(imageSrc, x, y);
		this.point = point;
		this.delay = delay;
	}
	
	/**
	 * @param input keyboard input
	 * @param delta time from last update
	 */
	public void update(Input input, int delta) {
	}
	/**
	 * @param g graphic
	 */
	public void render(Graphics g) {
		image.drawCentered(x, y);
	}
	
	/**
	 * check contact
	 * @param other another sprite
	 * @return if they are contact or not
	 */
	public boolean contactSprite(Sprite other) {
		float leftA;
		float topA;
		float widthA = this.getimage().getWidth();
		float heightA = this.getimage().getHeight();
		float leftB;
		float topB;
		float widthB = other.getimage().getWidth();
		float heightB = other.getimage().getHeight();;
		
		leftA = this.getx()-widthA/2;
		topA = this.gety()-heightA/2;
		leftB = other.getx()-widthB/2;
		topB = other.gety()-heightB/2;
		
		return !(leftB > leftA + widthA
				  || leftB+widthB  < leftA
				  || topB > topA+heightA
				  || topB+heightB < topA);
	}
	
	//getter and setter
	/**
	 * @return x-coordinate 
	 */
	public float getx(){
		return this.x;
	}
	/**
	 * @return y-coordinate 
	 */
	public float gety(){
		return this.y;
	}
	/**
	 * @return delay 
	 */
	public int getDelay(){
		return this.delay;
	}
	/**
	 * @return hp 
	 */
	public int gethp(){
		return this.hp;
	}
	/**
	 * @param hp enemy's hp 
	 */
	public void sethp(int hp){
		this.hp = hp;
	}
	/**
	 * @return sprite's image 
	 */
	public Image getimage(){
		return this.image;
	}
	/**
	 * @return enemy's point 
	 */
	public int getPoint(){
		return this.point;
	}
	/**
	 * @return fire or not
	 */
	public Boolean getFire(){
		return this.fire;
	}
	/**
	 * @return timer
	 */
	public int getTimer(){
		return this.timer;
	}
	/**
	 * @param x x-coordinate
	 */
	public void setx(float x){
		this.x = x;
	}
	/**
	 * @param y y-coordinate
	 */
	public void sety(float y){
		this.y = y;
	}
	/**
	 * @param fire fire or not
	 */
	public void setFire(Boolean fire){
		this.fire = fire;
	}
	/**
	 * @param timer timer for sprite
	 */
	public void setTimer(int timer){
		this.timer = timer;
	}
	
	/**
	 * movement of sprites
	 * @param dir direction
	 * @param speed moving speed
	 */
	public void moveToDest(int dir, float speed) {
		float delta_x = 0,
				delta_y = 0;
		switch (dir) {
		case DIR_LEFT:
			delta_x = -speed;
			break;
		case DIR_RIGHT:
			delta_x = speed;
			break;
		case DIR_UP:
			delta_y = -speed;
			break;
		case DIR_DOWN:
			delta_y = speed;
			break;
		}
		// prevent player from moving out of screen
		if((!(this instanceof Player))||((x + delta_x>0)&&(x + delta_x<App.SCREEN_WIDTH)&&(y + delta_y>0)&&(y + delta_y<App.SCREEN_HEIGHT))){
			x += delta_x;
			y += delta_y;
		}
		// make the bottom background sprite move to the top
		if((this instanceof Background)&&(y>BACKGROUND_SIZE*2.5f)){
			y = y-BACKGROUND_SIZE*3;
		}
	}
}
