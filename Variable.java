class Variable implements Comparable{
	public int x, y, value;
	public Domain domain;

	public Variable(int x, int y, boolean explored){
		this.x = x;
		this.y = y;
		this.domain = new Domain();
		if (explored){
			this.value = 0;
			domain.add(0);
		}
		else{
			this.value = -1;
			domain.add(0,1);
		}
	}

	public void set(int val){
		this.value = val;
	}

	public void unset(){
		this.value = -1;
	}

	//OLD STUFF BELOW HERE----- REMOVE DOMAIN EVENTUALLY
	public void remove(int value){
  		this.value = domain.remove(value);
	}	

	public void add(int value){
		this.value = -1;
		this.domain.add(value);
	}

	public int compareTo(Object o){
		//default to all equal for now
		return 0;
	}
}
