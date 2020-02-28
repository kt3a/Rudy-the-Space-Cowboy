import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.BoundingBox;

public class Alien {

	// they need a health indicator
	public int health = 2; // it will take two hits before the alien dies
	int locx, locy;
	Grid grid;
	Image image;
	
	//alien is 80px by 80px
	public int width = 80;
	public int height = 80;
	
	static final int GRAVITY = 3;
	public int dx = 2, dy = 2;  //he should start slowly moving

	boolean isDead = false;

	public Alien(Grid g, int x, int y, Image i) {
		locx = x;
		locy = y;
		grid = g;
		image = i;
	}

	public void update() {

		// he needs to move on his own
		// like every frame update he should move around the grid
		updatePosition();
	}

	public void updatePosition() {

		// Note: The g in the following code is the
		// game Grid, not a Graphics context
		//
		// first handle sideways movement
		
		
		//this needs to be changed to reflect that he keeps moving always, but does not have controls 
		if (dx > 0) {
			dx = grid.moveRight(collisionBox(), dx);
		} else if (dx < 0) {
			dx = -grid.moveLeft(collisionBox(), -dx);
		}
		if (dx != 0)
			locx += dx;
		
		
		if (state == JUMP) {
			// Figure out how far we can move (at our
			// current speed) without running into
			// something
			if (dy > 0) {

				dy = grid.moveDown(collisionBox(), dy);

			} else if (dy < 0) {
				dy = -grid.moveUp(collisionBox(), -dy);
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
			if (grid.onGround(collisionBox())) {
				dy = 0;
				state = STAND;
			} else if (grid.atTop(collisionBox())) {
				dy = 0;
			}
		} else if (!grid.onGround(collisionBox()))
			state = JUMP;
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(image, locx, locy);
	}

	
	public BoundingBox collisionBox()
	{
		return new BoundingBox(locx, locy, width, height);
	}
	
}
