package org.liang.KDTree;

public class KDArea{
	
	public KDPoint min;
	public KDPoint max;	
	
	public int k;
	public KDArea(int k){
		
		this.k = k;	
	}

	public void setMin(KDPoint a_point){
		min = a_point;	
	}

	public void setMax(KDPoint a_point){
		max = a_point;	
	}

	public void setMinMax(KDPoint a_min, KDPoint a_max){
		max = a_max;	
		min = a_min;
	}

}
