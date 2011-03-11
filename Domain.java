import java.util.LinkedList;
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




