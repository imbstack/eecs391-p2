class Minesweeper{
	public static void main(String[] args){
		//Clean up the interface and check input if time exists for it
		if (args.length > 0){
			if (args[3].equals("true")){
				Game play = new Game(Integer.parseInt(args[1]), Integer.parseInt(args[0]), Integer.parseInt(args[2]), true);
			}
			else{
				Game play = new Game(Integer.parseInt(args[1]), Integer.parseInt(args[0]), Integer.parseInt(args[2]), false);
			}
		}
		else{
			Game play = new Game();
		}
	}

}
