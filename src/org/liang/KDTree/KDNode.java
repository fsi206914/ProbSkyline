package org.liang.KDTree;


public abstract class KDNode<T> implements Comparable<KDNode<T>> {

    public int k;
    public int depth;
    public KDNode parent = null;
    public KDNode lesser = null;
    public KDNode greater = null;
    
    /*
     * if RectOrLeaf == true, it means taht the node is leaf.
     */
    public boolean RectOrLeaf;
	public T a_T;

    public KDNode() {
    }

    public KDNode(int k, int depth) {

        this.k = k;
        this.depth = depth;
    }

	public void setT(T a_T){
		
		this.a_T = a_T;
	}

    public void setDepth(int a_depth) {

        depth = a_depth;
    }

    public void setRL(boolean RectOrLeaf) {

        this.RectOrLeaf = RectOrLeaf;
    }

	abstract boolean lieIn(KDArea area);
	public abstract KDArea getArea();

    public boolean getRL() {

        return RectOrLeaf;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("k=").append(k);
        builder.append(" depth=").append(depth);
        return builder.toString();
    }

    @Override
    public int compareTo(KDNode<T> o) {
        return 1;
    }
}
