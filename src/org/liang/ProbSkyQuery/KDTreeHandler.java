package org.liang.ProbSkyQuery;

import org.liang.DataStructures.instance;
import org.liang.KDTree.KDPoint;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class KDTreeHandler{

	public List<KDPoint> KDPList;
	public KDTree kdTree;
	public KDTreeInfo KDInfo;

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
		kdInfo = new KDTreeInfo();
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

	void computeInfo(KDNode node){
		
		if(node.parent == null){
			KDArea a_area = new KDArea(dim, min, min);
			kdInfo.init(node, a_area);	
		}	
		else{
			KDArea parentArea = kdInfo.getArea(node.parent);

			/*
			 * find the new area for range query. a_list is the instance list found in the range.
			 */
			KDArea a_area = node.cut(parentArea);
			List<KDPoint> a_list;
			kdTree.rangeQuery(kdTree.root, a_area, a_list);

			/*
			 * old area stored for future search.
			 */
			KDPoint max;
			if(!node.getRL() )
				max = (KDRect)node.min;
			else
				max = (KDLeaf)node.point;

			kdInfo.Add(node, new KDArea(min, max), a_list)

		}
	}
}
