import java.util.Scanner;

class Minesweeper{
	public static void main(String[] args){
		if (args.length == 5){
			boolean human = false;
			boolean backtrackp = false;
			if (args[3].equals("human"))human = true;
			if (args[4].equals("1"))backtrackp = true;
			Game play = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]),Integer.parseInt(args[2]),human, backtrackp);
		}
		else{
			Scanner input = new Scanner(System.in);
			System.out.println("In the following prompts enter the number following the choice you want and hit enter to select that option");
			System.out.println("Select game for player (1) or computer solver (2):");
			if (input.nextLine().equals("1")){
				Game play = new Game(true);
			}
			else{
				System.out.println("Select backtracking search (1) or local search (2):");
				if(input.nextLine().equals("1")){
					Game play = new Game(false, true);
				}
				else{
					Game play = new Game(false, false);
				}
			}
		}	
	}
}
