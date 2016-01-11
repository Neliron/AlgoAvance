import java.util.Random;


public class Metaheuristic {

	private int[] system;
	private int[][] subSystems;
	private int[][] remainingSystems;
	private int[][] solutions;
	private int[] remainingCost;
	private int[] cost;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	public Metaheuristic(int[] system, int[][] subSystems, int[] cost){
		this.system = system;
		this.subSystems = subSystems;
		this.remainingSystems = this.subSystems;
		this.cost = cost;
		this.remainingCost = this.cost;
	}
	
	public void metaRasPSSCPConstruction(int[] uncoveredRows, int priority, int restriction){
		int[] uR = uncoveredRows;
		while(uR.length != 0){
			int sol = this.min();
			Random rdm = new Random();
			int rdmNum = rdm.nextInt()%100 + 1;
			if(rdmNum > priority){
				this.candidateListConstruct(sol, restriction);
			}
		}
	}
	
	public void deleteColumn(int[][] array, int col){
		int offset = 0;
		int[][] tmp = array;
		array = new int[tmp.length-1][];
		for(int i=0; i<tmp.length;i++){
			array[i-offset] = tmp[i];
			if(i == col){
				offset = 1;
			}
		}
	}
	
	public void deleteCost(int col){
		int offset = 0;
		int[] tmp = this.remainingCost;
		this.remainingCost = new int[tmp.length-1];
		for(int i=0; i<tmp.length;i++){
			this.remainingCost[i-offset] = tmp[i];
			if(i == col){
				offset = 1;
			}
		}
	}
	
	public int[] candidateListConstruct(int col, int restriction){
		int[][] cl = new int[0][];
		for(int i=0; i<this.remainingSystems.length; i++){
			if(this.remainingCost[i]/this.remainingSystems[i].length <= this.remainingCost[col]/this.remainingSystems[col].length*(1+restriction/100)){
				cl = new int[cl.length+1][];
				cl[cl.length-1] = this.remainingSystems[i];
			}
		}
		Random rdm = new Random();
		return cl[rdm.nextInt()%cl.length];
	}
	
	public int min(){
		int[] yolo = this.remainingSystems[0];
		return yolo;
	}

}
