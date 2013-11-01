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

	/**
	 * The left Corner point of range query.
	 */
	public KDPoint min;
	public int dim;

	void init(List<instance> aList, int dim ){
		this.dim = dim;
		KDPList = new ArrayList<KDPoint> ();
		for(instance i: aList){
			KDPList.add( util.InstanceToKDPoint(i) );	
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

			if(!node.getRL()){
				List<KDPoint> a_list;
				KDPoint max = (KDRect)node.min;

				KDArea a_area = node.cut(parentArea);


			}
		}
	}

}
