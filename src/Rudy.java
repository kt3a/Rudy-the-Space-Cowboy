import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//Katie Tooher and Amy Herrigan 
//COSC 3550 
public class Rudy {

	public int locx;
	public int locy;
	public Grid g;
	static final int STAND = 0;
	static final int JUMP = 1;
	static final int GRAVITY = 3;
	static final int SHOOT = 2;

	public int dir = 0;
	public int jmp = 0;
	public int shoots = 0;

	public int state;
	public int flag = 10;

	Image cowboyRight, cowboyLeft, cowboyShootsRight, cowboyShootsLeft;
	boolean active = false, visible = true;
	boolean left = false;
	boolean right = true;

	// key controls
	boolean leftKey = false, rightKey = false;
	boolean shootkey = false;

	// Rudy is 80px by 80px
	public int width = 80;
	public int height = 80;

	// rudy speed
	public int dx = 0, dy = 0;
	
	
	//rudy gun control
	Bullet bult = new Bullet();
	Image shootimg;
	boolean wasHit = false;

	
	public Rudy(Grid grid, int x, int y, Image[] images) {
		// constructor
		// We use locx, locy is the top-left corner of sprite

		locx = x;
		locy = y;
		g = grid;
		state = STAND;
		cowboyRight = images[0];
		cowboyLeft = images[1];
		cowboyShootsRight = images[2];
		cowboyShootsLeft = images[3];
	}

	public BoundingBox collisionBox() {
		return new BoundingBox(locx, locy, width, height);
	}

	public void render(GraphicsContext gc) {
		if (right && shoots != 1) {
			gc.drawImage(cowboyRight, locx-MainDriver.vleft, locy-MainDriver.vtop);
			//System.out.println(locx+" "+ locy);
		}
		
		if(left && shoots!= 1) {
			gc.drawImage(cowboyLeft,locx-MainDriver.vleft,locy-MainDriver.vtop);
		}
		
		if(right && shoots == 1) {
			gc.drawImage(cowboyShootsRight,locx-MainDriver.vleft,locy-MainDriver.vtop);
			flag -= 1; 
			
			if(flag == 0) {
				shoots = 0;
				flag = 10;
			}
			
			
		}
		
		if(left && shoots == 1) {
			gc.drawImage(cowboyShootsLeft,locx-MainDriver.vleft,locy-MainDriver.vtop);
			flag -= 1; 
			if(flag == 0) {
				shoots = 0;
				flag = 10;
			}
		}
	

	}
	
	public void setVelocity(int x, int y) {
		dx = x;
		dy = y;
	}

	public void update() {
		if (dir == 1) {  //going left
			dx = -15;
			right = false;
			left = true;
			//bult.dir = 1;
		}
		else if (dir == 2) { //going right
			dx = 15;
		    right = true;
		    left = false;
			//bult.dir = 2;
		}
		
		else
			dx = 0;
		
		if ((state == STAND) && (jmp == 1)) {
			dy = -27;
			state = JUMP;
			jmp = 0;
		}
		if (shoots == 1) {
			bult.wasReleased = true;

		}
		
		if(shoots == 0) {
			bult.wasReleased = false;
		}
		
		updatePosition();

	}

	public void updatePosition() {

		// Note: The g in the following code is the
		// game Grid, not a Graphics context
		//
		// first handle sideways movement
		if (dx > 0) {
			dx = g.moveRight(collisionBox(), dx);
		} else if (dx < 0) {
			dx = -g.moveLeft(collisionBox(), -dx);
		}
		if (dx != 0)
			locx += dx;
		if (state == JUMP) {
			// Figure out how far we can move (at our
			// current speed) without running into
			// something
			if (dy > 0) {

				dy = g.moveDown(collisionBox(), dy);

			} else if (dy < 0) {
				dy = -g.moveUp(collisionBox(), -dy);
			}
			// Adjust our position
			if (dy != 0)
				if(locy != g.height())
					locy += dy;
				if(locy == g.height())
					locy = g.height();
			
			dy += GRAVITY;
			
			if (g.onGround(collisionBox())) {
				dy = 0;
				state = STAND;
			} else if (g.atTop(collisionBox())) {
				dy = 0;
			}
		} else if (!g.onGround(collisionBox()))
			state = JUMP;
	}
	


}
