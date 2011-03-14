import java.util.Scanner;

class Minesweeper{
	public static void main(String[] args){
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
