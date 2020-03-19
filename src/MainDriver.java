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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


//Katie Tooher and Amy Herrigan 
//COSC 3550 

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
	Image cowboy, alienAlive;
	Alien alien;
	Bullet bult; 

	MediaPlayer mP;

	public static void main(String[] args) 
	{
		launch(args);
	}

	void initialize() {
		//create the classes for the characters 
		//should just be rudy and the aliens

		grid = new Grid();
		
		
		bult = new Bullet();
		
		
		
		cowboy = new Image("rudy80.gif");
		alienAlive = new Image("alien80.gif");
		rudy = new Rudy(grid,600, 200,cowboy);
		alien = new Alien(grid,100, 290, alienAlive);
		shipCreate();
		
		Media song = new Media(ClassLoader.getSystemResource("TunnelChase.mp3").toString());
		mP = new MediaPlayer(song);
		mP.play();


	}

	void render(GraphicsContext gc) {
		gc.setFill( Color.rgb(143, 140, 137) );
		gc.fillRect( 0, 0, WIDTH, HEIGHT);

		rudy.render(gc);
		bult.render(gc);
		grid.render(gc);
		alien.render(gc);


	}

	void checkScrolling()
	{
		// Test if hero is at edge of view window and scroll appropriately
		if (rudy.locx < (vleft+SCROLL))
		{
			vleft = rudy.locx-SCROLL;
			if (vleft < 0)
				vleft = 0;
		}
		if ((rudy.locx) > (vleft+VWIDTH-SCROLL))
		{
			vleft = rudy.locx-VWIDTH+SCROLL;
			if (vleft > (grid.width()-VWIDTH))
				vleft = grid.width()-VWIDTH;
		}
	}

	void setHandlers(Scene scene) {
		scene.setOnKeyPressed(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {

					case A: rudy.dir = 1;
						break;
					case D: rudy.dir = 2;
						break;
					
					case W: rudy.jmp = 1;
						break;
						
					case SPACE: 
						rudy.shoots = 1;
						bult.wasReleased = true;
						bult.locx = rudy.locx+20;
						bult.locy = rudy.locy + 20;
						
					default:
						break;
					}
				}
				);

		scene.setOnKeyReleased(
				e -> {
					KeyCode c = e.getCode();									
					if ((c == KeyCode.A)||(c == KeyCode.D))
						rudy.dir = 0;

				}
				);

	}
	
	public void checkAlienHit() {
		if(bult.wasReleased && bult.locx == alien.locx) {
			bult.hitAlien = true;
			alien.wasHit = true;
		}
	}
	
	public void update() {
		rudy.update();
		if (bult.wasReleased) {
			bult.update();
		}
		
		checkAlienHit();
		alien.update();
		checkScrolling();
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
				grid.setBlock(i,20);
			}
			if( i > 18 && i <50)
				grid.setBlock(i,20);
		}

		//sides
		for (int i = 0; i < Grid.MHEIGHT ; i++) {
			if (i > 5 && i <Grid.MHEIGHT-2) {
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
