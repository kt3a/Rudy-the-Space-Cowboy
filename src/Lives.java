import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//this class will be text for Rudy's Lives
public class Lives {
	
	Font font;
	int left = 3;  // score for left player
	
	public Lives(){
		font = Font.font("Impact", FontWeight.BOLD, 20);
	}
	
	public void render(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.setFont(font);
		gc.fillText("Lives: "+ left, 20, 20);
	}

}
