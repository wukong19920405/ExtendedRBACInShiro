package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.Function;
import com.wukong.function.StringFunction;



public class StringFunctionCluster implements FunctionCluster
{

    public Set<Function> getSupportedFunctions() {
        Set<Function> set = new HashSet<Function>();
        Iterator it = StringFunction.getSupportedIdentifiers().
            iterator();

        while (it.hasNext())
            set.add(new StringFunction((String)(it.next())));

        

        return set;
    }

}
