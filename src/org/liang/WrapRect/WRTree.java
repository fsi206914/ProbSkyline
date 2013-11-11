package org.liang.KDTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;

import org.liang.DataStructures.*;

/**
 *	Wrap Rectangle tree;
 */
@SuppressWarnings("rawtypes")
public class WRTree{

    private int k;
	public static int nodeNum = 0;
	List<WRRect> RectList;
    private ALLCOMPARATOR A_COMPARATOR;

    final class ALLCOMPARATOR implements Comparator<KDPoint>{

        private final boolean ascending;

        public ALLCOMPARATOR(){
            this.ascending = true;
        }
        public int compare(instance.point o1, instance.point o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Default constructor.
     */
	public KDTree() {
		this.k = 2;
		this.A_COMPARATOR = new ALLCOMPARATOR(2);
	}

    /**
     * More efficient constructor.
     *
     * @param list of XYZPoints.
     */

    public WRTree(List<instance.point> list, int a_dim) {
        this.k = a_dim;
        this.A_COMPARATOR = new ALLCOMPARATOR();
		this.RectList = new ArrayList<WRRect>();

        if(list.size() >1){

            root = new KDRect(T, k);
            //((KDRect)root).infiniteHRect(this.name);
        }

        createRect(list, );
    }

	public int getNodeNum(){
		
		return nodeNum;	
	}


    /**
     * Create node from list of XYZPoints.
     *
     * @param list of XYZPoints.
     * @param k of the tree.
     * @param depth depth of the node.
     * @return node created.
     */
    public KDNode createNode(List<KDPoint> list, int k, int depth, KDNode currRoot) {
        if (list==null || list.size()==0) return null;
		
		//----- increase the number of nodes;
		nodeNum++;
        if(list.size()==1){
            KDNode a_leaf = new KDLeaf(name, k);
            a_leaf.setDepth(depth);
            a_leaf.setRL(true);
            ((KDLeaf)a_leaf).setPoint(list.get(0));
            return a_leaf;
        }
        int axis = depth % k;
        A_COMPARATOR.setDimToSort(axis);
        Collections.sort(list, A_COMPARATOR);

        int mediaIndex = list.size()/2;
        KDNode node = currRoot;

        KDPoint median_point = list.get(mediaIndex-1);
        double mid_value =  median_point.getCoord(axis);

        ((KDRect) node).setHyperPlane(axis,mid_value);

        if (list.size()>1) {

            List<KDPoint> less = list.subList(0, mediaIndex);
            if (less.size()>1 ) {
                    KDNode leftLeaf = new KDRect(name, k);
                    leftLeaf.setDepth(depth+1);
                    leftLeaf.setRL(false);

                    ((KDRect)leftLeaf).setLeftValue((KDRect)node, axis, mid_value);
                    node.lesser = createNode(less, k, depth+1, leftLeaf);
                    node.lesser.parent = node;
			}
            else{
                    node.lesser = createNode(less, k, depth+1, currRoot);
                    node.lesser.parent = node;

			}
            List<KDPoint> more = list.subList(mediaIndex, list.size());

			if (more.size()>1) {
				KDNode rightLeaft = new KDRect(name, k);
				rightLeaft.setDepth(depth+1);
				rightLeaft.setRL(false);

				((KDRect)rightLeaft).setRightValue((KDRect)node, axis, mid_value);
				node.greater = createNode(more, k, depth+1, rightLeaft);
				node.greater.parent = node;
			}
			else{
				node.greater = createNode(more, k, depth+1, currRoot);
				node.greater.parent = node;

			}
		}
        return node;
    }

    public static void printANode( KDNode node)
    {
        if(node.RectOrLeaf)
             System.out.println(((KDLeaf)node).toString());
        else
             System.out.println(((KDRect)node).toString());
    }

    public void Traverse( KDNode node ) {
        if (node ==null ) return;
        printANode(node);
        Traverse(node.lesser);
        Traverse(node.greater);
    }


	@SuppressWarnings("unchecked")
	public void rangeQuery(KDNode node, KDArea area, List<KDPoint> a_list){
		if(node.getRL()){
			if( node.lieInArea(area) ){
				a_list.add( ((KDLeaf)node).point );
			}
			return;	
		}
		else{
			if( node.lesser.lieInArea(area) ){
				rangeQuery(node.lesser, area, a_list);
			}

			if( node.greater.lieInArea(area) ){
				rangeQuery(node.greater, area, a_list);
			}
		}
	}


	/**
	 * Another Range Query for cut of two hyper-rectangles.
	 * 9:16AM
	 */
	@SuppressWarnings("unchecked")
	public void rangeQuery(KDNode node, KDPoint min, KDPoint max,  List<KDPoint> a_list){
		if(node.getRL()){
			if( node.lieIn(min, max) ){
				a_list.add( ((KDLeaf)node).point );
			}
			return;	
		}
		else{
			if( node.lesser.lieIn(min, max) ){
				rangeQuery(node.lesser, min, max, a_list);
			}

			if( node.greater.lieIn(min, max) ){
				rangeQuery(node.greater, min, max, a_list);
			}
		}
	}

    /**
     * Does the tree contain a point in a leaf.
     *
     * @param value T to locate in the tree.
     * @return True if tree contains value.
     */
    public boolean contains( KDPoint a_point) {
        if (a_point==null) return false;

        KDNode node = this.getNode(a_point);
        return (node!=null);
    }


    public KDNode getNode( KDPoint a_point ) {
        if ( this.root==null || a_point==null) return null;
        KDNode node = this.root;

        while (true) {
            if(node.getRL()){
                if( ((KDLeaf)node ).equal(a_point) )
                    return node;
                else return null;
            }
            else{
                if ( ((KDRect)node).FindNextDirect(a_point) < 0) {
                //Greater
                    if (node.greater==null) {
                        return null;
                    } else {
                        node = node.greater;
                    }
                } else {
                //Lesser
                    if (node.lesser==null) {
                        return null;
                    } else {
                        node = node.lesser;
                    }
                }
            }
        }
//        return null;
    }
    protected static class TreePrinter {

        public static < T > String getString(KDTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static < T > String getString(KDNode node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node.parent!=null) {
                String side = "left";
                if (node.parent.greater!=null ) side = "right";
                builder.append(prefix + (isTail ? "{---" : "$--- ") + "[" + side + "] " + "depth=" + node.depth );
                if(node.getRL())
                    builder.append("   "+ ((KDLeaf) node).point.toString() +  "\n" );
                else
                    builder.append("   "+ ((KDRect) node).min.toString() +  ((KDRect) node).max.toString() + "\n" );
            } else {
                builder.append(prefix + (isTail ? "{--- " : "$--- ") + "depth=" + node.depth +  "\n");
            }
            List<KDNode> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<KDNode>(2);
                if (node.lesser != null) children.add(node.lesser);
                if (node.greater != null) children.add(node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "$   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "$   "), true));
                }
            }


            return builder.toString();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    public static List generatePoints() {
        List<KDPoint> list = new ArrayList<KDPoint>();
        Random random = new Random();

        for(int i = 0; i < 20; ++i) {
          double x = random.nextDouble();
          double y = random.nextDouble();
          double z = random.nextDouble();
		 
          list.add(new KDPoint(x, y, z));
        }
        return list;
    }

	@SuppressWarnings("unchecked")
    public static void main (String args[]){


		//    List<KDPoint> list = new ArrayList<KDPoint>();
		//    list.add(a);list.add(b);list.add(c);list.add(d);

		List<KDPoint> list = generatePoints();

		KDTree myTree = new KDTree<Double>(Double.class, list,3);
		System.out.println(myTree.toString());


		/**
		 * Test range query algorithm.
		 *
		 */ 
		List<KDPoint> rangeList = new ArrayList<KDPoint>();
		KDArea KDA = new KDArea(3);
		KDPoint min = new KDPoint(0.2, 0.2, 0.01);
		KDPoint max = new KDPoint(0.7, 0.7, 0.9);
		KDA.setMinMax(min, max);

		myTree.rangeQuery(myTree.root, KDA, rangeList);

		for(KDPoint i:rangeList){

			System.out.println(i.toString());	
		}

	}
}
