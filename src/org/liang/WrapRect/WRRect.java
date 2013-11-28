// Hyper-Rectangle class supporting KDTree class

package org.liang.WrapRect;

import org.liang.DataStructures.instance;

/*
 * Rect is the intermediate level of KDTree. It includes various
 *
 */
public class WRRect{

    public instance.point min;
    public instance.point max;

	public WRRect child = null;
	public WRRect parent = null;

    public int dim;

    public WRRect(int ndims) {
        dim = ndims;
    }

	public void init(){
        min = new instance.point(dim);
        max = new instance.point(dim);
	}

	public void set(instance.point min, instance.point max){
		this.min = min;		
		this.max = max;
	}

    public void setValue(double [] min_value, double [] max_value) {
			min.setPoint(min_value);
			max.setPoint(max_value);
    }


    public String toString() {
        return "A rectangle: " + min.toString() + "   " + max.toString() + "-----";
    }

    public int compareTo(WRRect o) {
        return 1;
    }


    public static void main(String args[]){
		
        //KDRect a = new KDRect(Double.class, 5);
        //a.infiniteHRect(Double.class);
    }
}

