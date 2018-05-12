package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.ConditionBagFunction;
import com.wukong.function.Function;

public class ConditionSetFunctionCluster implements FunctionCluster {

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = ConditionBagFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new ConditionBagFunction((String) (it.next())));

        return set;
    }

}

