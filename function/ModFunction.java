package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.IntegerAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class ModFunction extends FunctionBase{

	/**
     * Standard identifier for the integer-mod function.
     */
    public static final String NAME_INTEGER_MOD = FUNCTION_NS + "integer-mod";

    /**
     * Creates a new <code>ModFunction</code> object.
     * 
     * @param functionName the standard XACML name of the function to be handled by this object,
     *            including the full namespace
     * 
     * @throws IllegalArgumentException if the function is unknown
     */
    public ModFunction(String functionName) {
        super(NAME_INTEGER_MOD, 0, IntegerAttribute.identifier, false, 2,
                IntegerAttribute.identifier, false);

        if (!functionName.equals(NAME_INTEGER_MOD))
            throw new IllegalArgumentException("unknown mod function: " + functionName);
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.add(NAME_INTEGER_MOD);

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

        // Evaluate the arguments
        AttributeValue[] argValues = new AttributeValue[inputs.size()];
        EvaluationResult result = evalArgs(inputs, context, argValues);
        if (result != null)
            return result;

        // Now that we have real values, perform the mod operation
        long arg0 = ((IntegerAttribute) argValues[0]).getValue();
        long arg1 = ((IntegerAttribute) argValues[1]).getValue();

        return new EvaluationResult(new IntegerAttribute(arg0 % arg1));
    }
}
