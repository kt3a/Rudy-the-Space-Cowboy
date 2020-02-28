import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainDriver extends Application{
	
	//global variables
	final static int WIDTH = 2000;
	final static int HEIGHT = 800;
	final int FPS = 30; // frames per second
	public static final int SCROLL = 100;  // Set edge limit for scrolling
	public static final int VWIDTH = 1000;
	public static int vleft = 0;
	Grid grid;
	
	//rudy cowboy things
	Rudy rudy;
	Image cowboy;
	Sprite sprites[];
	
	
	public static void main(String[] args) 
    {
        launch(args);
    }
	

	void initialize() {
		//create the classes for the characters 
		//should just be rudy and the aliens
		
		grid = new Grid();
		cowboy = new Image("rudy80.gif");
		rudy = new Rudy(grid,100, 200,cowboy);
		shipCreate();
		
		
	}
	
	void render(GraphicsContext gc) {
		gc.setFill( Color.rgb(143, 140, 137) );
        gc.fillRect( 0, 0, WIDTH, HEIGHT);
        
        rudy.render(gc);
        grid.render(gc);
      //  ship.render(gc);
        
		
	}
	
	void checkScrolling(){
		//this will be here to make sure that the screen moves with the main character
		
	}
	
	void setHandlers(Scene scene) {
		scene.setOnKeyPressed(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {
//						case A: rudy.setLeftKey(true);
//									break;
//						case D: rudy.setRightKey(true);
//									break;
//						case LEFT: rudy.setLeftKey(true);
//									break;
//						case RIGHT: rudy.setRightKey(true);
//									break;
//						
//						default:
//									break;
					
					case A: rudy.dir = 1;
						break;
					case D: rudy.dir = 2;
						break;
					// add a Jump key here 
					case SPACE: rudy.jmp = 1;
						break;
					default:
						break;
					}
				}
			);
			
			scene.setOnKeyReleased(
					e -> {
						KeyCode c = e.getCode();
//						switch (c) {
//							case A: rudy.setLeftKey(false);
//										break;
//							case D: rudy.setRightKey(false);
//										break;
//							//case ENTER: this should be for the shooting of the gems
//							case LEFT: rudy.setLeftKey(false);
//										break;
//							case RIGHT: rudy.setRightKey(false);
//										break;
//							default:
//										break;
//										
						if ((c == KeyCode.A)||(c == KeyCode.D))
							rudy.dir = 0;
						
					}
				);
		
	}
	public void update() {
		//update each sprite and object 1 key frame
		rudy.update();
		
		
	}
	
	public void shipCreate()
	{
		//bottom and top of ship
		for (int i = 0; i < Grid.MWIDTH ; i++) {
			if (i > 2 && i < 50) {
				grid.setBlock(i, Grid.MHEIGHT-3);
				grid.setBlock(i, 6);
			}
			
			//platforms in the ship
			if (i > 2 && i <12) {
				grid.setBlock(i,15);
			}
			if( i > 18 && i <45)
			grid.setBlock(i,15);
			
			if (i > 2 && i <12) {
				grid.setBlock(i,25);
			}
			if( i > 18 && i <50)
			grid.setBlock(i,25);
		}
		
		
		
		//sides
		for (int i = 0; i < Grid.MHEIGHT ; i++) {
			if (i > 5 && i <30) {
				grid.setBlock(2, i);
				grid.setBlock(50, i);
			}
		}
		
		

		
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle("Rudy's Mission to the Sun");
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
}
