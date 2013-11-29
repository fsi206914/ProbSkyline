package org.liang.ProbSkyQuery;

import org.liang.DataStructures.instance;

import org.liang.WrapRect.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class WRTreeHandler implements CompProbSky {

	private static org.apache.log4j.Logger log = Logger.getRootLogger();
	/*
	 * input to this class is a list of instance.
	 */
	public List<instance> instList;
	public List< List<instance> > divList; 
	public static List<instance.point> medList; 
	public instance.point extreme;

	public WRTree wrTree;
	public WRTreeInfo wrTreeInfo;
	private int dim;
	//-----------------div ranges from 0 to div(inclusive, inclusive)
	public int div;

	public HashMap<Integer, Boolean> itemSkyBool;
	/**
	 * The left Corner point of range query.
	 */

	public WRTreeHandler(List<instance> instList, int dim, HashMap<Integer, Boolean> itemSkyBool, int div){
		this.instList = instList;
		this.dim = dim;	
		this.itemSkyBool = itemSkyBool;
		this.div = div;
		this.divList = new ArrayList< List<instance>>();
		for(int i=0; i<= div; i++){
			divList.add(new ArrayList<instance>());	
		}
		medList = new ArrayList<instance.point>();
	}

	public void findExtreme(){
		extreme = new instance.point(dim);
		instance.point aPoint = new instance.point(dim);
		double[] max = new double[dim];
		for(int i =0; i<max.length; i++){
			max[i] = 0.0;	
		}
		for(int i=0; i<instList.size(); i++){

			instance inst = instList.get(i);
			for(int j=0; j<dim; j++){
				if(inst.a_point.__coordinates[j] > max[j])
					max[j] = inst.a_point.__coordinates[j];
			}
		}
		extreme.setPoint(max);
	}


	void createTree(){
		if(PruneMain.verbose)
			log.info("dim =  " + dim);

		/**
		 * It contains several procedures to create the wrap rectangle tree:
		 * 1, find the extreme value of all instances in the partition;
		 * 2, generate points, which are the top right point of every rectangle, based on the extreme value;
		 * 3, construction for WRTreeInfo;
		 * 4, find unique objectids which still necessary for computation.
		 */

		findExtreme();
		medList = WRTree.generatePoints(this.div, this.dim, extreme);
		wrTree = new WRTree(medList, this.dim);
		wrTreeInfo = new WRTreeInfo();
		wrTreeInfo.setObjectBool(itemSkyBool);
		System.out.println(wrTree.toString());
	}

   /* public void computeCenterPoint(){*/
		
		//for(int i=0; i<instList.size(); i++){
			
			//int index = wrTree.compPosition(instList.get(i).a_point);	
			//divList.get(index).add(instList.get(i));
		//}

		//for(int i=0; i<divList.size(); i++){
			
			//List<instance> part = divList.get(i);	
			//instance.point medPoint = compMedian(part);
			//medList.add(medPoint);
			//part.clear();
		//}
	//}

	
	//public instance.point compMedian(List<instance> part){
		
		//instance.point medPoint = new instance.point(dim);	
		//medPoint.setOneValue(0.0);
		//for(int i=0; i<part.size(); i++){
			//instance curr = part.get(i);	

			//for(int j=0; j<dim; j++){
				//medPoint.__coordinates[j] += curr.a_point.__coordinates[j];
			//}
		//}

		//for(int j=0; j<dim; j++){
			//medPoint.__coordinates[j] = medPoint.__coordinates[j]/part.size(); 
		//}
		//return medPoint;
	/*}*/


	void assign(instance aInst){

		//----div paritions increases from 6 to 7 .
		int index = 0;
		for(int i=0; i<medList.size(); i++){
			
			instance.point pt = medList.get(i);	
			if( aInst.a_point.compareTo(pt) >0 ){
				break;
			}
			index++;		
		}
		divList.get(index).add(aInst);
	}

	void findAllPartition(){
		for(int i=0; i<instList.size(); i++){
			assign(instList.get(i));
		}
	}

	@SuppressWarnings("unchecked")
	void computeInfo(){
		for(int i=0; i<wrTree.RectList.size(); i++){

			wrTreeInfo.initPartition(wrTree.RectList.get(i), divList.get(i));	
		}
		
		wrTreeInfo.iterateAllDiv();

		/*     for(int i=0; i<instList.size(); i++){*/

			//instance curr = instList.get(i);
			//for(int j=0; j<medList.size(); j++){
			
				//if( curr.a_point.DominateAnother( medList.get(j)))
					//wrTreeInfo.compute(curr, j);
			//}
	   /*}*/

	}

	@Override
	public void computeProb( ){
		
		this.createTree();
		this.findAllPartition();
		this.computeInfo();
	}
}
