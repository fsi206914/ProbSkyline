package org.liang.WrapRect;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class WRTreeInfo{

	private static org.apache.log4j.Logger log = Logger.getRootLogger();

	public HashMap<instance.point, Info> maintain;	
	public ArrayList<Integer> objectIDs;

	public final class Info{
		
		public HashMap<Integer, Double> theta;	
		public double pi;
		public int x;

		public Info(HashMap<Integer, Double> theta, double pi, int x){
			this.theta = theta;	
			this.pi = pi;
			this.x = x;
		}

	}

	public KDTreeInfo( ){
		maintain = new HashMap<instance.point, Info>();	
		objectIDs = new ArrayList<Integer>();
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


	public void init(instance,point first){
		HashMap<Integer, Double> theta = new HashMap<Integer, Double>();
		for(int i=0; i<objectIDs.size(); i++){
			int id = objectIDs.get(i);
			theta.put(id, 0.0);
		}
		
		double pi = 1;
		int x = 0;
		
		Info a_info = new Info(theta, pi, x);
		maintain.put(first, a_info);
	}

	public void add(KDNode node, KDArea a_area, List<KDPoint> a_list){

		if(node.parent == null) 
			System.out.println("Something Wrong in node parent in KDNodeInfo");
		

		KDNode parent = node.parent; 
		Info parentInfo = maintain.get(parent);

		/*
		 * iterator parent hashmap, copying previous (object, Pr) to the child's. 
		 */
		HashMap<Integer, Double> theta = new HashMap<Integer, Double>();
		Iterator it = parentInfo.theta.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();	

			theta.put((int)pairs.getKey(), (double)pairs.getValue());
		}
		
		double pi = 1;
		int x = 0;
		
		/*
		 * adding curret range query result to parent hashmap, to get a new HashMap.
		 */
		if(a_list != null){
			for(KDPoint kdp: a_list){
				instance aInst = KDMapInstance.get(kdp);	
				int objectID = aInst.objectID;
				double prob = aInst.prob + theta.get(objectID);
				theta.put(objectID, prob);
			}
		}


		Info a_info = new Info(theta, pi, x);
		a_info.setArea(a_area);
		maintain.put(node, a_info);

		/*
		 * if the node is the leaf, we can output the probskyline now.
		 */

		if(node.getRL()){
			//System.out.println("begin computing the skyline point.");
			CompFinalSkyProb(theta,node);
		}
	}

	public void CompFinalSkyProb(HashMap<Integer, Double> theta, KDNode node){
		
		KDPoint instPoint = ((KDLeaf) node).point;
		instance aInst = KDMapInstance.get(instPoint);
		int omitObjID = aInst.objectID;

		double ret = 1.0;

		Iterator it = theta.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();	
			if( (Integer)pairs.getKey() == omitObjID) continue;
			
			double objProb = (double)pairs.getValue();
			if(objProb >= 1.0){
				aInst.instSkyProb = 0.0;
				return ;
			}
			else{
	
				//if(aInst.instanceID == 2805 && objProb>0){
					//log.info("itemID = "+ pairs.getKey()+ "   prob = " + objProb);
				//}
				ret *= (1-objProb);	
			}
		}
			aInst.instSkyProb = ret;
		log.info("instance ID = "+ aInst.instanceID+ " prob = "+ret);
		return ;
	}
}
