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
	boolean active=false, visible=false;
	
	Grid grid;

	public void render(GraphicsContext gc) {
		if (wasReleased) {
			gc.setFill(Color.RED);
			gc.fillOval(locx, locy, w, h);
		}
		
		if(hitWall) {		//if it hit the wall
			wasReleased = false;		//turn it off
		}
		
	}

	public void update() {
		locx += dx;
		System.out.println("bullet location "+locx);
		
	}
	
	public void checkHit() {
		//if the location of bullet's x direction is < or =  2, OR if > or = 50
		//then hitWall = true
		if(locx <= 2 || locx >=50) {
			hitWall = true;
		}
		
	}
	
	//bounding box for the bullet why not
	public BoundingBox collisionBox()
	{
		return new BoundingBox(locx, locy, w/3, h/3);
	}

}
