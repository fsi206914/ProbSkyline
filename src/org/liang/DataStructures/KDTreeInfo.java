package org.liang.ProbSkyQuery;

import org.liang.KDTree.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class KDTreeInfo{

	public HashMap<KDNode, Info> maintain;	

	public final class Info{
		
		public HashMap<Integer, double> theta;	
		public double pi;
		public int X;

	}
}
