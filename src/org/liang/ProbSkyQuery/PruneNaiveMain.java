package org.liang.ProbSkyQuery;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class PruneNaiveMain{

	private static org.apache.log4j.Logger log = Logger.getRootLogger();
	public static boolean verbose = true;
	
	public static void initializeLogger(String fileName)
	{
		FileAppender fa = new FileAppender();
		fa.setName("FileLogger");
		fa.setFile(fileName+ ".log");
		fa.setLayout(new PatternLayout("%d{HH:mm:ss}  %m%n  "));
		fa.setThreshold(Level.INFO);
		fa.setAppend(true);
		fa.activateOptions();
		log.addAppender(fa);
	}

	public static void main(String [] args){
		
		initializeLogger("NaiveProb");
		NaivePrune NP = new NaivePrune();	
		//P12.preprocess();
		NP.prune();
	}	
}
