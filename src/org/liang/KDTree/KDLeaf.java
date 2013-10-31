// Hyper-Rectangle class supporting KDTree class

package org.liang.KDTree;

class KDLeaf<T> extends KDNode<T> {

    public KDPoint point;
    private Class instClass;
    public int dim;

    public KDLeaf(Class cName, int ndims) {
        super();
        point = new KDPoint(ndims);
        dim = ndims;
		instClass = cName;
    }


    public void setValue(double[] value) {

        for (int i=0; i<value.length; ++i) {
            point.setCoord(i, value[i]);
        }
    }

    public void setPoint(KDPoint a_point) {
        point = a_point;
    }

    public String toString() {
        return "A leaf: " + point.toString() + "\n";
    }

	public boolean lieIn(KDArea area){
		
		for(int i=0; i<dim; i++){
			
			if( point.__coordinates[i] >= area.min.__coordinates[i] && point.__coordinates[i] <= area.max.__coordinates[i]  )	
				return continue;
			else
				return false;
		}	
		return true;
	}

    public boolean equal(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof KDPoint)) {
            System.out.println("The comparison objects are confused");
            return false;
        }
        KDPoint a_point = (KDPoint) obj;
        int ret = 0;

        for(int i=0; i<dim; i++){

            point.setCurrDimComp(i);
            int local = point.compareTo(a_point);
            ret += local;
            if(ret != 0) return false;

        }
        return true;
    }


    public static void main(String args[]){

		//KDLeaf a = new KDLeaf(Double.class, 5);
    }
}

