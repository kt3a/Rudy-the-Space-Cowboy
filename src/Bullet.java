import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Katie Tooher and Amy Herrigan 
//COSC 3550 
public class Bullet {

	public int locx;
	public int locy;
	public int w = 10;
	public int h = 10;
	public int dx = 10;
	public int dy;

	boolean wasReleased = false; // false by default, need to check if rudy's guns were triggered
	boolean hitAlien = false;
	boolean hitWall = false;
	boolean active = false, visible = false;
	boolean goingLeft = false, goingRight = false;

	int dir; // this marks the left/right direction of where bullet should shoot

				
	Grid grid;

	public void render(GraphicsContext gc) {

		if (wasReleased) {
			gc.setFill(Color.RED);
			gc.fillOval(locx - MainDriver.vleft, locy - MainDriver.vtop, w, h);
		}

		if (hitWall) { // if it hit the wall
			wasReleased = false; // turn it off
			hitWall = false; // turn off the Wall check
		}

	}

	public void update() {
		if (dir == 2) { // go right
			if (locx >= 2400) {
				hitWall = true;
				wasReleased = false;

			}

			else
				locx += dx;
		}

		if (dir == 1) { // && goingLeft && goingRight == false) { //go left
			if (locx <= 90) {
				hitWall = true;
				wasReleased = false;

			}

			else
				locx -= dx;
		}

	}

	// bounding box for the bullet
	public BoundingBox collisionBox() {
		return new BoundingBox(locx, locy, w / 3, h / 3);
	}

	public void resume() {
		active = true;
		visible = true;
	}

}
