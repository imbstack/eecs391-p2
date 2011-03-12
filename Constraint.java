import java.util.Collection;
import java.util.LinkedList;

class Constraint implements Comparable{
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

	public boolean satisfied(){
		//do this more efficiently later
		int sum = 0;
		for (Variable v : vars){
			//System.out.println(v.x + ", " + v.y + ", " + v.value);
			sum += v.value;
		}
		this.printSelf();
		boolean result = sum == val; //temporary, move to return statement
		System.out.println(result);
		return sum == val;
	}

	private void printSelf(){
		System.out.print("Constraint " + this.id + "("+temp_x+temp_y+")"+": ");//remove location stuff later!!!!
		for (Variable v : vars){
			System.out.print(v.value + " ");
		}
		System.out.print(val + " ");
	}

	public int compareTo(Object o){
		Constraint c = (Constraint)o;
		return this.numConstraints - c.numConstraints;
	}
}
