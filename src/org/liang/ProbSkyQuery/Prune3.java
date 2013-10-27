package org.liang.ProbSkyQuery;

import org.liang.IO.TextInstanceWriter;
import org.liang.IO.TextInstanceReader;

import org.liang.DataStructures.*;
import org.liang.IO.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Properties;
import java.util.Iterator;
import java.util.Map;

public class Prune1And2 extends PruneBase{

	public List<item> afterPrune1;

	public Prune1And2(){
		super();
		this.preprocess();	
	}

	@Override
	protected void preprocess() {
		super.init();
		super.readFile();
		super.setItemSkyBool();
	}

	protected void prune(){
		rule1();
		setAfterPrune1List();
		rule2();
	}

	@SuppressWarnings("rawtypes")
	public void setAfterPrune1List(){
	
		Iterator iter_bool = ItemSkyBool.entrySet().iterator();
		while(iter_bool.hasNext()){
			Map.Entry obj_Bool = (Map.Entry) iter_bool.next();	
			int id = (int) obj_Bool.getKey();
			if( ItemSkyBool.get(id)== false)
				super.corrIndex.remove(id);	
		}
	}
	@SuppressWarnings("rawtypes")
	public void rule1(){
		
		PartitionInfo thisArea = outputLists.get(Integer.parseInt(super.testArea));

		if(thisArea != null){
			HashMap<Integer, instance.point> min = thisArea.min;
			HashMap<Integer, instance.point> max = thisArea.max;

			Iterator iter_max = max.entrySet().iterator();
			while(iter_max.hasNext()){
				Map.Entry obj_max = (Map.Entry) iter_max.next();	

				int max_id = (int) obj_max.getKey();
				
				/*
				 * if ItemSkyBool hasn't the key, it means that a object's max corner is in this partition, but all objects 				 * don't doesn't appear in the partition.
				 */
				if( !ItemSkyBool.containsKey(max_id) || ItemSkyBool.get(max_id)== false ) continue;

				instance.point max_value = (instance.point)obj_max.getValue();	

				Iterator iter_min = min.entrySet().iterator();
				while(iter_min.hasNext()){
					Map.Entry obj_min = (Map.Entry) iter_min.next();	
					int min_id = (int)obj_min.getKey();

					/*
					 * if ItemSkyBool hasn't the key, it means that a object's min corner is in this partition, but all objects 				 	 * don't doesn't appear in the partition.
					 */
					if( !ItemSkyBool.containsKey(min_id) || min_id==max_id || ItemSkyBool.get(min_id) == false) continue;

					instance.point min_value = (instance.point)obj_min.getValue();	

					if(max_value.DominateAnother(min_value)) {
						ItemSkyBool.put(min_id, false);
					}
				}
			}
			
		}
		
	}
	@SuppressWarnings("rawtypes")
	public void rule2(){

		PartitionInfo thisArea = outputLists.get(Integer.parseInt(super.testArea));
		
		Iterator iter_corr = corrIndex.entrySet().iterator();
		while(iter_corr.hasNext()){
			Map.Entry obj_corr = (Map.Entry) iter_corr.next();	
			int id = (int) obj_corr.getKey();
			int indexInList = corrIndex.get(id);
				
			item iterItem = listItem.get(indexInList);	
			for( int j=0; j<iterItem.instances.size(); j++ ){	
				instance currInst = iterItem.instances.get(j);

				Iterator iter_corr_2 = corrIndex.entrySet().iterator();
				while(iter_corr_2.hasNext()){
					Map.Entry obj_corr_2 = (Map.Entry) iter_corr_2.next();	
					int objectid = (int) obj_corr_2.getKey();

					if( thisArea.max.containsKey(objectid ) ){
					
						if(thisArea.max.get(objectid).DominateAnother(currInst.a_point) )
							currInst.IfSkyline = false;
					}
				}	
			}
		}

	}
}
