// TODO: DO NOT LET PLAYER USE MORE FLAGS THAN THERE ARE MINES


import java.awt.Point;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

class Game{
	private Dimension gridDims;
	private short[][] grid;
	private boolean[][] explored;
	private boolean[][] flagged;
	private Scanner input = new Scanner(System.in);
	private static Point[] dboard = {new Point(2,1), new Point(1,3), new Point(3,3)};
	private static boolean hasWon = true;
	private int mineCount, flagCount;


	//getters and setters
	
	public int width(){
		return this.gridDims.width;
	}
	public int height(){
		return this.gridDims.height;
	}
	public int valOf(int x, int y){
		if (explored[x][y]){
			return grid[x][y];
		}
		else{
			return 10;
		}
	}
	public boolean isFlagged(int x, int y){
		return flagged[x][y];
	}
	public boolean isExplored(int x, int y){
		return explored[x][y];
	}
	public boolean select(int x, int y, boolean flag){
		if (explored[x][y]){
			System.out.println("Already selected, try again!");
			return false;
		}
		if (flag){
			if (flagCount++ < mineCount){
				flagged[x][y] = true;
				return true;
			}
			System.out.println("Too many flags used!");
			return false;
		}
		else{
			explored[x][y] = true;
			if (grid[x][y] == 0){
				for (Point p : getAdjacentPoints(x,y)){
					if(!explored[p.x][p.y]){
						select(p.x,p.y, false);
					}	
				}
			}
			return true;
		}
	}

	public boolean removeFlag(int x, int y){
		if (flagged[x][y]){
			flagged[x][y] = false;
			return true;
		}
		else{
			System.out.println("No flag there");
			return false;
		}
	}

