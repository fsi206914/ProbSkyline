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

	public static List generatePoints() {
		List<KDPoint> list = new ArrayList<KDPoint>();
		Random random = new Random();

		for(int i = 0; i < 20; ++i) {
			double x = random.nextDouble();
			double y = random.nextDouble();
			
			list.add(new KDPoint(x, y));
		}
		return list;
	}


    public static void main (String args[]){

		List<KDPoint> list = KDTree.generatePoints();

		KDTree myTree = new KDTree<Integer>(Double.class, list,2);

		KDPoint min = new KDPoint(0.2, 0.2);
		KDPoint max = new KDPoint(0.7, 0.7);

		List<KDPoint> rangeList = new ArrayList<KDPoint>();
		myTree.rangeQuery(myTree.root, min, max, rangeList);

		for(KDPoint i: rangeList){
			
			System.out.println(i.toString());
		}

		System.out.println(myTree.toString());

	}
}
