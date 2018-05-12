package com.wukong.function;

import java.util.HashSet;
import java.util.Set;

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



public abstract class SetFunction extends FunctionBase{

	public static final String NAME_BASE_INTERSECTION = "-intersection";

    /**
     * Base name for the type-at-least-one-member-of funtions. To get the standard identifier for a
     * given type, use <code>FunctionBase.FUNCTION_NS</code> + the datatype's base name (e.g.,
     * <code>string</code>) + </code>NAME_BASE_AT_LEAST_ONE_MEMBER_OF</code>.
     */
    public static final String NAME_BASE_AT_LEAST_ONE_MEMBER_OF = "-at-least-one-member-of";

    /**
     * Base name for the type-union funtions. To get the standard identifier for a given type, use
     * <code>FunctionBase.FUNCTION_NS</code> + the datatype's base name (e.g., <code>string</code>)
     * + </code>NAME_BASE_UNION</code>.
     */
    public static final String NAME_BASE_UNION = "-union";

    /**
     * Base name for the type-subset funtions. To get the standard identifier for a given type, use
     * <code>FunctionBase.FUNCTION_NS</code> + the datatype's base name (e.g., <code>string</code>)
     * + </code>NAME_BASE_SUBSET</code>.
     */
    public static final String NAME_BASE_SUBSET = "-subset";

    /**
     * Base name for the type-set-equals funtions. To get the standard identifier for a given type,
     * use <code>FunctionBase.FUNCTION_NS</code> + the datatype's base name (e.g.,
     * <code>string</code>) + </code>NAME_BASE_SET_EQUALS</code>.
     */
    public static final String NAME_BASE_SET_EQUALS = "-set-equals";

    /**
     * A complete list of all the XACML datatypes supported by the Set functions in XACML 1.x
     */
    protected static String baseTypes[] = { StringAttribute.identifier,
            BooleanAttribute.identifier, IntegerAttribute.identifier, DoubleAttribute.identifier,
            DateAttribute.identifier, DateTimeAttribute.identifier, TimeAttribute.identifier,
            AnyURIAttribute.identifier, HexBinaryAttribute.identifier,
            DayTimeDurationAttribute.identifier,YearMonthDurationAttribute.identifier};

    /**
     * A complete list of all the XACML datatypes newly supported by the Set functions in XACML 2.0
     */
    protected static String baseTypes2[] = { IPAddressAttribute.identifier,
            DNSNameAttribute.identifier };

    /**
     * A complete list of all the XACML datatypes supported by the Set functions in XACML 1.x, using
     * the "simple" form of the names (eg, string instead of
     * http://www.w3.org/2001/XMLSchema#string)
     */
    protected static String simpleTypes[] = { "string", "boolean", "integer", "double", "date",
            "dateTime", "time", "anyURI", "hexBinary", "dayTimeDuration",
            "yearMonthDuration" };

    /**
     * A complete list of all the XACML datatypes newly supported by the Set functions in XACML 2.0,
     * using the "simple" form of the names (eg, string instead of
     * http://www.w3.org/2001/XMLSchema#string)
     */
    protected static String simpleTypes2[] = { "ipAddress", "dnsName" };

    /**
     * Creates a new instance of the intersection set function. This should be used to create
     * support for any new attribute types and then the new <code>SetFunction</code> object should
     * be added to the factory (all set functions for the base types are already installed in the
     * factory).
     * 
     * @param functionName the name of the function
     * @param argumentType the attribute type this function will work with
     * 
     * @return a new <code>SetFunction</code> for the given type
     */
    public static SetFunction getIntersectionInstance(String functionName, String argumentType) {
        return new GeneralSetFunction(functionName, argumentType, NAME_BASE_INTERSECTION);
    }

    /**
     * Creates a new instance of the at-least-one-member-of set function. This should be used to
     * create support for any new attribute types and then the new <code>SetFunction</code> object
     * should be added to the factory (all set functions for the base types are already installed in
     * the factory).
     * 
     * @param functionName the name of the function
     * @param argumentType the attribute type this function will work with
     * 
     * @return a new <code>SetFunction</code> for the given type
     */
    public static SetFunction getAtLeastOneInstance(String functionName, String argumentType) {
        return new ConditionSetFunction(functionName, argumentType,
                NAME_BASE_AT_LEAST_ONE_MEMBER_OF);
    }

    /**
     * Creates a new instance of the union set function. This should be used to create support for
     * any new attribute types and then the new <code>SetFunction</code> object should be added to
     * the factory (all set functions for the base types are already installed in the factory).
     * 
     * @param functionName the name of the function
     * @param argumentType the attribute type this function will work with
     * 
     * @return a new <code>SetFunction</code> for the given type
     */
    public static SetFunction getUnionInstance(String functionName, String argumentType) {
        return new GeneralSetFunction(functionName, argumentType, NAME_BASE_UNION);
    }

    /**
     * Creates a new instance of the subset set function. This should be used to create support for
     * any new attribute types and then the new <code>SetFunction</code> object should be added to
     * the factory (all set functions for the base types are already installed in the factory).
     * 
     * @param functionName the name of the function
     * @param argumentType the attribute type this function will work with
     * 
     * @return a new <code>SetFunction</code> for the given type
     */
    public static SetFunction getSubsetInstance(String functionName, String argumentType) {
        return new ConditionSetFunction(functionName, argumentType, NAME_BASE_SUBSET);
    }

    /**
     * Creates a new instance of the equals set function. This should be used to create support for
     * any new attribute types and then the new <code>SetFunction</code> object should be added to
     * the factory (all set functions for the base types are already installed in the factory).
     * 
     * @param functionName the name of the function
     * @param argumentType the attribute type this function will work with
     * 
     * @return a new <code>SetFunction</code> for the given type
     */
    public static SetFunction getSetEqualsInstance(String functionName, String argumentType) {
        return new ConditionSetFunction(functionName, argumentType, NAME_BASE_SET_EQUALS);
    }

    /**
     * Protected constuctor used by the general and condition subclasses. If you need to create a
     * new <code>SetFunction</code> instance you should either use one of the
     * <code>getInstance</code> methods or construct one of the sub-classes directly.
     * 
     * @param functionName the identitifer for the function
     * @param functionId an optional, internal numeric identifier
     * @param argumentType the datatype this function accepts
     * @param returnType the datatype this function returns
     * @param returnsBag whether this function returns bags
     */
    protected SetFunction(String functionName, int functionId, String argumentType,
            String returnType, boolean returnsBag) {
        super(functionName, functionId, argumentType, true, 2, returnType, returnsBag);
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.addAll(ConditionSetFunction.getSupportedIdentifiers());
        set.addAll(GeneralSetFunction.getSupportedIdentifiers());

        return set;
    }

}
