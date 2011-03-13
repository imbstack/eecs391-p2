import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Random;

class Backtracking{

	private Game game;
	public PriorityQueue<Constraint> constraints;
	private ArrayList<Variable> variables;
	private LinkedList<Variable[]> assignment;
	private PriorityQueue<Variable> unassigned;
	private static int[] domain = {1,0};
	private Random generator;

	public Backtracking(Game game){
		this.game = game;
		constraints = new PriorityQueue<Constraint>();
		variables = new ArrayList<Variable>();
		assignment = new LinkedList<Variable[]>();
		unassigned = new PriorityQueue<Variable>();
		generator = new Random();
	}

	public void search(){
		//interact with world in this one
		updateState();
		step(unassigned);
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
		printVarVals(true);
		int x = -1, y = -1;//saving value of least square
		int min = 10000000;
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				if (count[i][j] != -1){
					//later make this work for flagging too
					if (count[i][j] < min){
						min = count[i][j];
						x = i;
						y = j;
					}
				}
			}
		}
		System.out.println("Selection: "+ (x + 1) + ", " + (y + 1));
		game.select(x,y,false);
	}

	private boolean step(PriorityQueue<Variable> unas){
		if (isCompletelyConsistent()){
			assignment.add(atoa());
			System.out.println("ADDED " + assignment.size());
			return true;
		}
		if (unas.isEmpty()){
			return false;
		}
		Variable cvar = unas.poll();
		cvar.set(1);
		if(isUnder()){
			step(new PriorityQueue<Variable>(unas));
		}
		cvar.set(0);
		if(isUnder()){
			step(new PriorityQueue<Variable>(unas));
		}
		return true;
	}

		

/*
	//int count = 0;
	private boolean step(){
		System.out.println("STEP");
		
		if (isCompletelyConsistent()){
			assignment.add(atoa());
			System.out.println("ADDED " + assignment.size());
			return true;
		}
		if (unassigned.isEmpty()){
			return false;
		}
		Variable cvar = unassigned.poll();
		cvar.set(1);
		for (Constraint c : constraints){
			//System.out.println("hbar");
			if (c.satisfied()){
				//System.out.println(count++);
				boolean result = step();
				if (!result){
					//System.out.println("UNSET");
					cvar.unset();
					unassigned.add(cvar);
				}
			}
		}
		cvar.unset();
		for (Constraint c : constraints){
			//System.out.println("hbar");
			if (c.satisfied()){
				//System.out.println(count++);
				boolean result = step();
			}
		}

		//cvar.unset();
		//unassigned.add(cvar);
		return false;
	}
*/
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
					variables.add(new Variable(i,j));
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
		System.out.println("Size: " + assignment.size());
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
}

