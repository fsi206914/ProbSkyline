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


public class KDTreeMain {

    public static void main (String args[]){

//    List<GenericPoint> list = new ArrayList<GenericPoint>();
//    list.add(a);list.add(b);list.add(c);list.add(d);

		List<GenericPoint> list = KDTree.generatePoints();

		KDTree myTree = new KDTree<Integer>(Integer.class, list,2);
		myTree.Traverse(myTree.root);

		GenericPoint<Integer> GP = new GenericPoint(Integer.class, 5, 6);

		boolean check = myTree.contains(GP);

		if(check)
			System.out.println(myTree.toString());

	}
}
