class Minesweeper{
	public static void main(String[] args){
		//Clean up the interface and check input if time exists for it
		if (args.length > 0){
			Game play = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		}
		else{
			Game play = new Game();
		}
	}

}
