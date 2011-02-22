import java.awt.Point;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Scanner;

class Game{
	private Dimension gridDims;
	private short[][] grid;
	private boolean[][] explored;
	Scanner input = new Scanner(System.in);

	public Game(int width, int height, Point... mines){
		gridDims = new Dimension(width, height);
		grid = new short[gridDims.width][gridDims.height];
		explored = new boolean[gridDims.width][gridDims.height];
		for(Point mine : mines){
			grid[mine.x][mine.y] = 9;
		}
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height;j++){
				grid[i][j] = countAdjacentMines(i,j);
			}
		}
		select(0,4);
	}

	public Game(){
		this(5,5,new Point(2,1), new Point(1,3), new Point(3,3));
	}

	/*
	 * Eventually implement Game constructor with random placement 
	 * of mines, and settable grid size, just like the real game.
	 */


	public boolean select(int x, int y){
		explored[x][y] = true;
		if(grid[x][y] == 9){
			System.out.println("-----Game Over-----");
			return false;
		}
		else if (grid[x][y] == 0){
			for (Point p : getAdjacentMines(x,y)){
				if(!explored[p.x][p.y] && grid[p.x][p.y] == 0){
					select(p.x,p.y);
				}	
			}
		}
		return true;
	}

	private short countAdjacentMines(int x, int y){
		if (grid[x][y] == 9){
			return 9;
		}
		short mineCount = 0;
		for (Point p : getAdjacentMines(x,y)){
			if(grid[p.x][p.y] == 9){
				mineCount++;
			}	
		}
		return mineCount;
	}

	private LinkedList<Point> getAdjacentMines(int x, int y){
		LinkedList<Point> adjacents = new LinkedList<Point>();
		int cx,cy;
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				cx = x + i;
				cy = y + j;
				if(cx >= 0 && cx < gridDims.width && 
						cy >= 0 && cy < gridDims.height &&
						!(i == 0 && j == 0)){
					adjacents.add(new Point(cx, cy));
						}	

			}	
		}
		return adjacents;
	}

	//Printing methods --------------//
	//Reverse the x and y directions to 
	//make this agree with the outside world
	//-------------------------------//

	public void printFullGrid(){
		System.out.println("-Values====================");
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height; j++){
				System.out.print(grid[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println("===========================");
	}

	public void showMines(){
		System.out.println("-Mines=====================");
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

	public void printState(){
		System.out.println("-Move======================");
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height; j++){
				if(explored[j][i]){
					if(grid[j][i] == 9){
						System.out.print("✸ ");
					}
					else if(grid[j][i] == 0){
						System.out.print("◻ ");
					}
					else{
						System.out.print(grid[j][i] + " ");
					}
				}
				else{
					System.out.print("◼ ");
				}
			}
			System.out.println();
		}
		System.out.println("===========================");
	}
}
