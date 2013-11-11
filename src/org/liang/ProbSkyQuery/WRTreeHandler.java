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

	public WRTree wrTree;
	public WRTreeInfo wrInfo;
	private int dim;
	//-----------------div ranges from 0 to div(inclusive, inclusive)
	public int div;

	public HashMap<Integer, Boolean> itemSkyBool;
	/**
	 * The left Corner point of range query.
	 */
	public int dim;

	public WRTreeHandler(int dim, HashMap<Integer, Boolean> itemSkyBool, int div){
		this.dim = dim;	
		this.itemSkyBool = itemSkyBool;
		this.div = div;
	}

	void init( ){

	}	

	void createTree(){
		
		if(PruneMain.verbose)
			log.info("dim =  " + dim);

		List<instance.point> aList = generatePoints(this.div, this.dim);
		wrTree = new WRTree(aList, this.dim);
		wrTreeInfo = new WRTreeInfo();
		wrTreeInfo.setObjectBool(itemSkyBool);
	}

	void computeCenterPoint(){
		
		
	}

	void Traverse(){
		preOrder(kdTree.root);	
	}

	void preOrder(KDNode node){
		if(node == null) return;	
		computeInfo(node);
		preOrder(node.lesser);
		preOrder(node.greater);
	}

	@SuppressWarnings("unchecked")
	void computeInfo(KDNode node){
		
		if(node.parent == null){
			KDArea a_area = new KDArea(dim, min, max);
			kdInfo.init(node, a_area);	
		}	
		else{
			KDArea parentArea = node.parent.getArea();

			if(parentArea == null) System.out.println("Something Wrong happens parentArea here.");
			/*
			 * find the new area for range query. a_list is the instance list found in the range.
			 * currArea is the current node's area{ (0.0, 0.0), (min_x, min_y)}.
			 * parentArea is parent node's area{(0.0, 0.0), (min_x, min_y)}.
			 */
			KDArea currArea = node.getArea();
			if(PruneMain.verbose){
				if(node.getRL()){
					//log.info("KDArea currArea = "+ currArea.toString() );			
				}
			}
				
	    	if(currArea == null)
				System.out.println("Sth Wrong in currArea");

		    if(currArea.equals(parentArea)){
				//System.out.println("node String :" + node.toString());
				kdInfo.add(node, parentArea, null);
			}
			else{
				List<KDPoint> a_list = new ArrayList<KDPoint>();
				KDPoint max;

				if(!node.getRL()){
					kdTree.rangeQuery(kdTree.root, parentArea.max, currArea.max, a_list);

					/*
					 * old area stored for future search.
					 */
					max = ((KDRect)node).min;
					kdInfo.add(node, new KDArea(dim, min, max), a_list);
				}
				else{
					kdTree.rangeQuery(kdTree.root, parentArea.max, currArea.max, a_list);
					max = ((KDLeaf)node).point;
					kdInfo.add(node, new KDArea(dim, min, max), a_list);
				}
			}
		}
	}

	//@Override
	public void computeProb( ){
		
		this.init();
		this.createTree();
		this.Traverse();
	}
}
