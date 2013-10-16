package dist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyOutputStream {
    
    //OutputStream myOPS;
    File file;
    FileWriter fw;
    BufferedWriter bw;

	public MyOutputStream(){}

    public MyOutputStream(String a_name){
        
        try{
            file = new File(a_name);
            if (!file.exists()){
                file.createNewFile();    
            }
            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
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
            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
        }
        catch (IOException e){
            e.printStackTrace();    
        }    
    }

	public void close()throws IOException{
		bw.flush();
		bw.close();
		
	}

    public void write(String a_string ) throws IOException{
        bw.write(a_string);    
    }    
    
    public void write(int b) throws IOException
    {
        bw.write(b);
    }

    public void write(instance a_inst ) throws IOException
    {
        String temp = Integer.toString(a_inst.objectID) + " "+ Integer.toString(a_inst.instanceID)+" ";
        temp += a_inst.a_point.toString();
        temp += Double.toString(a_inst.prob)+"\n";
		//System.out.println(temp);
        this.write(temp);
    }    

}
