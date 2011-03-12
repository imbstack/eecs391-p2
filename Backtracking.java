import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Random;

class Backtracking{

	private Game game;
	public LinkedList<Constraint> constraints;
	private PriorityQueue<Variable> unassigned;
	private Stack<Variable> assigned;
	private LinkedList<Variable[]> assignment;
	private static int[] domain = {1,0};
	private Random generator;

	public Backtracking(Game game){
		this.game = game;
		constraints = new LinkedList<Constraint>();
		unassigned = new PriorityQueue<Variable>();
		assigned = new Stack<Variable>();
		assignment = new LinkedList<Variable[]>();
		generator = new Random();
		
	}

	public void search(){
		//interact with world in this one
		updateState();
		if (step()){
			for ( Variable v : assigned ){
				if ( v.value == 0){
					System.out.println("Selection: "+ (v.x + 1) + ", " + (v.y + 1));
					game.select(v.x,v.y,false);
					break;
				}
			}
		}
		else{
			System.out.println("RANDOM SELECTION!!!");
			int next = generator.nextInt(unassigned.size());
			Variable v = game.getUnexploredVars().get(next);
			game.select(v.x, v.y, false);
		}
		printVarVals(true);
	}



	private boolean step(){

	}

/*
	private boolean step(int level){
		//System.out.println("----------------------------------" + level);
		if (unassigned.isEmpty()){
			assignment.add(atoa());
			return true;
		}
		Variable cvar = unassigned.poll();
		//System.out.println(cvar.x + " " + cvar.y);
		for (int i : domain){
			if (isConsistent(cvar, i)){
				cvar.set(i);
				assigned.add(cvar);
				boolean result = step(level + 1);
				if (result){
					return result;
				}
				else{
					Variable v = assigned.pop();
					v.unset();
					unassigned.add(v);
				}
			}
		}
		return false;
	}
*/
	private boolean isConsistent(Variable var, int value){
		for (Constraint c : constraints){
			if (!c.satisfied(var, value)){
				return  false;
			}
		}
		return true;
	}

	private int[] atoa(){
		Variable[] assign = new Variable[
		for (Variable v : assigned){
			 
		}
	}

	private void updateState(){
		assignment.clear();
		unassigned.clear();
		constraints.clear();
		assigned.clear();
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				if (!game.isExplored(i,j) && game.onBorder(i,j)){
					unassigned.add(new Variable(i,j));
				}
			}
		}	
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				int temp = game.valOf(i,j);
				if(temp < 10){
					LinkedList<Variable> cons = new LinkedList<Variable>();
					for (Variable v : unassigned){
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
	}

	//Printing methods------------//

	private void printVarVals(boolean verbose){
		for ( Variable var : assigned){
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

