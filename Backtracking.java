import java.util.LinkedList;

class Backtracking{

	private int[][] variables;
	private Domain[][] domains;
	private LinkedList<Constraint> constraints;

	public Backtracking(Game game){
		variables = new int[game.width()][game.height()];
		domains   = new Domain[game.width()][game.height()];	
		for (int i = 0; i < game.width(); i++){
			for (int j = 0; j < game.width(); j++){
				variables[i][j] = -1;
				if (game.isExplored(i,j)){
					domains[i][j].add(0);
				}
				else{
					domains[i][j].add(0, 1);
				}
			}
		}
	}
}

//eventually make this public
//also note that it will work for domains larger than two
class Domain{
	public LinkedList<Integer> values;

	public Domain(){
		values = new LinkedList<Integer>();
	}

	public void add(Integer... vals){
		for (Integer a : vals){
			values.add(a);
		}
	}

	public int remove(Integer val){
		values.remove((Integer)val);
		if(values.size() == 1){
			return values.get(0);
		}
		return -1;
	}
}




