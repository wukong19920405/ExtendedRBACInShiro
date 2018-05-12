package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.EqualFunction;
import com.wukong.function.Function;



public class EqualFunctionCluster implements FunctionCluster{

	 public Set<Function> getSupportedFunctions() {
	        Set<Function> set = new HashSet<Function>();
	        Iterator it = EqualFunction.getSupportedIdentifiers().iterator();

	        while (it.hasNext())
	            set.add(new EqualFunction((String) (it.next())));

	        return set;
	    }
}
