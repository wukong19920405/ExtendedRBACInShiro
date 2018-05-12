package com.wukong.function;



import com.wukong.attribute.AnyURIAttribute;
import com.wukong.attribute.BooleanAttribute;
import com.wukong.attribute.DNSNameAttribute;
import com.wukong.attribute.DateAttribute;
import com.wukong.attribute.DateTimeAttribute;
import com.wukong.attribute.DayTimeDurationAttribute;
import com.wukong.attribute.DoubleAttribute;
import com.wukong.attribute.HexBinaryAttribute;
import com.wukong.attribute.IPAddressAttribute;
import com.wukong.attribute.IntegerAttribute;
import com.wukong.attribute.StringAttribute;
import com.wukong.attribute.TimeAttribute;
import com.wukong.attribute.YearMonthDurationAttribute;

public abstract class BagFunction extends FunctionBase{
	
	public static final String NAME_BASE_ONE_AND_ONLY = "-one-and-only";

    /**
     * Base name for the type-bag-size funtions. To get the standard identifier for a given type,
     * use <code>FunctionBase.FUNCTION_NS</code> + the datatype's base name (e.g.,
     * <code>string</code>) + </code>NAME_BASE_BAG_SIZE</code>.
     */
    public static final String NAME_BASE_BAG_SIZE = "-bag-size";

    /**
     * Base name for the type-is-in. To get the standard identifier for a given type, use
     * <code>FunctionBase.FUNCTION_NS</code> + the datatype's base name (e.g., <code>string</code>)
     * + </code>NAME_BASE_IS_IN</code>.
     */
    public static final String NAME_BASE_IS_IN = "-is-in";

    /**
     * Base name for the type-bag funtions. To get the standard identifier for a given type, use
     * <code>FunctionBase.FUNCTION_NS</code> + the datatype's base name (e.g., <code>string</code>)
     * + </code>NAME_BASE_BAG</code>.
     */
    public static final String NAME_BASE_BAG = "-bag";
    
    protected static String baseTypes[]={StringAttribute.identifier,
            BooleanAttribute.identifier, IntegerAttribute.identifier, DoubleAttribute.identifier,
            DateAttribute.identifier, DateTimeAttribute.identifier, TimeAttribute.identifier,
            AnyURIAttribute.identifier, HexBinaryAttribute.identifier,
            DayTimeDurationAttribute.identifier, YearMonthDurationAttribute.identifier};
    
    protected static String baseTypes2[]={IPAddressAttribute.identifier,
            DNSNameAttribute.identifier};
    
    protected static String simpleTypes[] = { "string", "boolean", "integer", "double", "date",
            "dateTime", "time", "anyURI", "hexBinary",  "dayTimeDuration","yearMonthDuration"};

    /**
     * A complete list of all the 2.0 XACML datatypes newly supported by the Bag functions, using
     * the "simple" form of the names (eg, string instead of
     * http://www.w3.org/2001/XMLSchema#string)
     */
    protected static String simpleTypes2[] = { "ipAddress", "dnsName" };
    
    private static final boolean[] bagParams={true,false};
    
    protected BagFunction(String functionName,int functionId,String paramType,boolean paramIsBag,int numParams,String returnType,boolean returnsBag){
    	super(functionName,functionId,paramType,paramIsBag,numParams,returnType,returnsBag);
    }
    
    protected BagFunction(String functionName,int functionId,String[] params){
    	super(functionName,functionId,params,bagParams,BooleanAttribute.identifier,false);
    }

}
