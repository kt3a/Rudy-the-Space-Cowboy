import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spaceship {
	//need to keep track of the location of the ship in the world map
	//once it gets to sun ask about change of scene 
	
	//set up platforms for the ship using this?? unless I need a separate class for this 
	
	public Spaceship() {
		//not sure yet
	}
	
	
	public void render(GraphicsContext gc) {
		//draw outline 
		gc.setFill(Color.CADETBLUE);
		gc.fillRect(50,600, 900, 30);
		
		
	}
	
	
	
	
	
}
