package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DoubleAttribute;
import com.wukong.attribute.IntegerAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class NumericConvertFunction extends FunctionBase{

	/**
     * Standard identifier for the double-to-integer function.
     */
    public static final String NAME_DOUBLE_TO_INTEGER = FUNCTION_NS + "double-to-integer";

    /**
     * Standard identifier for the integer-to-double function.
     */
    public static final String NAME_INTEGER_TO_DOUBLE = FUNCTION_NS + "integer-to-double";

    // private identifiers for the supported functions
    private static final int ID_DOUBLE_TO_INTEGER = 0;
    private static final int ID_INTEGER_TO_DOUBLE = 1;

    /**
     * Creates a new <code>NumericConvertFunction</code> object.
     * 
     * @param functionName the standard XACML name of the function to be handled by this object,
     *            including the full namespace
     * 
     * @throws IllegalArgumentException if the function is unknwon
     */
    public NumericConvertFunction(String functionName) {
        super(functionName, getId(functionName), getArgumentType(functionName), false, 1,
                getReturnType(functionName), false);
    }

    /**
     * Private helper that returns the internal identifier used for the given standard function.
     */
    private static int getId(String functionName) {
        if (functionName.equals(NAME_DOUBLE_TO_INTEGER))
            return ID_DOUBLE_TO_INTEGER;
        else if (functionName.equals(NAME_INTEGER_TO_DOUBLE))
            return ID_INTEGER_TO_DOUBLE;
        else
            throw new IllegalArgumentException("unknown convert function " + functionName);
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.add(NAME_DOUBLE_TO_INTEGER);
        set.add(NAME_INTEGER_TO_DOUBLE);

        return set;
    }

    /**
     * Private helper that returns the type used for the given standard function. Note that this
     * doesn't check on the return value since the method always is called after getId, so we assume
     * that the function is present.
     */
    private static String getArgumentType(String functionName) {
        if (functionName.equals(NAME_DOUBLE_TO_INTEGER))
            return DoubleAttribute.identifier;
        else
            return IntegerAttribute.identifier;
    }

    /**
     * Private helper that returns the return type for the given standard function. Note that this
     * doesn't check on the return value since the method always is called after getId, so we assume
     * that the function is present.
     */
    private static String getReturnType(String functionName) {
        if (functionName.equals(NAME_DOUBLE_TO_INTEGER))
            return IntegerAttribute.identifier;
        else
            return DoubleAttribute.identifier;
    }

    /**
     * Evaluate the function, using the specified parameters.
     * 
     * @param inputs a <code>List</code> of <code>Evaluatable</code> objects representing the
     *            arguments passed to the function
     * @param context an <code>EvaluationCtx</code> so that the <code>Evaluatable</code> objects can
     *            be evaluated
     * @return an <code>EvaluationResult</code> representing the function's result
     */
    public EvaluationResult evaluate(List inputs, EvaluationCtx context) {

        // Evaluate the arguments
        AttributeValue[] argValues = new AttributeValue[inputs.size()];
        EvaluationResult result = evalArgs(inputs, context, argValues);
        if (result != null)
            return result;

        // Now that we have real values, perform the numeric conversion
        // operation in the manner appropriate for this function.
        switch (getFunctionId()) {
        case ID_DOUBLE_TO_INTEGER: {
            double arg0 = ((DoubleAttribute) argValues[0]).getValue();
            long longValue = (long) arg0;

            result = new EvaluationResult(new IntegerAttribute(longValue));
            break;
        }
        case ID_INTEGER_TO_DOUBLE: {
            long arg0 = ((IntegerAttribute) argValues[0]).getValue();
            double doubleValue = (double) arg0;

            result = new EvaluationResult(new DoubleAttribute(doubleValue));
            break;
        }
        }

        return result;
    }
}
