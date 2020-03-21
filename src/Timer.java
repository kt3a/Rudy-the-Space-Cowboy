import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Timer {
	
	int time = 130;	//2 mins for this level
	Font font;
	Image hat,sun;
	
	int x = 10;
	
	Grid grid;
	
	public Timer(Image[] images) {
		font = Font.font("Impact", FontWeight.BOLD, 20);
		hat = images[0];
		sun = images[1];
	}
	
	public void render(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.fillText("START", 30, 74);
		
		gc.setFill(Color.BLUE);
		gc.fillRect(30,75,1300,20);
		
		gc.drawImage(hat,x ,60);
		gc.drawImage(sun,1310, 47);
		
		
	}
	
	
	public void update() {
		x += 1;
	}
}
