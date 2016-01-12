import java.util.Random;


public class Metaheuristic {

	private int[] system;
	private int[][] subSystems;
	private int[][] remainingSystems;
	private int[] solutionElement;
	private int solutionElementCost;
	private int[][] solutions;
	private int[] solutionsCost;
	private int[] remainingCost;
	private int[] cost;
	private int[] clIndex;
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
		this.solutionsCost = new int[0];
		this.solutions = new int[0][];
	}
	
	public void metaRasPSSCPConstruction(int[] uncoveredRows, int priority, int restriction){
		int[] uR = uncoveredRows;
		while(uR.length != 0){
			this.solutionElement = this.min(); // TODO
			Random rdm = new Random();
			int rdmNum = rdm.nextInt()%100 + 1;
			if(rdmNum > priority){
				int[][] cl = this.candidateListConstruct(restriction);
				rdmNum = rdm.nextInt()%cl.length;
				this.solutionElement = cl[rdmNum];
				this.remainingSystems = this.deleteColumn(this.remainingSystems, this.clIndex[rdmNum]);
				this.deleteCost(this.clIndex[rdmNum]);
			
			}
			uR = this.addSolution(uR);
			
		}
		
	}
	
	public void cleanSolution(){
		int[] presenceArray = new int[this.system.length];
		for(int i=0; i<this.solutions.length; i++){
			boolean usefull = false;
			for(int j=0; j<this.solutions[i].length; j++){
				for(int k=0; k<presenceArray.length; k++){
					if(this.solutions[i][j] == this.system[k] && presenceArray[k] == 0){
						presenceArray[k] = 1;
						usefull = true;
					}
				}
			}
			if(!usefull){
				this.solutions = deleteColumn(this.solutions, i);
			}
		}
	}
	
	public int[] addSolution(int[] uncoveredRows){
		int[] uR = uncoveredRows;
		int[][] tmp = this.solutions;
		this.solutions = new int[this.solutions.length+1][];
		for(int i=0; i<tmp.length; i++){
			this.solutions[i] = tmp[i];
		}
		this.solutions[this.solutions.length-1] = this.solutionElement;
		int[] tmpCost = this.solutionsCost;
		this.solutionsCost = new int[this.solutionsCost.length+1];
		for(int i=0; i<tmpCost.length; i++){
			this.solutionsCost[i] = tmpCost[i];
		}
		this.solutionsCost[this.solutionsCost.length-1] = this.solutionElementCost;
		for(int i=0; i<uR.length; i++){
			for(int j=0; j<this.solutionElement.length; j++){
				if(this.solutionElement[j] == uR[i]){
					int[] tmpUR = uR;
					uR = new int[uR.length-1];
					int offset = 0;
					for(int k=0; k<uR.length; k++){
						uR[k] = tmpUR[k+offset];
						if(k == i-1){
							offset=1;
						}
					}
				}
			}
		}
		return uR;
	}
	
	public int[][] deleteColumn(int[][] array, int col){
		int offset = 0;
		int[][] tmp = array;
		array = new int[tmp.length-1][];
		for(int i=0; i<tmp.length;i++){
			array[i-offset] = tmp[i];
			if(i == col){
				offset = 1;
			}
		}
		return array;
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
	
	public int[][] candidateListConstruct(int restriction){
		int[][] cl = new int[0][];
		this.clIndex = new int[0];
		for(int i=0; i<this.remainingSystems.length; i++){
			if(this.remainingCost[i]/this.remainingSystems[i].length <= this.solutionElementCost/this.solutionElement.length*(1+restriction/100)){
				cl = new int[cl.length+1][];
				cl[cl.length-1] = this.remainingSystems[i];
				this.clIndex = new int[this.clIndex.length+1];
				this.clIndex[this.clIndex.length-1] = i;
			}
		}
		return cl;
	}
	
	public int[] min(){
		int[] element = this.remainingSystems[0];
		int elementCost = this.remainingCost[0];
		for(int i=0; i<this.remainingSystems.length; i++){
			if(this.remainingCost[i]/this.remainingSystems[i].length < elementCost/element.length){
				element = this.remainingSystems[i];
				elementCost = this.remainingCost[i];
			}
		}
		return element;
	}

}
