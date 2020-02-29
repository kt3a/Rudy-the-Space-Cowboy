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
	Image image;

	//alien is 80px by 80px
	public int width = 80;
	public int height = 80;

	static final int GRAVITY = 3;
	public int dx = 4, dy = 0;  //he should start slowly moving

	boolean isDead = false;

	public Alien(Grid g, int x, int y, Image i) {
		locx = x;
		locy = y;
		grid = g;
		image = i;
	}

	public void update() {

		if (dy > 0) {
			grid.moveDown(collisionBox(), dy);
			locy += dy;
		} else {
			if (dx > 0) {
				dx = grid.moveRight(collisionBox(), dx);
				if (locx >= 1160) {
					dx = -dx;
				}
			} else if (dx < 0) {
				dx = -grid.moveLeft(collisionBox(), -dx);
				if (locx <= 90) {
					dx = -dx;
				}
			}
			locx += dx;
		}

	}




	public void render(GraphicsContext gc) {
		gc.drawImage(image, locx, locy);
	}

	public BoundingBox collisionBox()
	{
		return new BoundingBox(locx, locy, width, height);
	}
	
//	public boolean checkHit(int bullx, int bully) {
//		//take in the coordinates of the bullet, and compare to this sprites current location
//		//if the bullet is within bounding box then return true
//	
//	}

}
