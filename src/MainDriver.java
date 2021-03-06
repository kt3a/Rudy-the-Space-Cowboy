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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.concurrent.ThreadLocalRandom;


//Katie Tooher and Amy Herrigan 
//COSC 3550 

public class MainDriver extends Application{

	//global variables
	final static int WIDTH = 2000;
	final static int HEIGHT = 1500;
	final int FPS = 30; // frames per second
	public static final int SCROLL = 200;  // Set edge limit for scrolling
	public static final int DOWNSCROLL = 100;  // Set edge limit for scrolling
	
	public static final int VWIDTH = 1000;
	final static int VHEIGHT = 800;
	public static int vleft = 0;
	public static int vtop = 0;
	
	int waitPeriod = 4;
	boolean wait = false;
	int min = 100;
	int max = 1990;
	int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
	//boolean end = false;
	boolean startMenu = false;
	static int score;
	
	Grid grid;
	Rudy rudy;
	Font font;
	ShipControler controls;
	Image controlImages[] = new Image[5];
	Image cowboys[] = new Image[4];
	Image timerImages[] = new Image[2];
	Image aliens[] = new Image[2];
	
	Image cowboyLeft, cowboyRight, cowboyShootRight, cowboyShootLeft,
	alienAlive,alienDead,hat,sun,heart, background,
	c1,c2,c3,c4,c5,start,win;
	
	int totalAliens = 30;
	int numAliens = 0;
	Alien[] alienA = new Alien[totalAliens];
	int a = 50; //starting ticks for alien
	int alienTicks = 100;
	Bullet bult;
	Lives lives;
	Timing timer;

	MediaPlayer mP;

	public static void main(String[] args) 
	{
		launch(args);
	}

	void initialize() {
		font = Font.font("Impact", FontWeight.BOLD, 60);
		startMenu = true;
		start = new Image("StartScreen.gif");
		win = new Image("winScreen.gif");
		
		grid = new Grid();
		
		
		bult = new Bullet();
		
		heart = new Image("life.gif");
		lives = new Lives(heart);
		
		background = new Image("Background.gif");
		
		hat = new Image("hat_60x60.png");
		sun = new Image("sun_60x60.png");
		timerImages[0] = hat;
		timerImages[1] = sun;
		timer = new Timing(timerImages);
		
		
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
		for(int i = 0; i < totalAliens; i++) {
			alienA[i] = new Alien(grid, randomNum, 270, aliens);
			randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		}
		
		shipCreate();		//this method creates the ship platform grid to run around on.
		c1 = new Image("Control1.gif");
		c2 = new Image("Control2.gif");
		c3 = new Image("Control3.gif");
		c4 = new Image("Control4.gif");
		c5 = new Image("Control5.gif");
		controlImages[0] = c1;
		controlImages[1] = c2;
		controlImages[2] = c3;
		controlImages[3] = c4;
		controlImages[4] = c5;
		controls = new ShipControler(controlImages);
		
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
		
		grid.render(gc);
		lives.render(gc);
		controls.render(gc);
		bult.render(gc);
		rudy.render(gc);
		
		for(int i = 0; i < numAliens; i++) {
			alienA[i].render(gc);
		}
		
		
		timer.render(gc);
		
		if(startMenu) {
			gc.setFill(Color.rgb(47, 113, 128));
			gc.fillRect(0,0,WIDTH,HEIGHT);
			gc.drawImage(start,90,50);
			
		}
		
		if(timer.interval <= 0) {
			gc.setFill(Color.rgb(37, 29, 45));
			gc.fillRect(0,0,WIDTH,HEIGHT);
			gc.drawImage(win,90,50);
			gc.setFill(Color.RED);
			gc.setFont(font);
			gc.fillText("YOU WIN!",500,200);
		}
		
		if (lives.left < 1 || controls.healthlevel < 1) {
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			Image gameover = new Image("GameOver2.gif");
			gc.drawImage(gameover, 150, 200);
			gc.setFill(Color.RED);
			gc.setFont(font);
			gc.fillText("Score: " + score,500,500);
		}
		
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
						
					case E:			//this is for the ship controller repairing
						if(rudy.collisionBox().intersects(controls.collisionBox()))
							controls.repairing = true;
						
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
		
		scene.setOnMousePressed(e -> {
			if (startMenu) {
				startMenu = false;
			}
			
		});

	}
	
