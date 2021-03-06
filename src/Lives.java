import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//this class will be text for Rudy's Lives
public class Lives {
	
	Font font;
	Image life;
	int left = 3;  // score for left player
	int offset = 30;
	int startX = 40;
	boolean remove = false;
	boolean hearts = true;
	
	int waitPeriod = 25;		//they have 60 frames to get away before loosing another life
	
	boolean[] heartss = {true,true,true};
	
	public Lives(Image life){
		font = Font.font("Impact", FontWeight.BOLD, 20);
		this.life = life;
	}
	
	public void render(GraphicsContext gc) {
		gc.setFill(Color.rgb(187, 193, 201));
		gc.fillRect(10,20, 200, 90);
		
		gc.setFill(Color.BLACK);
		gc.setFont(font);
		gc.fillText("Lives: ", 15, 45);
		
		
		if(heartss[0])
			gc.drawImage(life, 40, 5);
		if(heartss[1])
			gc.drawImage(life, 70, 5);
		if(heartss[2])
			gc.drawImage(life, 100, 5);
		
		gc.setFill(Color.rgb(140,25,255));
		gc.fillRect(800,20,200,30);
		gc.setFill(Color.BLACK);
		gc.fillText("Score: " + MainDriver.score, 800, 40);
		
	}
	
	public void checkLives() {
		//System.out.println("left value" + left);
		if(remove && left > 0) {
			if(waitPeriod == 25) {
				left -=1;
				heartss[left] = false;
				//System.out.println("HERE");
			}
			remove = false;
		}
				
		if (waitPeriod != 0) {
			waitPeriod -=1;
		}
		
		if(waitPeriod == 0) {	//reset the wait period
			waitPeriod = 25;
		}
	}

}
