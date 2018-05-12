package com.wukong.function.cluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wukong.function.DateMathFunction;
import com.wukong.function.Function;



public class DateMathFunctionCluster implements FunctionCluster {

    public Set<Function>  getSupportedFunctions() {
        Set<Function>  set = new HashSet<Function> ();
        Iterator it = DateMathFunction.getSupportedIdentifiers().iterator();

        while (it.hasNext())
            set.add(new DateMathFunction((String) (it.next())));

        return set;
    }

}