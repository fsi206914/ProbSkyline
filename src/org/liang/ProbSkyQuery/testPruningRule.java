package dist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.util.Properties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class testPruningRule{

	public static int objectNum;
    public static int dim;
	
	public static int splitNum = 0;
	public static ArrayList<  HashMap<Integer, HashMap<Integer, instance.point>  > > outputLists;

	public static double[] split_Value;

	public static final Logger log = LoggerFactory.getLogger(testPruningRule.class);
	public static final int MaxSplitNum = 100;
	public static final boolean logEnable = true;
	public static MyOutputStream[] MOPS;
	public testPruningRule(){

	System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
	}


	public static void FindleftExtremePoint(item a_item){
		int instanceNum = a_item.instances.size();
		instance.point min,max;
		min = new instance.point(dim);
		max = new instance.point(dim);
		min.setOneValue(1.0); max.setOneValue(0.0);
		for(int i=0; i<instanceNum; i++){
			instance a_instance = a_item.instances.get(i);

			for(int j=0; j< dim; j++){
				if(a_instance.a_point.__coordinates[j] < min.__coordinates[j])
					min.__coordinates[j] = a_instance.a_point.__coordinates[j];
				if(a_instance.a_point.__coordinates[j] > max.__coordinates[j])
					max.__coordinates[j] = a_instance.a_point.__coordinates[j];
			}
		
		}
		a_item.setMin(min);
		a_item.setMax(max);

		int min_area = min.partition(split_Value,splitNum);	
		int max_area = max.partition(split_Value,splitNum);	

		HashMap<Integer, instance.point> Adding =  outputLists.get(min_area).get(0);
		Adding.put(a_item.objectID,  min);
		Adding =  outputLists.get(max_area).get(1);
		Adding.put(a_item.objectID,  max);

		if(logEnable){
			log.info("Adding size = {}", Adding.size() );
			log.info(" a_item.id = {} ", a_item.objectID );
		}
	}


    public static void partitionSubList(ArrayList<item> itemList,  double[] split_value){

		for(int i=0; i<itemList.size(); i++){
			item a_item = itemList.get(i);
			int length = a_item.instances.size();
			for(int j=0; j<length; j++){
				//System.out.println("instance dim = "+ a_item.instances.get(j).a_point.dimCount  );
				int ret_areaID = a_item.instances.get(j).partition(split_value, splitNum);
				try{
				MOPS[ret_areaID].write(a_item.instances.get(j));	
				}catch(IOException e){e.printStackTrace();}
			}
		}

		//try{
		//MOPS[0].write("0.txt is finished writing.\n");
		//}catch(IOException e){e.printStackTrace();}
	
		for(int i=0; i<splitNum; i++){
			try{
			MOPS[i].close();
			}catch(IOException e){e.printStackTrace();}
		}

	}

	public static void main(String args[]){

	outputLists = new ArrayList< HashMap<Integer, HashMap<Integer, instance.point> >  > () ;

	Properties prop = new Properties();

	try {
		prop.load(new FileInputStream("config.properties"));;

    	}catch (IOException ex) {
    		ex.printStackTrace();
		}
	int objectNum = Integer.parseInt(prop.getProperty("objectNum"));
	int dim = Integer.parseInt(prop.getProperty("dim"));
	String input = prop.getProperty("input");
	testPruningRule.dim = dim;
	testPruningRule.objectNum = objectNum;
	
	if(logEnable){
		log.info("dim = {}, objectNum = {} ", dim, objectNum );
	}

	ArrayList<item> itemList= new ArrayList<item>();
    try {
		BufferedReader br;
		FileReader	fd = new FileReader(input);
		br = new BufferedReader(fd);

		int currObject = 0;
		itemList.add(0, new item(currObject));

        String line = br.readLine();
        while (line != null) {
			String[] parts = line.split(" ");
			if(Integer.parseInt(parts[0]) ==currObject ){
			//if(logEnable){
				////log.info("parts[0] = {}, parts[4] = {} ", parts[0], parts[4]);
				//}
			}
			else{
				currObject++;
				assert (currObject == Integer.parseInt(parts[0])): "the object ID doesn't match";
				itemList.add(currObject, new item(currObject));
			}
			itemList.get(currObject).addInstance(parts,dim );
			//if(logEnable)
				//log.info("{}",line);
			
            line = br.readLine();
		}
        br.close();
    } 
	catch (FileNotFoundException foef){
		foef.printStackTrace();
	}
	catch (IOException ex){
		ex.printStackTrace();
	}		
	
	split_Value = new double[MaxSplitNum];
	MOPS = new MyOutputStream[MaxSplitNum];
	for(int i=0;i<MOPS.length;i++){
		
		MOPS[i] = new MyOutputStream();	
	}
	if(logEnable){ System.out.println("MOPS size = "+MOPS.length); }
	int split_index = 0;
	
	try{
		BufferedReader br;
		FileReader	fd = new FileReader("split.txt");
		br = new BufferedReader(fd);
		String line = br.readLine();
		while(line != null && split_index < MaxSplitNum){

			if(Double.parseDouble(line) >=0 ){
			
			split_Value[split_index] = Double.parseDouble(line);
			if(logEnable){ System.out.println("split index = "+split_index); }
			MOPS[split_index].setName(Integer.toString(split_index)+".txt");
			
			outputLists.add(  new  HashMap<Integer, HashMap<Integer, instance.point> >   () ) ;
			
			split_index ++;
			
			}
		line = br.readLine();
		}
	}
	catch (FileNotFoundException foef){
		foef.printStackTrace();
	}
	catch (IOException ex){
		ex.printStackTrace();
	}
	//if(logEnable)
	//for(int i=0; i<split_index; i++){
		//log.info("split_Value[i] = {}", split_Value[i]);
	//}
	splitNum = split_index;
	partitionSubList(itemList,split_Value);

	for( HashMap i: outputLists ){
			i.put(0,  new  HashMap<Integer, instance.point> () ) ;// 0 for min
			i.put(1,  new  HashMap<Integer, instance.point> () ) ;// 1 for max
	}

	for(item i:itemList){
		
		FindleftExtremePoint(i);
	}


	try{
		//Serialization ser = new Serialization();
		//byte[] binary = ser.serialize(outputLists);
		//if(logEnable)
			//System.out.println("binary size = "+ binary.length );
		//FileOutputStream output = new FileOutputStream(new File("MAX_MIN_POINT"));
		//IOUtils.write(binary, output );

		FileOutputStream fileOut = new FileOutputStream( "MAX_MIN" );
		ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
		outStream.writeObject(outputLists);
		outStream.close();
		fileOut.close();

	}catch(IOException e){ e.printStackTrace();  }
	 //catch(ClassNotFoundException cnfe){}


	}
}
