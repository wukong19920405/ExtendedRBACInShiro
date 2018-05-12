package com.wukong.function;

import java.net.URI;
import java.net.URISyntaxException;

import com.wukong.attribute.AttributeValue;

public abstract class FunctionOld {
	
	public static final String FUNCTION_NS = "urn:oasis:names:tc:xacml:1.0:function:";

	    /**
	     * The standard namespace where all XACML 2.0 spec-defined functions live
	     */
	public static final String FUNCTION_NS_2 = "urn:oasis:names:tc:xacml:2.0:function:";

	    /**
	     * The standard namespace where all XACML 3.0 spec-defined functions live
	     */
	public static final String FUNCTION_NS_3 = "urn:oasis:names:tc:xacml:3.0:function:";
	
	private String funcname;
	
	private int funcId;
	
	private String returntype;
	
	public FunctionOld(String funcname,int funcId,String returntype){
		this.funcname=funcname;
		this.funcId=funcId;
		this.returntype=returntype;
	}
	
	public String getNmae(){
		return funcname;
	}
	
	public URI getIdentifier(){
		try {
            return new URI(funcname);
        } catch (URISyntaxException use) {
            throw new IllegalArgumentException("invalid URI");
        }
	}
	
	public String getType(){
		return returntype;
	}
	
	public abstract boolean evalute (AttributeValue input,AttributeValue context);
}
