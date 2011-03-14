import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Random;


class Search{

	private Game game;
	public LinkedList<Constraint> constraints;
	private ArrayList<Variable> variables;
	private LinkedList<Variable[]> assignment;
	private PriorityQueue<Variable> unassigned;
	private static int[] domain = {1,0};
	private Random generator;

	public Search(Game game){
		this.game = game;
		constraints = new LinkedList<Constraint>();
		variables = new ArrayList<Variable>();
		assignment = new LinkedList<Variable[]>();
		unassigned = new PriorityQueue<Variable>();
		generator = new Random();
	}

	public void search(){
		//interact with world in this one
		updateState();
		step(unassigned);
		int[][] count = new int[game.width()][game.height()];
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				count[i][j] = -1;
			}
		}
		for (Variable[] arr : assignment){	
			for (Variable v : arr){
				if (v.value == 0){
					if ( count[v.x][v.y] < 0){
						count[v.x][v.y] = 0;
					}
				}
				else{
					if (count[v.x][v.y] < 0){
						count[v.x][v.y] = 1;
					}
					else{
						count[v.x][v.y] += 1;
					}
				}
			}
		}	
		//printVarVals(true);
		prettyPrint();
		int x = generator.nextInt(game.width()), y = generator.nextInt(game.height());//saving value of least square
		int min = assignment.size() + 1;//max possible value
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.height(); j++){
				if (count[i][j] != -1){
					//later make this work for flagging too
					if (count[i][j] < min){
						min = count[i][j];
						x = i;
						y = j;
					}
					if (count[i][j] == assignment.size() && !game.isFlagged(i,j)){
						System.out.println("Flagged: (" + (y + 1) + "," + (x + 1) + ")");
						game.select(i,j,true);
					}
				}
			}
		}
		System.out.println("Selection: ("+ (y + 1) + "," + (x + 1) + ")");
		game.select(x,y,false);
	}

}
