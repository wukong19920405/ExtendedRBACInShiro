package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DoubleAttribute;
import com.wukong.attribute.IntegerAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class AbsFunction extends FunctionBase{

	/**
     * Standard identifier for the integer-abs function.
     */
    public static final String NAME_INTEGER_ABS = FUNCTION_NS + "integer-abs";

    /**
     * Standard identifier for the double-abs function.
     */
    public static final String NAME_DOUBLE_ABS = FUNCTION_NS + "double-abs";

    // inernal identifiers for each of the supported functions
    private static final int ID_INTEGER_ABS = 0;
    private static final int ID_DOUBLE_ABS = 1;

    /**
     * Creates a new <code>AbsFunction</code> object.
     * 
     * @param functionName the standard XACML name of the function to be handled by this object,
     *            including the full namespace
     * 
     * @throws IllegalArgumentException if the function is known
     */
    public AbsFunction(String functionName) {
        super(functionName, getId(functionName), getArgumentType(functionName), false, 1,
                getArgumentType(functionName), false);
    }

    /**
     * Private helper that returns the internal identifier used for the given standard function.
     */
    private static int getId(String functionName) {
        if (functionName.equals(NAME_INTEGER_ABS))
            return ID_INTEGER_ABS;
        else if (functionName.equals(NAME_DOUBLE_ABS))
            return ID_DOUBLE_ABS;
        else
            throw new IllegalArgumentException("unknown abs function " + functionName);
    }

    /**
     * Private helper that returns the type used for the given standard function. Note that this
     * doesn't check on the return value since the method always is called after getId, so we assume
     * that the function is present.
     */
    private static String getArgumentType(String functionName) {
        if (functionName.equals(NAME_INTEGER_ABS))
            return IntegerAttribute.identifier;
        else
            return DoubleAttribute.identifier;
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.add(NAME_INTEGER_ABS);
        set.add(NAME_DOUBLE_ABS);

        return set;
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

        // evaluate the inputs, returning any error that may occur
        AttributeValue[] argValues = new AttributeValue[inputs.size()];
        EvaluationResult result = evalArgs(inputs, context, argValues);
        if (result != null)
            return result;

        // Now that we have real values, perform the abs operation
        // in the manner appropriate for the type of the arguments.
        switch (getFunctionId()) {
        case ID_INTEGER_ABS: {
            long arg = ((IntegerAttribute) argValues[0]).getValue();
            long absValue = Math.abs(arg);

            result = new EvaluationResult(new IntegerAttribute(absValue));
            break;
        }
        case ID_DOUBLE_ABS: {
            double arg = ((DoubleAttribute) argValues[0]).getValue();
            double absValue = Math.abs(arg);

            result = new EvaluationResult(new DoubleAttribute(absValue));
            break;
        }
        }

        return result;
    }
}
