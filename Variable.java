class Variable implements Comparable{
	public int x, y, value;

	public Variable(int x, int y, boolean explored){
		this.x = x;
		this.y = y;
		this.value = 0;
		}

	public void set(int val){
		this.value = val;
	}

	public void unset(){
		this.value = 0;
	}

	public int compareTo(Object o){
		//later add in sorting if wanted
		return 0;
	}

	public boolean equals(Variable v){
		return v.x == this.x && v.y == this.y;
	}
}
