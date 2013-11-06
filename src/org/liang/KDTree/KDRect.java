// Hyper-Rectangle class supporting KDTree class

package org.liang.KDTree;

/*
 * Rect is the intermediate level of KDTree. It includes various
 *
 */
public class KDRect<T> extends KDNode {

    public KDPoint min;
    public KDPoint max;

    public int dim;
    private final Class cName; 
    public hyperplane currHP;

    public final class hyperplane{
        int planeDim;
        double value;

        public hyperplane(int a_planeDim, double a_value)
        {
            planeDim = a_planeDim;
            value = a_value;
        }
    }

    public KDRect(Class T, int ndims) {
        super();
        min = new KDPoint(ndims);
        max = new KDPoint(ndims);
        dim = ndims;
		cName = T;
		infiniteHRectDouble();
    }

    public void infiniteHRectDouble() {

        for (int i=0; i<dim; ++i) {
            min.setCoord(i, 0.0);
            max.setCoord(i, 1.0);
        }
    }

	public KDArea getArea(){
		KDPoint retMin = new KDPoint(dim);		
		retMin.setAllCoord(0.0);
		
		KDArea retArea = new KDArea(dim, retMin, min);
		return retArea;
	}


    public void setHyperPlane( int a_planeDim, double a_value ) {
        currHP = new hyperplane(a_planeDim, a_value);
    }


    public void setValue(double [] min_value, double [] max_value) {
        for (int i=0; i<min_value.length; ++i) {
            min.setCoord(i, min_value[i]);
            max.setCoord(i, max_value[i]);
        }
    }

    public void setLeftValue(KDRect Parent, int a_dim, double a_value) {

        for (int i=0; i<min.getDim(); ++i) {
            min.__coordinates[i] = Parent.min.__coordinates[i];
            max.__coordinates[i] = Parent.max.__coordinates[i];
        }
        max.__coordinates[a_dim] = a_value;
    }

    public void setRightValue(KDRect Parent, int a_dim, double a_value) {

        for (int i=0; i<min.getDim(); ++i) {
            min.__coordinates[i] = Parent.min.__coordinates[i];
            max.__coordinates[i] = Parent.max.__coordinates[i];
        }
        min.__coordinates[a_dim] = a_value;
    }

    public int  FindNextDirect( KDPoint a_point  ) {

        double midValue = this.currHP.value;
        int DimComp = this.currHP.planeDim;
		double retValue = a_point.getCoord(DimComp);

		if(midValue > retValue ) return 1;
		else return -1;
    }

	public boolean lieIn(KDArea area){
		
		for(int i=0; i<dim; i++){
			
			if(area.min.__coordinates[i] > this.max.__coordinates[i] || area.max.__coordinates[i] < this.min.__coordinates[i] )
				return false;
			else
				continue;
		}	
		return true;
	}

	public boolean lieIn(KDPoint smaller, KDPoint larger){
		
		for(int i=0; i<dim; i++){
			
			if(this.min.__coordinates[i] > larger.max.__coordinates[i] || this.max.__coordinates[i] < smaller.min.__coordinates[i] )
				return false;
			else
				continue;
		}	
		KDPoint a_min_0 = KDPoint.generate(dim);

		if( lieIn(new KDArea(dim, a_min_0, larger)) )
		return true;
	}

    public String toString() {
        return "A rectangle: " + min.toString() + "   " + max.toString() + "\n";
    }

    public int compareTo(KDNode o) {
        return 1;
    }


    public static void main(String args[]){

        //KDRect a = new KDRect(Double.class, 5);
        //a.infiniteHRect(Double.class);

    }
}

