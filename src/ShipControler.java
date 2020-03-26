import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ShipControler {
	
	Image health1, health2, health3, health4, health5;		//different images for different levels of hurt ship
	int healthlevel = 5;	//start it at 5
	int waitPeriod = 50;	//wait period between aliens that it can be damaged
	int healthWait = 10;
	boolean wasHit = false;		//boolean to check if it is being touched or not
	boolean repairing = false;
	
	int locx = 2215;
	int locy = 818;
	Font font;
	

	public ShipControler(Image[] images) {
		health1 = images[0] ;
		health2 = images[1];
		health3 = images[2];
		health4 = images[3];
		health5 = images[4];
		
		font = Font.font("Impact", FontWeight.BOLD, 20);
	}
	
	public void render(GraphicsContext gc) {
		
		gc.setFill(Color.BLACK);
		gc.setFont(font);
		gc.fillText("Ship Health: " + healthlevel, 15, 105);
		
		if(healthlevel == 5) 
			gc.drawImage(health1,locx - MainDriver.vleft, locy-MainDriver.vtop);
		
		if(healthlevel == 4) 
			gc.drawImage(health2,locx - MainDriver.vleft, locy-MainDriver.vtop);
		
		if(healthlevel == 3) 
			gc.drawImage(health3,locx - MainDriver.vleft, locy-MainDriver.vtop);
		
		if(healthlevel == 2) 
			gc.drawImage(health4,locx - MainDriver.vleft, locy-MainDriver.vtop);
		
		if(healthlevel == 1) 
			gc.drawImage(health5,locx - MainDriver.vleft, locy-MainDriver.vtop);
	}
	
	public BoundingBox collisionBox() {
		return new BoundingBox(locx, locy, 256, 256);
	}
	
	public void update() {
		
		if(wasHit) {
			//change value of health based on the buffer
			//then set back to false
			if(waitPeriod == 50) {
				if(healthlevel >= 0)
					healthlevel -= 1;
				if(healthlevel == 0)
					healthlevel = 0;
			}
			wasHit = false;
		}
		
		if(repairing) {
			//if true, then increase the health, once it hits 5, make this false
			if(healthWait == 10) {
				
				if(healthlevel != 5) {
					healthlevel += 1;
				}
				else {
					repairing = false;
				}
				
				
			}
		}
		
		if (waitPeriod != 0) {
			waitPeriod -=1;
		}
		
		if(waitPeriod == 0) {	//reset the dammage wait period
			waitPeriod = 50;
		}
		
		
		if(healthWait == 0) {	//reset the health wait period
			healthWait = 10;
		}
	}
	
	
}
