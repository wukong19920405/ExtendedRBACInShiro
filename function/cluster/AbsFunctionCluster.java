package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.AbsFunction;
import com.wukong.function.Function;



public class AbsFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = AbsFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new AbsFunction((String) (it.next())));

        return set;
    }

}