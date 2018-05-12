package com.wukong.function;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.wukong.attribute.AttributeValue;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.context.Status;

public abstract class FunctionBase implements Function{

	public static final String FUNCTION_NS="urn:oasis:names:tc:xacml:1.0:function:";
	
	public static final String FUNCTION_NS_2 = "urn:oasis:names:tc:xacml:2.0:function:";

    /**
     * The standard namespace where all XACML 3.0 spec-defined functions live
     */
    public static final String FUNCTION_NS_3 = "urn:oasis:names:tc:xacml:3.0:function:";
    
    private static List processErrorList=null;
	
	private String functionName;
	
	private int functionId;
	
	private String returnType;
	private boolean returnsBag;
	
	private boolean singleType;//???
	
	private String paramType;
	private boolean paramIsBag;
	private int numParams;
	private int minParams;
	
	private String[] paramTypes;
	private boolean[] paramsAreBags;
	
	 public FunctionBase(String functionName, int functionId, String paramType, boolean paramIsBag,
	            int numParams, String returnType, boolean returnsBag) {
	        this(functionName, functionId, returnType, returnsBag);

	        singleType = true;

	        this.paramType = paramType;
	        this.paramIsBag = paramIsBag;
	        this.numParams = numParams;
	        this.minParams = 0;
	    }

	    /**
	     * Constructor that sets up the function as having some number of parameters all of the same
	     * given type. If <code>numParams</code> is -1, then the length is variable, and then
	     * <code>minParams</code> may be used to specify a minimum number of parameters. If
	     * <code>numParams</code> is not -1, then <code>minParams</code> is ignored.
	     * 
	     * @param functionName the name of this function as used by the factory and any XACML policies
	     * @param functionId an optional identifier that can be used by your code for convenience
	     * @param paramType the type of all parameters to this function, as used by the factory and any
	     *            XACML documents
	     * @param paramIsBag whether or not every parameter is actually a bag of values
	     * @param numParams the number of parameters required by this function, or -1 if any number are
	     *            allowed
	     * @param minParams the minimum number of parameters required if <code>numParams</code> is -1
	     * @param returnType the type returned by this function, as used by the factory and any XACML
	     *            documents
	     * @param returnsBag whether or not this function returns a bag of values
	     */
	    public FunctionBase(String functionName, int functionId, String paramType, boolean paramIsBag,
	            int numParams, int minParams, String returnType, boolean returnsBag) {
	        this(functionName, functionId, returnType, returnsBag);

	        singleType = true;

	        this.paramType = paramType;
	        this.paramIsBag = paramIsBag;
	        this.numParams = numParams;
	        this.minParams = minParams;
	    }

	    /**
	     * Constructor that sets up the function as having different types for each given parameter.
	     * 
	     * @param functionName the name of this function as used by the factory and any XACML policies
	     * @param functionId an optional identifier that can be used by your code for convenience
	     * @param paramTypes the type of each parameter, in order, required by this function, as used by
	     *            the factory and any XACML documents
	     * @param paramIsBag whether or not each parameter is actually a bag of values
	     * @param returnType the type returned by this function, as used by the factory and any XACML
	     *            documents
	     * @param returnsBag whether or not this function returns a bag of values
	     */
	    public FunctionBase(String functionName, int functionId, String[] paramTypes,
	            boolean[] paramIsBag, String returnType, boolean returnsBag) {
	        this(functionName, functionId, returnType, returnsBag);

	        singleType = false;

	        this.paramTypes = paramTypes;
	        this.paramsAreBags = paramIsBag;
	    }

	    /**
	     * Constructor that sets up some basic values for functions that will take care of parameter
	     * checking on their own. If you use this constructor for your function class, then you must
	     * override the two check methods to make sure that parameters are correct.
	     * 
	     * @param functionName the name of this function as used by the factory and any XACML policies
	     * @param functionId an optional identifier that can be used by your code for convenience
	     * @param returnType the type returned by this function, as used by the factory and any XACML
	     *            documents
	     * @param returnsBag whether or not this function returns a bag of values
	     */
	    public FunctionBase(String functionName, int functionId, String returnType, boolean returnsBag) {
	        this.functionName = functionName;
	        this.functionId = functionId;
	        this.returnType = returnType;
	        this.returnsBag = returnsBag;
	    }
	    
