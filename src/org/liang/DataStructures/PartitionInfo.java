package org.liang.DataStructures;

import org.liang.DataStructures.instance;
import org.liang.DataStructures.item;
import java.util.HashMap;
import java.util.List;

import java.io.Serializable;

public class PartitionInfo implements Serializable{

	private int splitNo;
	private int dim;	
	
	/*
	 * Store every object's min extreme point 
	 */
	HashMap<Integer, instance.point> min;
	HashMap<Integer, instance.point> max;

	public PartitionInfo(int splitNo, int dim){
		this.splitNo = splitNo;
		this.dim = dim;

		min = new HashMap<Integer, instance.point>();
		max = new HashMap<Integer, instance.point>();
	}

	public void addMin(int objectID, instance.point minPoint){
		
		min.put(objectID, minPoint);	
	}

	public void addMax(int objectID, instance.point maxPoint){
		
		max.put(objectID, maxPoint);	
	}

}

