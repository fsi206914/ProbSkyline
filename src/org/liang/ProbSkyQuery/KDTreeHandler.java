package org.liang.ProbSkyQuery;

import org.liang.DataStructures.instance;
import org.liang.DataStructures.KDTreeInfo;

import org.liang.KDTree.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class KDTreeHandler{

	public List<KDPoint> KDPList;
	public KDTree kdTree;
	public KDTreeInfo kdInfo;

	public HashMap<KDPoint, instance> KDMapInstance;

	/**
	 * The left Corner point of range query.
	 */
	public KDPoint min;
	public int dim;

	void init(List<instance> aList, int dim ){
		this.dim = dim;
		KDMapInstance = new HashMap<KDPoint, instance>();
		KDPList = new ArrayList<KDPoint> ();
		for(instance i: aList){
			KDPoint aKDPoint =  util.InstanceToKDPoint(i); 
			KDPList.add(aKDPoint);	
			KDMapInstance.put(aKDPoint, i);
		}
		createTree();
		min = new KDPoint(dim);
		min.setAllCoord(0.0);
	}	

	void createTree(){
		kdTree = new KDTree<Double>(Double.class, KDPList, dim);
		kdInfo = new KDTreeInfo(KDMapInstance);
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
			KDArea a_area = new KDArea(dim, min, min);
			kdInfo.init(node, a_area);	
		}	
		else{
			KDArea parentArea = kdInfo.getArea(node.parent);

			if(parentArea == null) System.out.println("Something Wrong happens here.");
			/*
			 * find the new area for range query. a_list is the instance list found in the range.
			 */
			KDArea currArea = kdInfo.getArea(node);
			KDArea a_area = currArea.cut(parentArea);
			List<KDPoint> a_list = new ArrayList<KDPoint>();

			kdTree.rangeQuery(kdTree.root, a_area, a_list);

			/*
			 * old area stored for future search.
			 */
			KDPoint max;
			if(!node.getRL() )
				max = ((KDRect)node).min;
			else
				max = ((KDLeaf)node).point;

			kdInfo.add(node, new KDArea(dim, min, max), a_list);

		}
	}
}
