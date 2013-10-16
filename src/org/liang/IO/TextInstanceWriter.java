package org.liang.IO;

import org.liang.DataStructures.instance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextInstanceWriter{

	private String outputFile;
	private BufferedWriter bw;
	private String tmpSuffix;

	public TextInstanceWriter(String outputFile) {
		this.outputFile = outputFile;
		this.tmpSuffix = ".txt";
		initialize();
	}

    public void write(instance a_inst ) throws IOException
    {
        String temp = Integer.toString(a_inst.objectID) + " "+ Integer.toString(a_inst.instanceID)+" ";
        temp += a_inst.a_point.toString();
        temp += Double.toString(a_inst.prob)+"\n";

        this.write(temp);
    }    

	private void initialize() throws IOException {
		bw = new BufferedWriter(new FileWriter(outputFile + tmpSuffix, true));
	}

	public void close() throws IOException {
		bw.flush();
		bw.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
