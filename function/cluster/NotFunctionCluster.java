package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.Function;
import com.wukong.function.NotFunction;



public class NotFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = NotFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new NotFunction((String) (it.next())));

        return set;
    }

}