	public LinkedList<Point> getAdjacentPoints(int x, int y){
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

	public LinkedList<Variable> getUnexploredVars(){
		LinkedList<Variable> vars = new LinkedList<Variable>();
		for (int i = 0; i < width(); i++){
			for (int j = 0; j < height(); j++){
				if (!explored[i][j]){
					vars.add(new Variable(i,j));
				}
			}
		}
		return vars;
	}

	public boolean onBorder(int x, int y){
		for (Point p : getAdjacentPoints(x,y)){
			if (explored[p.x][p.y]){
				return true;
			}
		}
		return false;
	}



	//constructors

	private Game(int width, int height, Point[] mines, boolean human, boolean backtrackp){
		gridDims = new Dimension(width, height);
		grid = new short[gridDims.width][gridDims.height];
		explored = new boolean[gridDims.width][gridDims.height];
		flagged = new boolean[gridDims.width][gridDims.height];
		this.mineCount = mines.length;
		this.flagCount = 0;
		for(Point mine : mines){
			grid[mine.x][mine.y] = 9;
		}
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height;j++){
				grid[i][j] = countAdjacentMines(i,j);
			}
		}
		select(0,0, false);
		if(human){
			playGameVsHuman();
		}
		else{
			playGame(backtrackp);
		}
	}


	public Game(boolean human){
		this(5,5, dboard, human, true);
	}

	public Game(boolean human, boolean backtrackp){
		this(5,5, dboard, false, backtrackp);
	}


	public Game(int width, int height, int difficulty, boolean human, boolean backtrackp){
		this(width, height, genRandPoints(width, height, difficulty),human,backtrackp);
	}

	//other functions

	private static Point[] genRandPoints(int width, int height, int difficulty){
		float frac = 0.0f;//Fraction of squares that will be mines
		switch(difficulty){
			case 1: frac = 0.10f;break;
			case 2: frac = 0.15f;break;
			case 3: frac = 0.20f;break;
			default:frac = 0.12f;break;
		}
		Point[] ret = new Point[(int)(width * height * frac)];
		Random generator = new Random();
		for (int i = 0; i < ret.length; i++){
			int tx = generator.nextInt(width);
			int ty = generator.nextInt(height);
			if (tx == 0 && ty == 0){
				tx +=1;
				ty +=1;
			}
				ret[i] = new Point(tx,ty);
		}
		return ret;

	}

	private void playGameVsHuman(){
		String welcome = "Ready to play?\nEach selection is made with a triple\nof the form y x [flag], where "+
		       		 "each of\nthe coordinates is indexed from 1 and the\nflag is represented by an optional f\n"+
				 "to remove a flag pass in an r in the f position\n"+
				 "To quit early, pass in the string \"quit\"\nwithout the quotes.\n";
		System.out.println(welcome);	
		boolean t;
		while( !gameOver() ){
			printState();
			do{
				t = humanPlay();
			}while(!t);
		}	
		if (hasWon){
			System.out.println("You Won!");
		}
		else{
			System.out.println("You have lost...");
		}
		this.showMines();
		System.exit(0);

	}

	private void playGame(boolean backtrackp){
		Search search = new Search(this, backtrackp);
		int play = 1;
		while( !gameOver() ) {
			System.out.println("MOVE: " + play++);
			search.search();
			printState();
		}
		if (hasWon){
			System.out.println("You Won!");
		}
		else{
			System.out.println("You have lost...");
		}
		this.showMines();
		System.exit(0);
	}

	private boolean humanPlay(){
		System.out.println("Select next spot:");
		String in = input.nextLine();
		if (in.toUpperCase().matches("QUIT"))System.exit(0);
		in = in.trim();
		String[] selection = in.split("([.,!?:;'\"-]|\\s)+");//split on commas and whitespace
		boolean flagging = false;
		try
		{
			int x = Integer.parseInt(selection[1]) - 1;
			int y = Integer.parseInt(selection[0]) - 1;
			if (selection.length == 3 && selection[2].charAt(0) == 'f') flagging = true;
			if (x > gridDims.width || y > gridDims.height || x < 0 || y < 0){
				System.err.println("Invalid coordinates, try again!");
				return false;
			}
			if (selection.length == 3 && selection[2].charAt(0) == 'r'){
				return removeFlag(x,y);
			}
			return select(x, y, flagging);
		}
		catch (NumberFormatException nfe)
		{
			System.err.println("NumberFormatException: " + nfe.getMessage());
			return false;
		}
		catch (Exception e){
			System.err.println("Improper input: " + e.getMessage());
			return false;
		}
	}

	private short countAdjacentMines(int x, int y){
		if (grid[x][y] == 9){
			return 9;
		}
		short mineCount = 0;
		for (Point p : getAdjacentPoints(x,y)){
			if(grid[p.x][p.y] == 9){
				mineCount++;
			}	
		}
		return mineCount;
	}



	private boolean gameOver(){
		boolean moreLeft = false;//Are there more tiles to be explored?
		for(int i = 0; i < gridDims.width; i++){
			for(int j = 0; j < gridDims.height; j++){
				if(explored[i][j] && grid[i][j] == 9){
					//Set the flag that the player lost, and return
					//true that the game has ended
					hasWon = false;
					return true;
				}
				else if(!explored[i][j] && grid[i][j] != 9){
					moreLeft = true;
				}
			}
		}
		return !moreLeft;
	}

	//Printing methods --------------//
	//Reverse the x and y directions to 
	//make this agree with the outside world
	//-------------------------------//

	public void printFullGrid(){
		System.out.println("-Values====================");
		for(int i = 0; i < gridDims.height; i++){
			for(int j = 0; j < gridDims.width; j++){
				System.out.print(grid[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println("===========================");
	}

	public void showMines(){
		System.out.println("-Mines=====================");
		for(int i = 0; i < gridDims.height; i++){
			for(int j = 0; j < gridDims.width; j++){
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
		for(int i = 0; i < gridDims.height; i++){
			for(int j = 0; j < gridDims.width; j++){
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
				else if(flagged[j][i]){
					System.out.print("F ");
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
