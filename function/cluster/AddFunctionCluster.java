package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.AddFunction;
import com.wukong.function.Function;



public class AddFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = AddFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new AddFunction((String) (it.next())));

        return set;
    }

}
