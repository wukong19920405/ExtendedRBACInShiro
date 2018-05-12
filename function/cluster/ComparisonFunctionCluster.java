package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.ComparisonFunction;
import com.wukong.function.Function;



public class ComparisonFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = ComparisonFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new ComparisonFunction((String) (it.next())));

        return set;
    }

}