	public void checkAlienHit() {
		for(int i = 0; i < numAliens; i++) {
			if(bult.wasReleased && (bult.collisionBox().intersects(alienA[i].collisionBox())) && alienA[i].active) {
				bult.hitAlien = true;
				alienA[i].wasHit = true;
				bult.wasReleased = false;
				score += 50;
			}
		}
	}
	
	public void checkRudyHit() {
		for(int i = 0; i < numAliens; i++) {
			if(alienA[i].collisionBox().intersects(rudy.collisionBox()) && alienA[i].active) {
				rudy.wasHit = true;
				lives.remove = true;
				lives.checkLives();
			}
		}
	}
	
	public void checkControlsHit() {
		for(int i = 0; i < numAliens; i++) {
			if(alienA[i].collisionBox().intersects(controls.collisionBox()) && alienA[i].active) {
				controls.wasHit = true;
				
			}
		}
	}
	public void update() {
		rudy.update();
		checkAlienHit();
		checkRudyHit();
		checkControlsHit();		//if the aliens smack the control box then health decreases
		controls.update();
		
		if (bult.wasReleased) {
				bult.update();	
		}
		
		if (numAliens < totalAliens && a%alienTicks == 0) {
			alienA[numAliens].active = true;
			numAliens++;
		}
		a++;
		for(int i = 0; i < numAliens; i++) {
			alienA[i].update();
		}
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
			
			
			if (i > 2 && i <14) 
				grid.setBlock(i,20);
			if (i > 60 && i < 80) 
				grid.setBlock(i,20);
			
			
			if( i > 10 && i < 30)
				grid.setBlock(i,25);
			if( i > 45 && i < 60)
				grid.setBlock(i,25);
			if( i > 80 && i < 100)
				grid.setBlock(i,25);
			

			
			if( i > 2 && i < 15)
				grid.setBlock(i,30);
			if( i > 30 && i < 40)
				grid.setBlock(i,30);
			if( i > 60 && i < 75)
				grid.setBlock(i,30);
			
			
			
			
			if( i > 80 && i < 100)
				grid.setBlock(i,32);
			
			if( i > 20 && i < 30)
				grid.setBlock(i,35);
			if( i > 50 && i < 65)
				grid.setBlock(i,35);
			
			
			if( i > 32 && i < 37)
				grid.setBlock(i,38);
			if( i > 14 && i < 19)
				grid.setBlock(i,38);
			
			if( i > 45 && i < 49)
				grid.setBlock(i,38);
			if( i > 65 && i < 69)
				grid.setBlock(i,38);
			
			
			

			
			
			
			
			
		}

		//sides of ship
		for (int i = 0; i < Grid.MHEIGHT ; i++) {
			if (i > 5 && i <Grid.MHEIGHT-2) {
				grid.setBlock(2, i);
				grid.setBlock(99, i);
			}
			
		}
		
//		//vertical platforms 
//		for (int i = 0; i < Grid.MHEIGHT; i ++) {
//			if (i > 20 && i < Grid.MHEIGHT - 2) {
//				grid.setBlock(80,i);
//				
//				
//			}
//		}
		
		//stairs
//		for (int i = 0; i < Grid.MHEIGHT; i ++) {
//			if (i > 17 && i < Grid.MHEIGHT - 2) {
//				grid.setBlock(83,i);
//			}
//		}
//		
//		for (int i = 0; i < Grid.MHEIGHT; i ++) {
//			if (i > 13 && i < Grid.MHEIGHT - 2) {
//				grid.setBlock(86,i);
//			}
//		}

		
		
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
		timer.Plsupdate();
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
