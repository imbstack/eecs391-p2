import java.util.Collection;
import java.util.LinkedList;

class Constraint{
//Later make this take an interface called Solver for the local search too

	LinkedList<Variable> vars;
	int val;//What the variables must sum up to
	private static int numConstraints = 0;
	private int id;
	public int x, y;
	public boolean alreadySet;

	public Constraint(Collection<Variable> vars, int val, int tx, int ty){
		this.vars = (LinkedList<Variable>)vars;
		this.val = val;
		numConstraints++;
		this.id = numConstraints;
		this.x = tx;
		this.y = ty;
		alreadySet = false;
	}

	public boolean satisfied(boolean strict){
		//do this more efficiently later
		int sum = 0;
		for (Variable v : vars){
			//System.out.println(v.x + ", " + v.y + ", " + v.value);
			sum += v.value;
		}
		//this.printSelf();
		if (strict){
		//boolean result = sum == val; //temporary, move to return statement
		//System.out.println(result);
		//if(result)alreadySet = true;
		//if(!result)alreadySet = false;
		return sum == val;
		}
		else{
		//boolean result = sum <= val; //temporary, move to return statement
		//System.out.println(result);
		//if(result)alreadySet = true;
		//if(!result)alreadySet = false;
		return sum <= val;
		}
	}

	public int diff(){
		int sum = 0;
		for (Variable v : vars){
			sum += v.value;
		}
		return Math.abs(val - sum);
	}

	private void printSelf(){
		System.out.print("Constraint " + this.id + "("+x+y+")"+": ");//remove location stuff later!!!!
		for (Variable v : vars){
			System.out.print(v.value + " ");
		}
		System.out.print(val + " ");
	}
}
