class Variable{
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

	public void remove(int value){
  		this.value = domain.remove(value);
	}	

	public void add(int value){
		this.value = -1;
		this.domain.add(value);
	}
}
