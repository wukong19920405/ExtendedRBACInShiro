package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.FloorFunction;
import com.wukong.function.Function;



public class FloorFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = FloorFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new FloorFunction((String) (it.next())));

        return set;
    }

}
