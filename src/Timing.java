import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Timer;
import java.util.TimerTask;

public class Timing {
	
	int interval = 120;	//2 mins for this level
	Font font;
	Image hat,sun;
	
	int x = 10;
	
	Grid grid;
	Timer timer = new Timer(true);
	int delay = 1000;
    int period = 1000;
    
    int printable;
	
	public Timing(Image[] images) {
		font = Font.font("Impact", FontWeight.BOLD, 20);
		hat = images[0];
		sun = images[1];
	}
	
	public void render(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillText("Time: " + printable, 15, 75);
		
	
	}
	
	private int setInterval() {
	    if (interval == 0)
	        timer.cancel();
	    return --interval;
	}
	
	public void Plsupdate() {
		//time -= 1;
		timer.scheduleAtFixedRate(new TimerTask() {
			 public void run() {
				 printable = setInterval();
			 }
		}, delay, period);
	}
	

}
