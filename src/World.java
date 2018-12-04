import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/** the class which contain every sprites, create, destroy, update and render them*/
public class World {
	/** the boss's fire position*/
	public static final int BOSS_FIRE = 97;
	/** the boss's another fire position*/
	public static final int BOSS_FIRE_TWO = 74;
	/** time for shield when after death and start of the game*/
	public static final int SHIELD_TIME = 3000;
	/** time for power up*/
	public static final int PUP_TIME = 5000;
	/** fire interval after power up*/
	public static final int PUP_FIRE = 150;
	/** x-coordinate for life icon*/
	public static final float LIFE_POS_X = 20;
	/** y-coordinate for life icon*/
	public static final float LIFE_POS_Y = 696;
	/** x-coordinate between life icons*/
	public static final float LIFE_BETWEEN_X = 40;
	/** x-coordinate for score*/
	public static final float SCORE_POS_X = 20;
	/** y-coordinate for score*/
	public static final float SCORE_POS_Y = 738;
	/** x-coordinate for player*/
	public static final float PLAYER_POS_X = 480;
	/** y-coordinate for player*/
	public static final float PLAYER_POS_Y = 688;
	/** player's life*/
	public static final int PLAYER_LIFE = 3;
	/** enemy's position when the are in delay*/
	public static final float ENEMY_POS = -64;
	/** drop percentage for powerups*/
	public static final int DROP_PERCENT = 5;
	// all sprites in the game
	private ArrayList<Sprite> sprites;
	private int life=PLAYER_LIFE;
	private int score=0;
	private int shieldpup = SHIELD_TIME;
	private int laserpup;
	private int time=0;
	public World() {
		// Perform initialization logic
		sprites = new ArrayList<>();
		// assume background as one kind of sprite which is moving downward
		// initialize 6 background sprites on screen
		sprites.add(createSprite("background",Sprite.BACKGROUND_SIZE*0.5f,Sprite.BACKGROUND_SIZE*0.5f));
		sprites.add(createSprite("background",Sprite.BACKGROUND_SIZE*1.5f,Sprite.BACKGROUND_SIZE*0.5f));
		sprites.add(createSprite("background",Sprite.BACKGROUND_SIZE*0.5f,Sprite.BACKGROUND_SIZE*-0.5f));
		sprites.add(createSprite("background",Sprite.BACKGROUND_SIZE*1.5f,Sprite.BACKGROUND_SIZE*-0.5f));
		sprites.add(createSprite("background",Sprite.BACKGROUND_SIZE*0.5f,Sprite.BACKGROUND_SIZE*1.5f));
		sprites.add(createSprite("background",Sprite.BACKGROUND_SIZE*1.5f,Sprite.BACKGROUND_SIZE*1.5f));
		sprites.add(createSprite("player",PLAYER_POS_X,PLAYER_POS_Y));
		try (BufferedReader br = new BufferedReader(new FileReader("res/waves.txt"))) {
				String line;
				while ((line = br.readLine()) != null) {
					if(!line.startsWith("#")){
						String[] parts = line.split(",");
						int time = Integer.parseInt(parts[2]);
						int position = Integer.parseInt(parts[1]);
						sprites.add(createSprite(parts[0],position,ENEMY_POS,time));
					}
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* @param input keyboard input
	 * @param delta time from last update
	 */
	public int update(Input input, int delta) {
		// Update all of the sprites in the game, except the enemies which are in delay
		time = time+delta;
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				if((sprite instanceof Enemy)||(sprite instanceof Shooter)||(sprite instanceof SineEnemy)||(sprite instanceof Boss)){
					if(sprite.getDelay()<time){
						if((time-delta)<sprite.getDelay()){
							sprite.update(input, time-sprite.getDelay());
						}
						else{
							sprite.update(input, delta);
						}
					}
				}
				else{
					sprite.update(input, delta);
				}
			}
		}
		// Calculate the power-up time remaining
		if(shieldpup>0){
			shieldpup=shieldpup-delta;
		}
		else{
			shieldpup = 0;
		}
		if(laserpup>0){
			laserpup=laserpup-delta;
		}
		else{
			laserpup = 0;
		}
		for (ListIterator<Sprite> iterator = sprites.listIterator(); iterator.hasNext();) {
		    Sprite sprite = iterator.next();
		    //remove sprites which are out off screen
		    if ((sprite.gety()<0)&&(sprite instanceof Laser)) {
		        iterator.remove();
		        continue;
		    }
		    if ((sprite.gety()>App.SCREEN_HEIGHT)&&((sprite instanceof EnemyLaser)||(sprite instanceof SineEnemy)||
		    		(sprite instanceof Shieldpup)||(sprite instanceof Laserpup)||(sprite instanceof Enemy))) {
		        iterator.remove();
		        continue;
		    }
		    //remove power-ups which are picked up
		    if (sprite instanceof Laserpup){
		    	int i = 0;
		    	for (Sprite s: sprites){
		    		if((s instanceof Player)&&(sprite.contactSprite(s))){
		    			i=App.DIE;
		    		}
		    	}
		    	if (i==App.DIE){
		    		laserpup = PUP_TIME;
		    		iterator.remove();
		    	}
		    	continue;
		    }
		    if (sprite instanceof Shieldpup){
		    	int i = 0;
		    	for (Sprite s: sprites){
		    		if((s instanceof Player)&&(sprite.contactSprite(s))){
		    			i=App.DIE;
		    		}
		    	}
		    	if (i==App.DIE){
		    		shieldpup = PUP_TIME;
		    		iterator.remove();
		    	}
		    	continue;
		    }
		    //make enemy lose hp when they got hit
		    if (sprite instanceof Laser){
		    	int i = 0;
		    	for (Sprite s: sprites){
		    		if(((s instanceof Enemy)||(s instanceof Shooter)||(s instanceof SineEnemy)||
		    				(s instanceof Boss))&&(sprite.contactSprite(s))){
		    			if(s.getDelay()<time){
		    				i=App.DIE;
		    				s.sethp(s.gethp()-1);
		    			}
		    		}
		    	}
		    	if (i==App.DIE){
		    		iterator.remove();
		    	}
		    	continue;
		    }
		    // fire from player
		    if (sprite instanceof Player){
		    	if(laserpup>0){
		    		((Player) sprite).setFtime(PUP_FIRE);
		    	}
		    	else{
		    		((Player) sprite).setFtime(Player.FIRE_TIME);
		    	}
		    	if(sprite.getFire()){
		    		iterator.add(createSprite("laser",sprite.getx(),sprite.gety()));
		    	}
		    	// when player get hit and doesn't have a shield on 
		    	for (Sprite s: sprites){
		    		if(((s instanceof Enemy)||(s instanceof Shooter)||(s instanceof SineEnemy)||
		    				(s instanceof Boss)||(s instanceof EnemyLaser))&&(sprite.contactSprite(s)&&(shieldpup==0))){
		    			life=life-1;
		    			shieldpup = SHIELD_TIME;
		    		}
		    	}
		    }
		    //remove enemies which has no hp
		    if ((sprite instanceof Enemy)||(sprite instanceof Shooter)||
		    		(sprite instanceof SineEnemy)||(sprite instanceof Boss)){
		    	if (sprite.gethp()<=0){
		    		iterator.remove();
		    		score+=sprite.getPoint();
		    		Random rand = new Random();
		    		int n = rand.nextInt(2*(100/DROP_PERCENT))+1;
		    		if(n==1){
		    			iterator.add(createSprite("shieldpup",sprite.getx(),sprite.gety()));
		    		}
		    		if(n==2){
		    			iterator.add(createSprite("laserpup",sprite.getx(),sprite.gety()));
		    		}
		    	}
		    }
		    //fire from shooter and boss
		    if (sprite instanceof Shooter){
		    	if(sprite.getFire()){
		    		iterator.add(createSprite("enemylaser",sprite.getx(),sprite.gety()));
		    	}
		    }
		    if (sprite instanceof Boss){
		    	if(sprite.getFire()){
		    		iterator.add(createSprite("enemylaser",sprite.getx()+BOSS_FIRE,sprite.gety()));
		    		iterator.add(createSprite("enemylaser",sprite.getx()+BOSS_FIRE_TWO,sprite.gety()));
		    		iterator.add(createSprite("enemylaser",sprite.getx()-BOSS_FIRE,sprite.gety()));
		    		iterator.add(createSprite("enemylaser",sprite.getx()-BOSS_FIRE_TWO,sprite.gety()));
		    	}
		    }
		}
		if(life==0){
			return App.DIE;
		}
		return 0;
	}
	
	/* @param g graphic
	 */
	public void render(Graphics g) {
		// Draw all of the sprites in the game
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.render(g);
			}
			if((sprite instanceof Player)&&(shieldpup>0)){
				try {
					new Image("res/shield.png").drawCentered(sprite.getx(), sprite.gety());
				} catch (SlickException e) {
					e.printStackTrace();
				};
			}
		}
		for(int i=0;i<life;i++){
			try {
				new Image("res/lives.png").drawCentered(LIFE_POS_X+i*LIFE_BETWEEN_X,LIFE_POS_Y);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		g.drawString("Score: "+score, SCORE_POS_X, SCORE_POS_Y);
	}
	
	/* create sprite
	 * @param name target's name
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	private static Sprite createSprite(String name, float x, float y) {
		switch (name) {	
			case "laser":
				return new Laser(x, y);
			case "enemylaser":
				return new EnemyLaser(x, y);
			case "shieldpup":
				return new Shieldpup(x, y);
			case "laserpup":
				return new Laserpup(x, y);
			case "player":
				return new Player(x, y);
			case "background":
				return new Background(x, y);
		}
		return null;
	}
	
	/* create sprite
	 * @param name target's name
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param delay enemy's delay
	 */
	private static Sprite createSprite(String name, float x, float y, int delay) {
		switch (name) {
			case "BasicEnemy":
				return new Enemy(x, y, delay);
			case "BasicShooter":
				return new Shooter(x, y, delay);
			case "SineEnemy":
				return new SineEnemy(x, y, delay);	
			case "Boss":
				return new Boss(x, y, delay);
		}
		return null;
	}
}
