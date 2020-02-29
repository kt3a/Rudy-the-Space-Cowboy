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

	Image cowboy, shootimg;
	boolean active = false, visible = true;

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

	public Rudy(Grid grid, int x, int y, Image i) {
		// constructor
		// We use locx, locy is the top-left corner of sprite

		locx = x;
		locy = y;
		g = grid;
		state = STAND;
		cowboy = i;
	}

	public BoundingBox collisionBox() {
		return new BoundingBox(locx, locy, width, height);
	}

	public void render(GraphicsContext gc) {
		if (visible && shoots != 1) {

			// if active only draw boy
			gc.drawImage(cowboy, locx, locy);
			
			//if shoots true, play shoots image

		}

	}
	
	public void shootrender(GraphicsContext gc) {
		
		if(shoots == 1)
			gc.drawImage(shootimg, locx,locy);
		
	}

	public void setVelocity(int x, int y) {
		dx = x;
		dy = y;
	}
	
	
	

	public void update() {
		if (dir == 1)
			dx = -15;
		else if (dir == 2)
			dx = 15;
		else
			dx = 0;
		if ((state == STAND) && (jmp == 1)) {
			dy = -28;
			state = JUMP;
			jmp = 0;
		}
		
		if (shoots == 1) {
			//state = SHOOT;
			shoots = 0;
			
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
				locy += dy;
			//
			// Next we adjust dy to allow for the force
			// (acceleration) of gravity
			//
			dy += GRAVITY;
			//
			// Also, check if we're on the ground (or at the
			// top of the screen)
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
