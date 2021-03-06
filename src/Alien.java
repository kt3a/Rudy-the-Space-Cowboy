import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.BoundingBox;
//Katie Tooher and Amy Herrigan 
//COSC 3550 

public class Alien {

	// they need a health indicator
	public int health = 2; // it will take two hits before the alien dies
	int locx, locy;
	Grid grid;
	Image alienAlive, alienDead;

	// alien is 80px by 80px
	public int width = 80;
	public int height = 80;

	static final int GRAVITY = 3;
	public int dx = 4, dy = 2; // he should start slowly moving

	boolean wasHit = false;
	boolean active = false, visible = false;

	int waitPeriod = 10; // wait for 2 frames to see the death animation

	public Alien(Grid g, int x, int y, Image[] images) {
		locx = x;
		locy = y;
		grid = g;
		alienAlive = images[0];
		alienDead = images[1];
	}

	public void update() {

		if (!active) { // removes the alien
			return;
		}
		if (dy > 0) {
			grid.moveDown(collisionBox(), dy);
			
			if (dy > Grid.CELLSIZE - 1)
				dy = Grid.CELLSIZE - 1;
			locy += dy;
			
			
			
			if (grid.onGround(collisionBox())) {
				dy = 0;
			} else if (grid.atTop(collisionBox())) {
				dy = 0;
			}
		} else if (!grid.onGround(collisionBox())) {

			dy += 10;
			if (dy > Grid.CELLSIZE - 1)
				dy = Grid.CELLSIZE - 1;
			
		} else {
			if (dx > 0 && wasHit != true) {
				dx = grid.moveRight(collisionBox(), dx);
				if (locx >= 2338) {
					dx = -dx;
				}
			} else if (dx < 0 && wasHit != true) {
				dx = -grid.moveLeft(collisionBox(), -dx);
				if (locx <= 90) {
					dx = -dx;
				}
			}

			locx += dx;
		}

	}

	public void render(GraphicsContext gc) {

		if (active) {
			gc.drawImage(alienAlive, locx - MainDriver.vleft, locy - MainDriver.vtop);
		}

		if (wasHit) {
			gc.drawImage(alienDead, locx - MainDriver.vleft, locy - MainDriver.vtop);
			waitPeriod -= 1;

			if (waitPeriod == 0) {
				wasHit = false;
				active = false;
			}
		}
	}

	public BoundingBox collisionBox() {
		return new BoundingBox(locx, locy, width, height);
	}

}
