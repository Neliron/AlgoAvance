import java.util.Arrays;
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
	private int solIndex;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] sys = {1,2,3,4,5,6};
		int[][] subSys = {{1,2},{2,3},{3,4},{4,5},{1,5,6}};
		int[] cost = {2,4,6,8,2};
		Metaheuristic meta = new Metaheuristic(sys, subSys, cost);
		meta.metaRasPSSCPConstruction(sys, subSys, 20, 10);
		for(int i=0; i<meta.solutions.length; i++){
			System.out.println("Solution = " + Arrays.toString(meta.solutions[i]));
		}
		System.out.println("Couts = " + Arrays.toString(meta.solutionsCost));
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
	
	public int[][] metaRasPSSCPConstruction(int[] uncoveredRows, int[][] subs, int priority, int restriction){
		int[] uR = uncoveredRows;
		this.remainingSystems = subs;
		while(uR.length != 0){
			this.solutionElement = this.min();
			Random rdm = new Random();
			int rdmNum = rdm.nextInt()%100 + 1;
			if(rdmNum > priority){
				int[][] cl = this.candidateListConstruct(restriction);
				if(cl.length > 0){
					rdmNum = rdm.nextInt()%cl.length;
					this.solutionElement = cl[rdmNum];
					this.solIndex = this.clIndex[rdmNum];
				}
			
			}
			this.solutionElementCost = this.remainingCost[solIndex];
			uR = this.addSolution(uR);
			this.remainingSystems = this.deleteColumn(this.remainingSystems, this.solIndex);
			this.remainingCost = this.deleteCost(this.remainingCost, this.solIndex);
		}
		this.cleanSolution();
		return this.solutions;
	}
	
	public void NeighborSearch(int[] uncoveredRows, int[][] subs, int priority, int restriction, float searchMagnitude, int impIteration){
		for(int i=0; i<impIteration; i++){
			
		}
	}
	
	public void cleanSolution(){
		boolean finished = false;
		do{
			finished = true;
			boolean[] usefull = new boolean[this.solutions.length];
			for(int i=0; i<this.solutions.length; i++){
				usefull[i] = false;
				boolean[] presenceInOtherArray = new boolean[this.solutions[i].length];
				
				for(int j=0; j<this.solutions[i].length; j++){
					presenceInOtherArray[j] = false;
					for(int l=0; l<this.solutions.length; l++){
						if(l!=i){
							for(int m=0; m<this.solutions[l].length; m++){
								if(this.solutions[i][j] == this.solutions[l][m]){
									presenceInOtherArray[j] = true;
								}
							}
						}
					}
				}
				for(int k=0; k<presenceInOtherArray.length; k++){
					if(presenceInOtherArray[k] == false){
						usefull[i] = true;
					}
				}
			}
			int indexToDelete = -1, uselessMaxCost = 0;
			for(int i=0; i<this.solutions.length; i++){
				if(!usefull[i]){
					if(this.solutionsCost[i] >= uselessMaxCost){
						uselessMaxCost = this.solutionsCost[i];
						indexToDelete = i;
					}
				}
			}
			if(indexToDelete >= 0){
				this.solutions = this.deleteColumn(this.solutions, indexToDelete);
				this.solutionsCost = this.deleteCost(this.solutionsCost, indexToDelete);
			}
			int offset = 0;
			for(int i=0; i<this.solutions.length; i++){
				if(indexToDelete == i){
					offset = 1;
				}
				if(usefull[i+offset] == false){
					finished = false;
				}
			}
			
		}while(!finished);
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
						if(k == i){
							offset=1;
						}
						uR[k] = tmpUR[k+offset];
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
		for(int i=0; i<array.length;i++){
			if(i == col){
				offset = 1;
			}
			array[i] = tmp[i+offset];
		}
		return array;
	}
	
	public int[] deleteCost(int[] array, int col){
		int offset = 0;
		int[] tmp = array;
		array = new int[tmp.length-1];
		for(int i=0; i<array.length;i++){
			if(i == col){
				offset = 1;
			}
			array[i] = tmp[i+offset];
			
		}
		return array;
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
		this.solIndex = 0;
		for(int i=0; i<this.remainingSystems.length; i++){
			if(this.remainingCost[i]/this.remainingSystems[i].length < elementCost/element.length){
				element = this.remainingSystems[i];
				elementCost = this.remainingCost[i];
				this.solIndex = i;
			}
		}
		return element;
	}

}
