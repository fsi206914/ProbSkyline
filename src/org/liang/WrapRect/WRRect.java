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

    public int dim;

    public WRRect(int ndims) {
        min = new instance.point(ndims);
        max = new instance.point(ndims);
        dim = ndims;
    }



    public void setValue(double [] min_value, double [] max_value) {
			min.setPoint(min_value);
			max.setPoint(max_value);
    }


    public String toString() {
        return "A rectangle: " + min.toString() + "   " + max.toString() + "\n";
    }

    public int compareTo(WRRect o) {
        return 1;
    }


    public static void main(String args[]){

        //KDRect a = new KDRect(Double.class, 5);
        //a.infiniteHRect(Double.class);

    }
}

