import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid {
	
	public static final int MWIDTH = 80;
	public static final int MHEIGHT = 28;
	int map[][] = new int[MWIDTH][MHEIGHT];
	static final int CELLSIZE = 25; // Number of pixels per map cell, each cell will be 128x128
	
	Grid()     //constructor
	{
		for (int row = 0; row < MHEIGHT; row++)
		 for (int col = 0; col < MWIDTH; col++)
			map[col][row] = 0;
	}
	
	public int width() {
		return MWIDTH * CELLSIZE;
	}
	
	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.CADETBLUE);
		// Just draw visible blocks
//		int col1 = (MainDriver.vleft)/CELLSIZE;  //0
//		int col2 = (MainDriver.vleft + MainDriver.VWIDTH)/CELLSIZE;  //20
//		if (col2 >= MWIDTH)
//			col2 = MWIDTH-1;
		
		//map[0][0] = 1;
		
		for (int row = 0; row < MHEIGHT; row++) {
		 for (int col = 0; col < MWIDTH; col++) {
			 //System.out.println("MAP COORD: ("+col+","+row+ ") VALUE: "+ map[col][row]);
			if (map[col][row] == 1)
				gc.fillRect(col*CELLSIZE, row*CELLSIZE, CELLSIZE, CELLSIZE);
		 }
		}
			
	}
	

	public void setBlock(int x, int y)
	{
		map[x][y] = 1;
		System.out.println("SETTING COORDINATE ("+x+","+y+") EQUAL TO 1");
	}
	
	public int moveRight(BoundingBox r, int d) {
		// Return the number of pixels (not exceeding d) that
		// the Rectangle r can move to the right without hitting
		// a block.
		// Assume d is less than CELLSIZE.
		int right = (int) r.getMaxX();
		int col = right / CELLSIZE;
		int row1 = ((int) r.getMinY()) / CELLSIZE;
		int row2 = ((int) r.getMaxY()) / CELLSIZE;
		if (row2 >= MHEIGHT)
			row2 = MHEIGHT - 1;
		int edge = CELLSIZE * (col + 1);
		if ((right + d) < edge)
			return d;
		if (col == (MWIDTH - 1))
			return width() - right - 1;
		for (int row = row1; row <= row2; row++)
			if (map[col + 1][row] != 0)
				return edge - right - 1;
		return d;
	}

	public int moveLeft(BoundingBox r, int d) {
		// Return the number of pixels (not exceeding d) that
		// the Rectangle r can move to the left without hitting
		// a block.
		// Assume d is less than CELLSIZE.
		int left = (int) r.getMinX();
		int col = left / CELLSIZE;
		int row1 = ((int) r.getMinY()) / CELLSIZE;
		int row2 = ((int) r.getMaxY()) / CELLSIZE;
		if (row2 >= MHEIGHT)
			row2 = MHEIGHT - 1;
		int edge = CELLSIZE * col;
		if ((left - d) >= edge)
			return d;
		if (col == 0)
			return left;
		for (int row = row1; row <= row2; row++)
			if (map[col - 1][row] != 0)
				return left - edge;
		return d;
	}
	
	
	public int moveDown(BoundingBox r, int d) {
		int rbottom = (int) r.getMaxY();
		int row = rbottom / CELLSIZE;
		int col1 = ((int) r.getMinX()) / CELLSIZE;
		int col2 = ((int) r.getMaxX()) / CELLSIZE;
		int edge = CELLSIZE * (row + 1);
		if (rbottom + d < edge)
			return d;
		for (int col = col1; col <= col2; col++) {
			
			if (map[col][row + 1] != 0) {
			//	System.out.println("STUCK LINE 104: VALUE IS: " + (edge - rbottom -1));
				return edge - rbottom - 1;
			}
		}
		return d;
	}

	public int moveUp(BoundingBox r, int d) {
		return d;
	}

	public boolean onGround(BoundingBox r) {
		// Return true if Rectangle r is resting on the ground (or a block)
		int rbottom = (int) r.getMaxY();
		
		int row = rbottom / CELLSIZE;
		int edge = CELLSIZE * (row + 1);
		if ((rbottom + 1) != edge)
			return false;
		int col1 = ((int) r.getMinX()) / CELLSIZE;
		int col2 = ((int) r.getMaxX()) / CELLSIZE;
		for (int i = col1; i <= col2; i++)
			if (map[i][row + 1] != 0)
				return true;
		return false;
	}

	public boolean atTop(BoundingBox r) {
		return false;
	}
	
}
