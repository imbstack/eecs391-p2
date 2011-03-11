import java.util.Collection;
import java.util.LinkedList;

class Constraint{
//Later make this take an interface called Solver for the local search too

	LinkedList<Variable> vars;
	int val;//What the variables must sum up to

	public Constraint(Collection<Variable> vars, int val){
		this.vars = (LinkedList<Variable>)vars;
		this.val = val;
	}

	public boolean satisfied(){
		int sum = 0;
		for (Variable v : vars){
			sum += v.value;
		}
		return sum <= val;
	}
}
