import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Random;

class Search{

	private Game game;
	private LinkedList<Constraint> constraints;
	private ArrayList<Variable> variables;
	private LinkedList<Variable[]> assignment;
	private PriorityQueue<Variable> unassigned;
	private Random generator;
	private boolean searchtype;

	public Search(Game game, boolean backtrackp){
		this.game = game;
		constraints = new LinkedList<Constraint>();
		variables = new ArrayList<Variable>();
		assignment = new LinkedList<Variable[]>();
		unassigned = new PriorityQueue<Variable>();
		generator = new Random();
		this.searchtype = backtrackp;
	}

	public void search(){
		//interact with world in this one
		updateState();
		if (searchtype){
			backtrack();
		}
		else{
			local();
		}
		int[][] count = new int[game.width()][game.height()];
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				count[i][j] = -1;
			}
		}
		for (Variable[] arr : assignment){	
			for (Variable v : arr){
				if (v.value == 0){
					if ( count[v.x][v.y] < 0){
						count[v.x][v.y] = 0;
					}
				}
				else{
					if (count[v.x][v.y] < 0){
						count[v.x][v.y] = 1;
					}
					else{
						count[v.x][v.y] += 1;
					}
				}
			}
		}	
		//printVarVals(true);
		prettyPrint();
		int x = generator.nextInt(game.width()), y = generator.nextInt(game.height());
		int min = assignment.size();//max possible value
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				if (count[i][j] != -1){
					//later make this work for flagging too
					if (count[i][j] < min){
						min = count[i][j];
						x = i;
						y = j;
					}
					if (count[i][j] == assignment.size() && !game.isFlagged(i,j)){
						System.out.println("Flagged: (" + (y + 1) + "," + (x + 1) + ")");
						game.select(i,j,true);
					}
				}
			}
		}
		System.out.println("Selection: ("+ (y + 1) + "," + (x + 1) + ")");
		game.select(x,y,false);
	}

	private boolean backtrack(){
		if (isCompletelyConsistent()){
			assignment.add(atoa());
			//System.out.println("ADDED " + assignment.size());
			return true;
		}
		if (unassigned.isEmpty()){
			return false;
		}
		Variable cvar = unassigned.poll();
		cvar.set(1);
		if(isUnder()){
			backtrack();
		}
		if(!game.isFlagged(cvar.x,cvar.y)){
			cvar.set(0);
			backtrack();
		}
		cvar.unset();
		unassigned.add(cvar);
		return true;
	}

	private boolean local(){
		return false;
	}

	private boolean isCompletelyConsistent(){
		for (Constraint c : constraints){
			if (!c.satisfied(true)){
				return  false;
			}
		}
		return true;
	}

	private boolean isUnder(){
		for (Constraint c : constraints){
			if (!c.satisfied(false)){
				return  false;
			}
		}
		return true;
	}

	private Variable[] atoa(){
		Variable[] assign = new Variable[variables.size()];
		for (int i = 0; i < assign.length; i++){
			assign[i] = variables.get(i).clone();
		}
		return assign;
	}

	private void updateState(){
		assignment.clear();
		variables.clear();
		constraints.clear();
		unassigned.clear();
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				if (!game.isExplored(i,j) && game.onBorder(i,j)){
					Variable v = new Variable(i,j);
					variables.add(v);
					if ( game.isFlagged(i,j)){
						v.set(1);
					}
				}
			}
		}	
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				int temp = game.valOf(i,j);
				if(temp < 10 && temp > 0){
					LinkedList<Variable> cons = new LinkedList<Variable>();
					for (Variable v : variables){
						for (Point p : game.getAdjacentPoints(i,j)){
							if (p.x == v.x && p.y == v.y){
								cons.add(v);
							}
						}
					}
					constraints.add(new Constraint(cons, temp, i, j));
				}
			}
		}
		unassigned = new PriorityQueue<Variable>(variables);
	}

	//Printing methods------------//

	private void printVarVals(boolean verbose){
		//System.out.println("Size: " + assignment.size());
		for ( Variable[] arr : assignment){
			for ( Variable var : arr){
				if (!verbose){
					System.out.print( var.value + ", ");
				}
				else{
					System.out.println("(" + (var.x + 1) + ", " + (var.y + 1) + ") " + var.value);
				}
			}
			System.out.println();
		}
	}

	private void prettyPrint(){
		Variable[][] backToBoard = new Variable[game.width()][game.height()];
		for (Variable v : variables){
			backToBoard[v.x][v.y] = v;
		}
		int tid = 1;
		for (int j = 0; j < game.height(); j++){
			for (int i = 0; i < game.width(); i++){
				if (backToBoard[i][j] != null){
					Variable v = backToBoard[i][j];
					System.out.print("V" + tid++ + "(" + (v.y + 1) + "," + (v.x + 1) + ") ");
					for (Variable[] arr : assignment){
						System.out.print("{" + arr[variables.indexOf(v)].value  + "}");
					}
					System.out.println();
				}
			}
		}
	}
}

