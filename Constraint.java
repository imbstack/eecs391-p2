import java.util.Collection;
import java.util.LinkedList;

class Constraint{
//Later make this take an interface called Solver for the local search too

	LinkedList<Variable> vars;
	int val;//What the variables must sum up to
	private static int numConstraints = 0;
	private int id;
	private int temp_x, temp_y;//for debugging where this constraint is based

	public Constraint(Collection<Variable> vars, int val, int tx, int ty){
		this.vars = (LinkedList<Variable>)vars;
		this.val = val;
		numConstraints++;
		this.id = numConstraints;
		this.temp_x = tx;
		this.temp_y = ty;//TEMPORARY< REMOVE LATER
	}

	public boolean satisfied(Variable var, int value){
		//do this more efficiently later
		boolean isHere = false;
		for (Variable v : vars){
			if (v.equals(var)) isHere = true;
		}
		if(!isHere)return true;
		int sum = 0;
		for (Variable v : vars){
			//System.out.println(v.x + ", " + v.y + ", " + v.value);
			if (!v.equals(var)){
				sum += v.value;
			}
			else{
				sum += value;
			}
		}
		//System.out.println(sum + ", " + val);
		//this.printSelf(var, value);
		//boolean result = sum <= val; //temporary, move to return statement
		//System.out.println(result);
		return sum <= val;
	}

	private void printSelf(Variable var, int value){
		System.out.print("Constraint " + this.id + "("+temp_x+temp_y+")"+": ");//remove location stuff later!!!!
		for (Variable v : vars){
			if(!v.equals(var)){
				System.out.print(v.value + " ");
			}
			else{
				System.out.print(value + " ");
			}
		}
		System.out.print(val + " ");
	}
}
