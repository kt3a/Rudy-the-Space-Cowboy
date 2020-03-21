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
	boolean goingLeft = false, goingRight = false;
	
	
	//int dir = 1;
	
	Grid grid;

	public void render(GraphicsContext gc) {
		if (wasReleased) {
			gc.setFill(Color.RED);
			gc.fillOval(locx, locy, w, h);
		}
		
		if(hitWall) {		//if it hit the wall
			wasReleased = false;		//turn it off
			hitWall = false;			//turn off the Wall check
		}
		
	}

	public void update(int dir) {
//		System.out.println("Dir " +dir);
		
		if(dir == 1) { //&& goingRight && goingLeft == false) {  //go right
			if (locx >= 900) {
				hitWall = true;
				wasReleased = false;
				
			}
			
			else
				locx += dx;
		}
		
		if(dir == 2) { //&& goingLeft && goingRight == false) {  //go left
			if (locx <= 90) {
				hitWall = true;
				wasReleased = false;
				
				
			}
			
			else
				locx -= dx;
		}
		
		//locx += dx;
		
	}
	
	
	
	
	//bounding box for the bullet 
	public BoundingBox collisionBox()
	{
		return new BoundingBox(locx, locy, w/3, h/3);
	}
	
	public void resume()
	  {
	    active = true; visible = true;
	  }

}
