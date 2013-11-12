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
	public List<instance.point> medList; 

	public WRTree wrTree;
	//public WRTreeInfo wrInfo;
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


	void createTree(){
		if(PruneMain.verbose)
			log.info("dim =  " + dim);

		List<instance.point> aList = WRTree.generatePoints(this.div, this.dim);
		wrTree = new WRTree(aList, this.dim);
		//wrTreeInfo = new WRTreeInfo();
		//wrTreeInfo.setObjectBool(itemSkyBool);
	}

	public void computeCenterPoint(){
		
		for(int i=0; i<instList.size(); i++){
			
			int index = wrTree.compPosition(instList.get(i).a_point);	
			divList.get(index).add(instList.get(i));
		}

		for(int i=0; i<divList.size(); i++){
			
			List<instance> part = divList.get(i);	
			instance.point medPoint = compMedian(part);
			medList.add(medPoint);
			part.clear();
		}
	}

	
	public instance.point compMedian(List<instance> part){
		
		instance.point medPoint = new instance.point(dim);	
		medPoint.setOneValue(0.0);
		for(int i=0; i<part.size(); i++){
			instance curr = part.get(i);	

			for(int j=0; j<dim; j++){
				medPoint.__coordinates[j] += curr.a_point.__coordinates[j];
			}
		}

		for(int j=0; j<dim; j++){
			medPoint.__coordinates[j] = medPoint.__coordinates[j]/part.size(); 
		}
		return medPoint;
	}


	void assign(instance aInst){

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
		
   /*     if(node.parent == null){*/
			//KDArea a_area = new KDArea(dim, min, max);
			//kdInfo.init(node, a_area);	
		//}	
		//else{
			//KDArea parentArea = node.parent.getArea();

			//if(parentArea == null) System.out.println("Something Wrong happens parentArea here.");
			/*
			 * find the new area for range query. a_list is the instance list found in the range.
			 * currArea is the current node's area{ (0.0, 0.0), (min_x, min_y)}.
			 * parentArea is parent node's area{(0.0, 0.0), (min_x, min_y)}.
			 */
			//KDArea currArea = node.getArea();
			//if(PruneMain.verbose){
				//if(node.getRL()){
					////log.info("KDArea currArea = "+ currArea.toString() );			
				//}
			//}
				
			//if(currArea == null)
				//System.out.println("Sth Wrong in currArea");

			//if(currArea.equals(parentArea)){
				////System.out.println("node String :" + node.toString());
				//kdInfo.add(node, parentArea, null);
			//}
			//else{
				//List<KDPoint> a_list = new ArrayList<KDPoint>();
				//KDPoint max;

				//if(!node.getRL()){
					//kdTree.rangeQuery(kdTree.root, parentArea.max, currArea.max, a_list);

					/*
					 * old area stored for future search.
					 */
					//max = ((KDRect)node).min;
					//kdInfo.add(node, new KDArea(dim, min, max), a_list);
				//}
				//else{
					//kdTree.rangeQuery(kdTree.root, parentArea.max, currArea.max, a_list);
					//max = ((KDLeaf)node).point;
					//kdInfo.add(node, new KDArea(dim, min, max), a_list);
				//}
			//}
		/*}*/
	}

	@Override
	public void computeProb( ){
		
		this.createTree();
		this.computeCenterPoint();
	}
}
