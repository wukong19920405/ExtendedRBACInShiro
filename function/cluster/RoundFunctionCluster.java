package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.Function;
import com.wukong.function.RoundFunction;



public class RoundFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = RoundFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new RoundFunction((String) (it.next())));

        return set;
    }

}
