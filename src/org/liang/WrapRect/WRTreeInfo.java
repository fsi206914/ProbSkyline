package org.liang.WrapRect;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.liang.DataStructures.instance;
import org.apache.log4j.Logger;

public class WRTreeInfo{

	private static org.apache.log4j.Logger log = Logger.getRootLogger();

	public HashMap<WRRect, Info> maintain;	
	public ArrayList<Integer> objectIDs;

	public Info originInfo = null;

	public final class Info{
		
		public HashMap<Integer, Double> theta;	

		public Info(HashMap<Integer, Double> theta){
			this.theta = theta;	
		}
	}

	public WRTreeInfo( ){
		maintain = new HashMap<WRRect, Info>();	
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

	public void init(){
		HashMap<Integer, Double> theta = new HashMap<Integer, Double>();
		for(int i=0; i<objectIDs.size(); i++){
			int id = objectIDs.get(i);
			theta.put(id, 0.0);
		}
		
		Info originInfo = new Info(theta);
	}

	public HashMap<Integer, Double> copyInfo(HashMap<Integer, Double> org){
		HashMap<Integer, Double> theta = new HashMap<Integer, Double>();
		for(int i=0; i<objectIDs.size(); i++){
			int id = objectIDs.get(i);
			theta.put(id, org.get(id));
		}
		return theta;
	}

	public void initPartition(WRRect aWR, List<instance>a_list){
		HashMap<Integer, Double> theta;
		if(originInfo != null){
			theta = copyInfo(originInfo.theta);

			if(a_list != null){
				for(instance inst: a_list){
					int objectID = inst.objectID;
					double prob = inst.prob + theta.get(objectID);
					theta.put(objectID, prob);
				}
			}

			Info a_info = new Info(theta);
			maintain.put(aWR, a_info);
		}
		else
			System.out.println("Sth Wrong in allocating init() in WRTreeInfo");
	}


	/* public void add(instance.point aPoint, List<instance> a_list){*/

		//HashMap<Integer, Double> theta = copyInfo();
		//Iterator it = maintain.entrySet().iterator();
		//while (it.hasNext()) {
			//Map.Entry pairs = (Map.Entry)it.next();	

			//instance.point otherPoint = (instance.point)pairs.getKey();

			//if(otherPoint.DominateAnother(aPoint) == true){
				
				//HashMap<Integer, Double> addTheta = maintain.get(otherPoint).theta;

				//Iterator it_2 = addTheta.entrySet().iterator();
				//while (it_2.hasNext()) {
					//Map.Entry pairs_2 = (Map.Entry)it_2.next();	
					//if( (double)pairs_2.getValue() > 0){
						//int objectID = (int)pairs_2.getKey();
						//double prob = theta.get(objectID) + addTheta.get(objectID);
						//theta.put(objectID, prob);
					//}
				//}
			//}
		//}
		
		//if(a_list != null){
			//for(instance inst: a_list){
				//int objectID = inst.objectID;
				//double prob = inst.prob + theta.get(objectID);
				//theta.put(objectID, prob);
			//}
		//}


		//Info a_info = new Info(theta);
		//maintain.put(aPoint, a_info);
	/*}*/

	public void compute(instance curr, int index){
		
	}

	/*public void CompFinalSkyProb(HashMap<Integer, Double> theta, KDNode node){*/
		
		//KDPoint instPoint = ((KDLeaf) node).point;
		//instance aInst = KDMapInstance.get(instPoint);
		//int omitObjID = aInst.objectID;

		//double ret = 1.0;

		//Iterator it = theta.entrySet().iterator();
		//while (it.hasNext()) {
			//Map.Entry pairs = (Map.Entry)it.next();	
			//if( (Integer)pairs.getKey() == omitObjID) continue;
			
			//double objProb = (double)pairs.getValue();
			//if(objProb >= 1.0){
				//aInst.instSkyProb = 0.0;
				//return ;
			//}
			//else{
	
				////if(aInst.instanceID == 2805 && objProb>0){
					////log.info("itemID = "+ pairs.getKey()+ "   prob = " + objProb);
				////}
				//ret *= (1-objProb);	
			//}
		//}
			//aInst.instSkyProb = ret;
		//log.info("instance ID = "+ aInst.instanceID+ " prob = "+ret);
		//return ;
	/*}*/
}
