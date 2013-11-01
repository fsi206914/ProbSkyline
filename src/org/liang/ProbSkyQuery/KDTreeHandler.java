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
	public int dim;

	void init(List<instance> aList, int dim ){
		this.dim = dim;
		KDPList = new ArrayList<KDPoint> ();
		for(instance i: aList){
			KDPList.add( util.InstanceToKDPoint(i) );	
		}
		createTree();
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
			kdInfo.init(node)	
		}	
		else{
			
		}
	}

}
