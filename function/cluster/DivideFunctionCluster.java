package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.DivideFunction;
import com.wukong.function.Function;



public class DivideFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = DivideFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new DivideFunction((String) (it.next())));

        return set;
    }

}
