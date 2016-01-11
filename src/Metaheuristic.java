
public class Metaheuristic {

	private int[] system;
	private int[][] subSystems;
	private int[][] solutions;
	private int[] cost;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	public Metaheuristic(int[] system, int[][] subSystems, int[] cost){
		this.system = system;
		this.subSystems = subSystems;
		this.cost = cost;
	}
	
	public void metaRasPSSCPConstruction(int[] uncoveredRows, int priority, int restriction){
		int[] uR = uncoveredRows;
		while(uR.length != 0){
			
		}
	}

}
