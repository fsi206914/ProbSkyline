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
	
		afterPrune1 = new ArrayList<item> ();
		Iterator iter_bool = ItemSkyBool.entrySet().iterator();
		while(iter_bool.hasNext()){
			Map.Entry obj_Bool = (Map.Entry) iter_bool.next();	
			int id = (int) obj_Bool.getKey();
			if( ItemSkyBool.get(id)== true)
				afterPrune1.add(listItem.get(id));		
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
				if( ItemSkyBool.get(max_id)== false ) continue;

				instance.point max_value = (instance.point)obj_max.getValue();	

				Iterator iter_min = min.entrySet().iterator();
				while(iter_min.hasNext()){
					Map.Entry obj_min = (Map.Entry) iter_min.next();	
					int min_id = (int)obj_min.getKey();
					if( min_id==max_id || ItemSkyBool.get(min_id) == false) continue;

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
		List<item> rule2List = afterPrune1;
		for(int i=0; i<rule2List.size(); i++){
				
			item iterItem = rule2List.get(i);	
			for( int j=0; j<iterItem.instances.size(); j++ ){	
				instance currInst = iterItem.instances.get(j);

				for(int k=0; k<afterPrune1.size(); k++)
					if( thisArea.max.containsKey(afterPrune1.get(k).objectID ) ){
						if(thisArea.max.get(afterPrune1.get(k).objectID).DominateAnother(currInst.a_point) )
							currInst.IfSkyline = false;
					}
			}	
		
		}

	}

}
