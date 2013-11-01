package org.liang.ProbSkyQuery;

import org.liang.KDTree.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class KDTreeInfo{

	public HashMap<KDNode, Info> maintain;	
	public ArrayList<Integer> objectIDs;

	public final class Info{
		
		public HashMap<Integer, Double> theta;	
		public double pi;
		public int X;

		public Info(HashMap<Integer, Double> theta, double pi, double x){
			this.theta = theta;	
			this.pi = pi;
			this.x = x;
		}
	}

	public KDTreeInfo(){
		maintain = new HashMap<KDNode, Info>();	
	}

	public void setObjectBool(HashMap<Integer, Boolean> ItemSkyBool ){

		Iterator iter_bool = ItemSkyBool.entrySet().iterator();
		while(iter_bool.hasNext()){
			Map.Entry obj_Bool = (Map.Entry) iter_bool.next();	
			int id = (int) obj_Bool.getKey();
			if(ItemSkyBool.get(id) == true)	
				objectIDs.add(id);
		}
	}

	public void init(KDNode root){
		HashMap<Integer, Double> theta = new HashMap<Integer, Double>();
		for(int i=0; i<objectIDs.size(); i++){
			int id = objectIDs.get(i);	
			theta.put(id, 0.0);
		}
		
		double pi = 1;
		double x = 0;
		
		Info a_info = new Info(theta, pi, x);
		maintain.put(root, a_info);
	}

}
