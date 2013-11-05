package org.liang.KDTree;

public class KDArea{
	
	public KDPoint min;
	public KDPoint max;	
	
	public int k;
	public KDArea(int k){
		
		this.k = k;	
	}

	public KDArea(int k, KDPoint a_min, KDPoint a_max){
		
		this.k = k;	
		max = a_max;
		min = a_min;
	}

	public KDArea cut(KDArea parent, boolean leaf){
		
		//System.out.println(parent.toString() + "  "+ this.toString());
		if(leaf == false){
			if(max.equals(parent.max)) return null;

			int i = 0;
			for(i=0; i<k; i++){
				if(parent.max.__coordinates[i] != this.max.__coordinates[i] )
					break;	
			}

			KDPoint a_min = new KDPoint(k);
			a_min.setAllCoord(0.0);
			a_min.setCoord(i,parent.max.__coordinates[i]);		

			KDArea ret = new KDArea(k, a_min, max);
		}
		else{
			
			
			
		}
		return ret;
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

	public String toString(){
		
		return "{" + min.toString() + max.toString() + "}";
	}

	public boolean equals(Object obj){
		
		if( !( obj instanceof KDArea) )	{
			System.out.println("Wrong instance passed here");
			return false;
		}	

		KDArea other = (KDArea) obj;
			
		if(other.min.equals(this.min) && other.max.equals(this.max))
			return true;
			
		return false;
	}
}
