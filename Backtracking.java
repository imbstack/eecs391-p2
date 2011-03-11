import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Stack;

class Backtracking{

	private Game game;
	public LinkedList<Constraint> constraints;
	private PriorityQueue<Variable> unassigned;
	private Stack<Variable> assigned;

	public Backtracking(Game game){
		this.game = game;
		constraints = new LinkedList<Constraint>();
		unassigned = new PriorityQueue<Variable>();
		assigned = new Stack<Variable>();
	}

	public void search(){
		//interact with world in this one
		updateState();
		step();
		printVarVals();
	}


	private boolean step(){
		if (unassigned.isEmpty())return true;
		Variable cvar = unassigned.poll();
		for (int i = 0; i <= 1; i++){
			cvar.set(i);
			if (isConsistent()){
				assigned.add(cvar);	
				boolean result = step();
				if (result){
					return result;
				}
			}
			Variable v = assigned.pop();
			v.unset();
			unassigned.add(v);
		}
		return false;
	}

	private boolean isConsistent(){
		for (Constraint c : constraints){
			if (!c.satisfied()){
				return  false;
			}
		}
		return true;
	}

/*
	public boolean step(int toBeAssigned){
		if(toBeAssigned == 0){
			return true;
		}
		for (Variable var : variables){
			//make this ordered eventually
			//also have better way than just iterating through, maybe...
			if (var.value < 0){
				for (int i = 0; i < var.domain.values.size(); i++){
					int value = var.domain.values.get(i);
					var.remove(value);
					boolean consistent = true;
					for (Constraint c : constraints){
						if (!c.satisfied()){
							consistent = false;
						}	
					}
					if (consistent){
						//make inferences
						boolean result = step(toBeAssigned-1);
						if (result)return result;
					}
					else{
						var.add(value);
					}
				}
			}
		}	
		return false;
	}
*/
	private void updateState(){
		unassigned.clear();
		constraints.clear();
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				int temp = game.valOf(i,j);
				if (temp < 10){
					constraints.add(new Constraint(game.getAdjacentVars(i,j), temp));
				}
				else{	
					unassigned.add(new Variable(i,j,false));
				}
			}
		}	

	}

	private int index(int x, int y){
		return x * game.width() + y;
	}


	//Printing methods------------//

	private void printVarVals(){
		for ( Variable var : assigned){
			System.out.print( var.value + ", ");
		}
		System.out.println();
	}
}

