package org.liang.ProbSkyQuery;

import org.liang.DataStructures.instance;
import org.liang.DataStructures.item;
import org.liang.DataStructures.KDTreeInfo;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class NaiveHandler implements CompProbSky {

	private static org.apache.log4j.Logger log = Logger.getRootLogger();
	/*
	 * input to this class is a list of instance.
	 */
	public List<item> itemList;

	public int dim;

	public NaiveHandler (List<item> aList, int dim){
		this.dim = dim;	
		this.itemList= aList;
	}

	@SuppressWarnings("unchecked")
	void computeInfo(){

	}

	//@Override
	public void computeProb( ){
		
		for(int i=0; i<itemList.size(); i++){
			item aItem = itemList.get(i);	
			
			int itemID = aItem.objectID;

			for(int j=0; j<aItem.instances.size(); j++){
				instance aInst = aItem.instances.get(j);	
				int instID = aInst.instanceID;
				
				double skyProb = 1.0;
				for(int k=0; k<itemList.size(); k++){

					item itemOther = itemList.get(k);
					if(itemID == itemOther.objectID) continue;

					double itemAddition = 0.0;
					for(int l=0; l<itemOther.instances.size(); l++){
						instance instOther = itemOther.instances.get(l);	

						if(instOther.DominateAnother(aInst))
							itemAddition += instOther.prob;
					}

					skyProb *= (1.0 - itemAddition);
				}
				log.info("a inst ID = "+ instID + "  skyProb = " + skyProb);
			}
		}
	}
}
