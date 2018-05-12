package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BooleanAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class NotFunction extends FunctionBase{

	/**
     * Standard identifier for the not function.
     */
    public static final String NAME_NOT = FUNCTION_NS + "not";

    /**
     * Creates a new <code>NotFunction</code> object.
     * 
     * @param functionName the standard XACML name of the function to be handled by this object,
     *            including the full namespace
     * 
     * @throws IllegalArgumentException if the function is unknown
     */
    public NotFunction(String functionName) {
        super(NAME_NOT, 0, BooleanAttribute.identifier, false, 1, BooleanAttribute.identifier,
                false);

        if (!functionName.equals(NAME_NOT))
            throw new IllegalArgumentException("unknown not function: " + functionName);
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.add(NAME_NOT);

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

        // Now that we have a real value, perform the not operation.
        boolean arg = ((BooleanAttribute) argValues[0]).getValue();
        return EvaluationResult.getInstance(!arg);
    }
}
