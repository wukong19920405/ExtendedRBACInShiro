package com.wukong.function;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.wukong.function.cluster.AbsFunctionCluster;
import com.wukong.function.cluster.AddFunctionCluster;
import com.wukong.function.cluster.ComparisonFunctionCluster;
import com.wukong.function.cluster.ConditionBagFunctionCluster;
import com.wukong.function.cluster.ConditionSetFunctionCluster;
import com.wukong.function.cluster.DateMathFunctionCluster;
import com.wukong.function.cluster.DivideFunctionCluster;
import com.wukong.function.cluster.EqualFunctionCluster;
import com.wukong.function.cluster.FloorFunctionCluster;
import com.wukong.function.cluster.GeneralBagFunctionCluster;
import com.wukong.function.cluster.GeneralSetFunctionCluster;
import com.wukong.function.cluster.LogicalFunctionCluster;
import com.wukong.function.cluster.ModFunctionCluster;
import com.wukong.function.cluster.MultiplyFunctionCluster;
import com.wukong.function.cluster.NotFunctionCluster;
import com.wukong.function.cluster.NumericConvertFunctionCluster;
import com.wukong.function.cluster.RoundFunctionCluster;
import com.wukong.function.cluster.StringFunctionCluster;
import com.wukong.function.cluster.StringNormalizeFunctionCluster;
import com.wukong.function.cluster.SubtractFunctionCluster;

public class StandardFunctionFactory extends BaseFunctionFactory{

	private static volatile StandardFunctionFactory functionFactory=null;
	
	
	private static Set<Function>  functions=null;

	
	private StandardFunctionFactory(Set<Function> supportedFunctions){
		super(supportedFunctions);
	}
	
	private static void initFunctions(){
		functions=new HashSet<Function>();
		functions.addAll((new EqualFunctionCluster()).getSupportedFunctions());
		functions.addAll((new LogicalFunctionCluster()).getSupportedFunctions());
		//targetFunctions.addAll((new NOfFunctionCluster()).getSupportedFunctions());	   		TODO
        // add NotFunction
        functions.addAll((new NotFunctionCluster()).getSupportedFunctions());
        // add ComparisonFunction
        functions.addAll((new ComparisonFunctionCluster()).getSupportedFunctions());
        // add MatchFunction
        //targetFunctions.addAll((new MatchFunctionCluster()).getSupportedFunctions());			TODO
        functions.add(new TimeInRangeFunction());
        // add condition function IPInRange
        functions.add(new IPInRangeFunction());        
        // add condition functions from BagFunction
        functions.addAll((new ConditionBagFunctionCluster()).getSupportedFunctions());
        // add condition functions from SetFunction
        functions.addAll((new ConditionSetFunctionCluster()).getSupportedFunctions());
        // add condition functions from HigherOrderFunction
        //functions.addAll((new HigherOrderFunctionCluster()).getSupportedFunctions());			TODO
     // add AddFunction
        functions.addAll((new AddFunctionCluster()).getSupportedFunctions());
        // add SubtractFunction
        functions.addAll((new SubtractFunctionCluster()).getSupportedFunctions());
        // add MultiplyFunction
        functions.addAll((new MultiplyFunctionCluster()).getSupportedFunctions());
        // add DivideFunction
        functions.addAll((new DivideFunctionCluster()).getSupportedFunctions());
        // add ModFunction
        functions.addAll((new ModFunctionCluster()).getSupportedFunctions());
        // add AbsFunction
        functions.addAll((new AbsFunctionCluster()).getSupportedFunctions());
        // add RoundFunction
        functions.addAll((new RoundFunctionCluster()).getSupportedFunctions());
        // add FloorFunction
        functions.addAll((new FloorFunctionCluster()).getSupportedFunctions());
        // add DateMathFunction
        functions.addAll((new DateMathFunctionCluster()).getSupportedFunctions());
        // add general functions from BagFunction
        functions.addAll((new GeneralBagFunctionCluster()).getSupportedFunctions());
        // add NumericConvertFunction
        functions.addAll((new NumericConvertFunctionCluster()).getSupportedFunctions());
        // add StringNormalizeFunction
        functions.addAll((new StringNormalizeFunctionCluster()).getSupportedFunctions());
        // add general functions from SetFunction
        functions.addAll((new GeneralSetFunctionCluster()).getSupportedFunctions());
        // add the XACML 2.0 string functions
        functions.addAll((new StringFunctionCluster()).getSupportedFunctions());
        // add the XACML 3.0 start with functions
        //functions.addAll((new StringComparingFunctionCluster()).getSupportedFunctions());
        // add the XACML 3.0 start with functions
        //functions.addAll((new StringConversionFunctionCluster()).getSupportedFunctions());
        // add the XACML 3.0 start with functions
        //functions.addAll((new SubStringFunctionCluster()).getSupportedFunctions());
        // add the XACML 3.0 start with functions
        //functions.addAll((new StringCreationFunctionCluster()).getSupportedFunctions());  
        // add the XACML 3.0 start with functions
        //functions.addAll((new XPathFunctionCluster()).getSupportedFunctions());  

	}
	
	public static StandardFunctionFactory getFunctionFactory(){
		if(functionFactory==null){
			synchronized(StandardFunctionFactory.class){
				if(functionFactory==null){
					initFunctions();
					functionFactory=new StandardFunctionFactory(functions);
				}
			}
		}
		return functionFactory;
	}
}
