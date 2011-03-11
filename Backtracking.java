import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Point;

class Backtracking{

	private Game game;
	public ArrayList<Variable> variables;
	public LinkedList<Constraint> constraints;

	public Backtracking(Game game){
		this.game = game;
		variables   = new ArrayList<Variable>(game.width()*game.height());
		constraints = new LinkedList<Constraint>();
	}

	public void search(){
		//interact with world in this one
		updateState();
		step();
		printVarVals();
	}

	public void step(){
		for (Variable var : variables){
			//make this ordered eventually
			var.remove(0);
			boolean consistent = true;
			for (Constraint c : constraints){
				if (!c.satisfied()){
					consistent = false;
					break;
				}	
			}
			if (consistent){
				//make inferences
				step();
			}
			else{
				var.add(0);
			}
		}	
	}

	private void updateState(){
		variables.clear();
		constraints.clear();
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				int temp = game.valOf(i,j);
				if (temp < 10){
					constraints.add(new Constraint(game.getAdjacentVars(i,j), temp));
				}
				else{	
					variables.add(new Variable(i,j,false));
				}
			}
		}	

	}

	private int index(int x, int y){
		return x * game.width() + y;
	}


	//Printing methods------------//
	
	private void printVarVals(){
		for ( Variable var : variables){
			System.out.print( var.value + ", ");
		}
	}
}

