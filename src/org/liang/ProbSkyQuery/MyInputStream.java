package dist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.HashMap;

public class MyInputStream {
    File file;
    FileReader fd;
    BufferedReader bd;
	public static int dim = 2;
	public MyInputStream(){}

    public MyInputStream(String a_name){
        
        try{
            file = new File(a_name);
            if (!file.exists()){
                file.createNewFile();    
            }
            fd = new FileReader(file.getAbsoluteFile());
            bd = new BufferedReader(fd);
        }
        catch (IOException e){
            e.printStackTrace();    
        }    
    }
        
    public void setName(String a_name){
        try{
            file = new File(a_name);
            if (!file.exists()){
                file.createNewFile();    
            }
            fd = new FileReader(file.getAbsoluteFile());
            bd = new BufferedReader(fd);
        }
        catch (IOException e){
            e.printStackTrace();    
        }    
    }

	public void setDim(int a_dim){
		
		dim = a_dim;	
	}

	public void close()throws IOException{
		bd.close();
	}

	public String readLine()throws FileNotFoundException, IOException {
		
		return bd.readLine();	
	}

    public void read(List<item> listItem  /*, HashMap<item> hashItem*/  ) throws FileNotFoundException, IOException{
	
		int currObject = 0;
		listItem.add(0, new item(currObject));
		//hashItem.put(0, new item(currObject));
		String line = this.readLine();
		while(line !=null){
			String [] parts = line.split(" ");
			if(Integer.parseInt(parts[0]) == currObject  ){
				
			}
			else {
				currObject = Integer.parseInt(parts[0]);
				assert(currObject == Integer.parseInt(parts[0])):"the object ID doesn't match";
				listItem.add( new item(currObject));	
				//hashItem.put(Integer.parseInt(parts[0]), new item(currObject)  );
			}	
			listItem.get( listItem.size()-1 ).addInstance(parts,dim);	
			//hashItem.get(currObject).addInstance(parts,dim);
			line = this.readLine();
		}	
		
	}
   
    
    //public void write(int b) throws IOException
    //{
        //bw.write(b);
    //}

    //public void write(instance a_inst ) throws IOException
    //{
        //String temp = Integer.toString(a_inst.objectID) + " "+ Integer.toString(a_inst.instanceID)+" ";
        //temp += a_inst.a_point.toString();
        //temp += Double.toString(a_inst.prob)+"\n";
		////System.out.println(temp);
        //this.write(temp);
    //}    

}
