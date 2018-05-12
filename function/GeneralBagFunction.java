package com.wukong.function;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BagAttribute;
import com.wukong.attribute.IntegerAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;

@SuppressWarnings("unchecked")
public class GeneralBagFunction extends BagFunction{

	private static final int ID_BASE_ONE_AND_ONLY = 0;
    private static final int ID_BASE_BAG_SIZE = 1;
    private static final int ID_BASE_BAG = 2;
    
    private static HashMap paramMap;
    private static Set supportedIds;//????
    
    static{
    	paramMap=new HashMap();
    	for(int i=0;i<baseTypes.length;i++){
    		String paramType=baseTypes[i];
    		String functionName=FUNCTION_NS+simpleTypes[i];
    		paramMap.put(functionName+NAME_BASE_ONE_AND_ONLY, new BagParameters(ID_BASE_ONE_AND_ONLY,paramType,true,1,paramType,false));
    		paramMap.put(functionName+NAME_BASE_BAG_SIZE, new BagParameters(ID_BASE_BAG_SIZE,paramType,true,1,IntegerAttribute.identifier,false));
    		paramMap.put(functionName+NAME_BASE_BAG, new BagParameters(ID_BASE_BAG,paramType,false,-1,paramType,true));
    		
    	}
    	for(int i=0;i<baseTypes2.length;i++){
    		String paramType=baseTypes2[i];
    		String functionName=FUNCTION_NS_2+simpleTypes[i];
    		paramMap.put(functionName+NAME_BASE_ONE_AND_ONLY, new BagParameters(ID_BASE_ONE_AND_ONLY,paramType,true,1,paramType,false));
    		paramMap.put(functionName+NAME_BASE_BAG_SIZE, new BagParameters(ID_BASE_BAG_SIZE,paramType,true,1,IntegerAttribute.identifier,false));
    		paramMap.put(functionName+NAME_BASE_BAG, new BagParameters(ID_BASE_BAG,paramType,false,-1,paramType,true));
    	}
    	
    	supportedIds=Collections.unmodifiableSet(paramMap.keySet());
    }
    
    public GeneralBagFunction(String functionName) {
        super(functionName, getId(functionName), getArgumentType(functionName),
                getIsBag(functionName), getNumArgs(functionName), getReturnType(functionName),
                getReturnsBag(functionName));
    }

    /**
     * Constructor that is used to create instances of general-purpose bag functions for new
     * (non-standard) datatypes. This is equivalent to using the <code>getInstance</code> methods in
     * <code>BagFunction</code> and is generally only used by the run-time configuration code.
     * 
     * @param functionName the name of the new function
     * @param datatype the full identifier for the supported datatype
     * @param functionType which kind of Bag function, based on the <code>NAME_BASE_*</code> fields
     */
    public GeneralBagFunction(String functionName, String datatype, String functionType) {
        super(functionName, getId(functionType), datatype, getIsBag(functionType),
                getNumArgs(functionType), getCustomReturnType(functionType, datatype),
                getReturnsBag(functionType));
    }

    /**
     * Private helper that returns the internal identifier used for the given standard function.
     */
    private static int getId(String functionName) {
        BagParameters params = (BagParameters) (paramMap.get(functionName));

        if (params == null)
            throw new IllegalArgumentException("unknown bag function: " + functionName);

        return params.id;
    }

    /**
     * Private helper that returns the argument type for the given standard function. Note that this
     * doesn't check on the return value since the method always is called after getId, so we assume
     * that the function is present.
     */
    private static String getArgumentType(String functionName) {
        return ((BagParameters) (paramMap.get(functionName))).arg;
    }

    /**
     * Private helper that returns if the given standard function takes a bag. Note that this
     * doesn't check on the return value since the method always is called after getId, so we assume
     * that the function is present.
     */
    private static boolean getIsBag(String functionName) {
        return ((BagParameters) (paramMap.get(functionName))).argIsBag;
    }

    /**
     * Private helper that returns the argument count for the given standard function. Note that
     * this doesn't check on the return value since the method always is called after getId, so we
     * assume that the function is present.
     */
    private static int getNumArgs(String functionName) {
        return ((BagParameters) (paramMap.get(functionName))).params;
    }

    /**
     * Private helper that returns the return type for the given standard function. Note that this
     * doesn't check on the return value since the method always is called after getId, so we assume
     * that the function is present.
     */
    private static String getReturnType(String functionName) {
        return ((BagParameters) (paramMap.get(functionName))).returnType;
    }

    /**
     * Private helper that returns if the return type is a bag for the given standard function. Note
     * that this doesn't check on the return value since the method always is called after getId, so
     * we assume that the function is present.
     */
    private static boolean getReturnsBag(String functionName) {
        return ((BagParameters) (paramMap.get(functionName))).returnsBag;
    }
    
    private static String getCustomReturnType(String functionType, String datatype) {
        String ret = ((BagParameters) (paramMap.get(functionType))).returnType;

        if (ret == null)
            return datatype;
        else
            return ret;
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        return supportedIds;
    }
    
    private static class BagParameters{
    	
    	public int id;
    	public String arg;
    	public boolean argIsBag;
    	public int params;
    	public String returnType;
    	public boolean returnsBag;
    	
    	public BagParameters(int id,String arg,boolean argIsBag,int params,String returnType,boolean returnsBag){
    		this.id=id;
    		this.arg=arg;
    		this.argIsBag=argIsBag;
    		this.params=params;
    		this.returnType=returnType;
    		this.returnsBag=returnsBag;
    	}
    	
    }

	@Override
	public EvaluationResult evaluate(List<Evaluatable> inputs, EvaluationCtx context) {
		AttributeValue[] argValues=new AttributeValue[inputs.size()];
		EvaluationResult result=evalArgs(inputs,context,argValues);
		if(result!=null)
			return result;
		AttributeValue attrResult=null;
		
		switch(getFunctionId()){
		case ID_BASE_ONE_AND_ONLY:{
			BagAttribute bag=(BagAttribute)argValues[0];
			if(bag.size()!=1)
				return makeProcessingError(getFunctionName()+" expects "
                        + "a bag that contains a single " + "element, got a bag with " + bag.size()
                        + " elements");
			attrResult=(AttributeValue)(bag.iterator().next());
			break;
		}
		case ID_BASE_BAG_SIZE:{
			BagAttribute bag = (BagAttribute) (argValues[0]);

            attrResult = new IntegerAttribute(bag.size());
            break;
		}
		case ID_BASE_BAG:{
			List<AttributeValue> argsList=Arrays.asList(argValues);
			attrResult=new BagAttribute(getReturnType(),argsList);
			break;
		}
		}
		return new EvaluationResult(attrResult);
	}


}
