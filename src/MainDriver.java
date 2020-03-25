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
	final static int HEIGHT = 1500;
	final int FPS = 30; // frames per second
	public static final int SCROLL = 100;  // Set edge limit for scrolling
	public static final int DOWNSCROLL = 100;  // Set edge limit for scrolling
	
	public static final int VWIDTH = 1000;
	final static int VHEIGHT = 600;
	public static int vleft = 0;
	public static int vtop = 0;
	
	int waitPeriod = 4;
	boolean wait = false;
	
	Grid grid;
	Rudy rudy;
	Image cowboys[] = new Image[4];
	Image timerImages[] = new Image[2];
	Image aliens[] = new Image[2];
	Image cowboyLeft, cowboyRight, cowboyShootRight, cowboyShootLeft,
	alienAlive,alienDead,hat,sun,heart, background;
	Alien alien;
	Bullet bult; 
	Lives lives;               
	Timer timer;

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
		
		heart = new Image("life.gif");
		lives = new Lives(heart);
		
		background = new Image("Background.gif");
		
		hat = new Image("hat_60x60.png");
		sun = new Image("sun_60x60.png");
		timerImages[0] = hat;
		timerImages[1] = sun;
		timer = new Timer(timerImages);
		
		
		cowboyRight = new Image("rudy80.gif");
		cowboyLeft = new Image("leftrudy80.gif");
		cowboyShootRight = new Image("rudy2shoots80.gif");
		cowboyShootLeft = new Image("rudy2shootsLeft.gif");
	    cowboys[0] = cowboyRight;
	    cowboys[1] = cowboyLeft;
	    cowboys[2] = cowboyShootRight;
	    cowboys[3] = cowboyShootLeft;
		
		rudy = new Rudy(grid,600, 200,cowboys);
		
		alienAlive = new Image("alien80.gif");
		alienDead = new Image("aliendeath80.gif");
		aliens[0] = alienAlive;
		aliens[1] = alienDead;
		alien = new Alien(grid,100, 290, aliens);
		
		shipCreate();		//this method creates the ship platform grid to run around on.
		
		Media song = new Media(ClassLoader.getSystemResource("TunnelChase.mp3").toString());
		mP = new MediaPlayer(song);
		mP.play();


	}

	void render(GraphicsContext gc) {
		
//		gc.drawImage(background,0,0);
//		gc.drawImage(background,0,300);
//		gc.drawImage(background,0,600);
//
//		gc.drawImage(background,300,0);
//		gc.drawImage(background,600,0);
//		gc.drawImage(background,900,0);
//		
//		gc.drawImage(background,300,300);
//		gc.drawImage(background,600,300);
//		gc.drawImage(background,900,300);

		gc.setFill( Color.rgb(143, 140, 137) );
		gc.fillRect( 0, 0, WIDTH, HEIGHT);
		
		//timer.render(gc);
		lives.render(gc);
		rudy.render(gc);
		bult.render(gc);
		grid.render(gc);
		alien.render(gc);


	}

	void checkScrolling()
	{
		// Test if hero is at edge of view window and scroll 
		if (rudy.locx < (vleft+SCROLL))		//if rudy's x location is less than the left view + scroll constant
		{
			vleft = rudy.locx-SCROLL;		//then set the left view to rudy's x location  - the scroll constant
			if (vleft < 0)					//if the left view is less than 0 (if he is at the leftmost edge, then reset him to 0 so he is still in view
				vleft = 0;
		}
		if ((rudy.locx) > (vleft+VWIDTH-SCROLL))		//if rudy's x location  is greater than the left view+ the (view width - scroll constant)
		{
			
			vleft = rudy.locx-VWIDTH+SCROLL;			//left view is set to rudy's location - vwidth+scroll
			if (vleft > (grid.width()-VWIDTH))		
				vleft = grid.width()-VWIDTH;
		}
		
		
		//up
		if ((rudy.locy) < (vtop+DOWNSCROLL))		
		{
			vtop = rudy.locy -DOWNSCROLL;					
			//grid height is 1125
			
			if (vtop < 0) {
				vtop = 0;
			}
		}
		
		//down
		if ((rudy.locy) > (vtop+VHEIGHT-DOWNSCROLL))		
		{
			vtop = rudy.locy - (VHEIGHT-DOWNSCROLL);					
			//grid height is 1125
			
			if (vtop > (grid.height()-VHEIGHT))		
				vtop = grid.height()-VHEIGHT+DOWNSCROLL;
		}
		
		
		
	}

	void setHandlers(Scene scene) {
		scene.setOnKeyPressed(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {

					case A: 
						rudy.dir = 1;	//left
						break;
					case D: 
						rudy.dir = 2;	//right
						break;
					
					case W: rudy.jmp = 1;
						break;
						
					case SPACE: 
						rudy.shoots = 1;
						bult.wasReleased = true;
						bult.locx = rudy.locx + 35;
						bult.locy = rudy.locy + 35;
						if(rudy.left) 
							bult.dir = 1;
						if(rudy.right)
							bult.dir = 2;
						
						//bult.dir = 1;
						
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

		if(bult.wasReleased && (bult.collisionBox().intersects(alien.collisionBox())) && alien.active) {
			bult.hitAlien = true;
			alien.wasHit = true;
			bult.wasReleased = false;
		}
	}
	
	public void checkRudyHit() {
		if(alien.collisionBox().intersects(rudy.collisionBox()) && alien.active) {
			rudy.wasHit = true;
			lives.remove = true;
			lives.checkLives();
			
						
		}
	}
	
	public void update() {
		rudy.update();
		//timer.update();	//this updates the hat timer 
		checkAlienHit();
		checkRudyHit();
		
		if (bult.wasReleased) {
				bult.update();	
		}
		
		
		alien.update();
		checkScrolling();
	}

	public void shipCreate()
	{
		//horizontal platforms
		for (int i = 0; i < Grid.MWIDTH ; i++) {
			
			//bottom and top of ship
			if (i > 2 && i < 1000) {
				
				//bottom -- change this when we get down scrolling working
				//grid.setBlock(i, Grid.MHEIGHT-20);
				grid.setBlock(i,Grid.MHEIGHT-2);
				//top
				grid.setBlock(i, 6);
			}

			//horizontal platforms in the ship
			if (i > 2 && i <12) {
				grid.setBlock(i,15);
			}
			
			
			if( i > 18 && i <45)
				grid.setBlock(i,15);
			if( i > 18 && i <50)
				grid.setBlock(i,20);
			
			
			if (i > 2 && i <14) {
				grid.setBlock(i,20);
			}
			
			if( i > 10 && i <30)
				grid.setBlock(i,25);
			

			
			if( i > 2 && i < 15)
				grid.setBlock(i,30);
			if( i > 30 && i < 40)
				grid.setBlock(i,30);
			
			if( i > 20 && i < 30)
				grid.setBlock(i,35);
			
			if( i > 30 && i < 35)
				grid.setBlock(i,37);
			if( i > 35 && i < 40)
				grid.setBlock(i,39);
			
			
			
			//stairs
			if( i > 79 && i <84)
				grid.setBlock(i,20);
			if( i > 82 && i <87)
				grid.setBlock(i,17);
			if( i > 85 && i <90)
				grid.setBlock(i,13);
			
		}

		//sides of ship
		for (int i = 0; i < Grid.MHEIGHT ; i++) {
			if (i > 5 && i <Grid.MHEIGHT-2) {
				grid.setBlock(2, i);
				grid.setBlock(90, i);
			}
			
		}
		
		//vertical platforms 
		for (int i = 0; i < Grid.MHEIGHT; i ++) {
			if (i > 20 && i < Grid.MHEIGHT - 2) {
				grid.setBlock(80,i);
				
				
			}
		}
		
		//stairs
		for (int i = 0; i < Grid.MHEIGHT; i ++) {
			if (i > 17 && i < Grid.MHEIGHT - 2) {
				grid.setBlock(83,i);
			}
		}
		
		for (int i = 0; i < Grid.MHEIGHT; i ++) {
			if (i > 13 && i < Grid.MHEIGHT - 2) {
				grid.setBlock(86,i);
			}
		}

	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle("Rudy's Mission to the Sun");
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(VWIDTH, VHEIGHT);
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