	    public URI getIdentifier() {
	        // this is to get around the exception handling problems, but may
	        // change if this code changes to include exceptions from the
	        // constructors
	        try {
	            return new URI(functionName);
	        } catch (URISyntaxException use) {
	            throw new IllegalArgumentException("invalid URI");
	        }
	    }

	    /**
	     * Returns the name of the function to be handled by this particular object.
	     * 
	     * @return the function name
	     */
	    public String getFunctionName() {
	        return functionName;
	    }

	    /**
	     * Returns the Identifier of the function to be handled by this particular object.
	     * 
	     * @return the function Id
	     */
	    public int getFunctionId() {
	        return functionId;
	    }

	    /**
	     * Returns the same value as <code>getReturnType</code>. This is here to support the
	     * <code>Expression</code> interface.
	     * 
	     * @return the return type
	     */
	    public URI getType() {
	        return getReturnType();
	    }

	    /**
	     * Get the attribute type returned by this function.
	     * 
	     * @return a <code>URI</code> indicating the attribute type returned by this function
	     */
	    public URI getReturnType() {
	        try {
	            return new URI(returnType);
	        } catch (Exception e) {
	            return null;
	        }
	    }

	    /**
	     * Returns true if this function returns a bag of values.
	     * 
	     * @return true if the function returns a bag, false otherwise
	     */
	    public boolean returnsBag() {
	        return returnsBag;
	    }

	    /**
	     * Returns the return type for this particular object.
	     * 
	     * @return the return type
	     */
	    public String getReturnTypeAsString() {
	        return returnType;
	    }

	    protected EvaluationResult makeProcessingError(String message){
	    	if(processErrorList==null){
	    		String[] stringList={Status.STATUS_PROCESSING_ERROR};
	    		processErrorList=Arrays.asList(stringList);
	    	}
	    	Status errStatus=new Status(processErrorList,message);
	    	return new EvaluationResult(errStatus);
	    }
	    
	    protected EvaluationResult evalArgs(List<Evaluatable> params,EvaluationCtx context,AttributeValue[] args){
	    	Iterator iter=params.iterator();
	    	int index=0;
	    	while(iter.hasNext()){
	    		Evaluatable eval=(Evaluatable)iter.next();
	    		EvaluationResult result=eval.evaluate(context);
	    		if(result.indeterminate())
	    			return result;
	    		args[index++]=result.getAttributeValue();
	    	}
	    	return null;
	    }
	    
	    public void checkInputs(List inputs) throws IllegalArgumentException{
	    	if(singleType){
	    		if(numParams!=-1){
	    			if(inputs.size()!=numParams)
	    				throw new IllegalArgumentException("wrong number args of"+functionName);
	    		}
	    		else if(inputs.size()<minParams)
    				throw new IllegalArgumentException("not enough args of" + functionName);
	    		Iterator it=inputs.iterator();
	    		while(it.hasNext()){
	    			Evaluatable eval=(Evaluatable)it.next();
	    			if(!eval.getType().toString().equals(paramType))
	    				throw new IllegalArgumentException("wrong returnType or returnsBag of"+functionName);
	    		}
	    	}
	    	else{
	    		
	    		if(inputs.size()!=paramType.length())
	    			throw new IllegalArgumentException("wrong number of params"+ functionName);
	    		Iterator it=inputs.iterator();
	    		while(it.hasNext()){
	    			Evaluatable eval=(Evaluatable)it.next();
	    			int index=0;
	    			if((!eval.getType().toString().equals(paramTypes[index]))||eval.returnsBag()!=paramsAreBags[index])
	    				throw new IllegalArgumentException("wrong returnType or returnsBag"+functionName);
	    			index++;
	    		}	
	    	}
	    }
	    public void encode(StringBuilder sb){
	    	sb.append("Function FunctionId= \""+getFunctionName()+"\"/>\n");
	    }
	    
	    public String encode(){
	    	StringBuilder sb=new StringBuilder();
	    	encode(sb);
	    	return sb.toString();
	    }
	    
}
