import java.awt.Point;
import java.awt.Dimension;

class Game{
	private Dimension gridDims;
	private short[][] grid;

	public Game(int width, int height, Point... mines){
		gridDims = new Dimension(width, height);
		grid = new short[gridDims.width][gridDims.height];
		for(Point mine : mines){
			grid[mine.x][mine.y] = 9;
		}
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height;j++){
				grid[i][j] = countAdjacentMines(i,j);
			}
		}
	}

	public Game(){
		this(5,5,new Point(2,1), new Point(1,3), new Point(3,3));
	}

	/*
	 * Eventually implement Game constructor with random placement 
	 * of mines, and settable grid size, just like the real game.
	 */

	private short countAdjacentMines(int x, int y){
		if (grid[x][y] == 9){
			return 9;
		}
		short mineCount = 0;
		int cx,cy;
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				cx = x + i;
				cy = y + j;
				if(cx > 0 && cx < gridDims.width && 
						cy > 0 && cy < gridDims.height &&
						x != 0 && y != 0){
					if(grid[cx][cy] == 9){
						mineCount++;
					}
						}	

			}	
		}
		return mineCount;
	}
	
	//Printing methods --------------//
	//Reverse the x and y directions to 
	//make this agree with the outside world
	//-------------------------------//

	public void printFullGrid(){
		System.out.println("=Values====================");
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height; j++){
				System.out.print(grid[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println("===========================");
	}
	
	public void showMines(){
		System.out.println("=Mines=====================");
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height; j++){
				if(grid[j][i] == 9){
					System.out.print("✸ ");
				}
				else{
					System.out.print("◻ ");
				}
			}
			System.out.println();
		}
		System.out.println("===========================");
	}
}
