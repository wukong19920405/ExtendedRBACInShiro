package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DoubleAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class FloorFunction extends FunctionBase{

	  /**
     * Standard identifier for the floor function.
     */
    public static final String NAME_FLOOR = FUNCTION_NS + "floor";

    /**
     * Creates a new <code>FloorFunction</code> object.
     * 
     * @param functionName the standard XACML name of the function to be handled by this object,
     *            including the full namespace
     * 
     * @throws IllegalArgumentException if the function is unknown
     */
    public FloorFunction(String functionName) {
        super(NAME_FLOOR, 0, DoubleAttribute.identifier, false, 1, DoubleAttribute.identifier,
                false);

        if (!functionName.equals(NAME_FLOOR))
            throw new IllegalArgumentException("unknown floor function: " + functionName);
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.add(NAME_FLOOR);

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

        // Now that we have real values, perform the floor operation
        double arg = ((DoubleAttribute) argValues[0]).getValue();

        return new EvaluationResult(new DoubleAttribute(Math.floor(arg)));
    }
}
