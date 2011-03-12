class Variable implements Comparable{
	public int x, y, value, constrained;

	public Variable(int x, int y){
		this.x = x;
		this.y = y;
		this.value = 0;
		this.constrained = 0;
		}

	public void set(int val){
		this.value = val;
	}

	public void unset(){
		this.value = 0;
	}


	public int compareTo(Object o){
		Variable v = (Variable)o;
		return v.constrained - this.constrained;
	}

	public boolean equals(Variable v){
		return v.x == this.x && v.y == this.y;
	}

	public Variable clone(){
		Variable v = new Variable(this.x, this.y);
		v.set(this.value);
		return v;
	}
}
