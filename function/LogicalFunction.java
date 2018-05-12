package com.wukong.function;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BooleanAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;


public class LogicalFunction extends FunctionBase{

	/**
     * Standard identifier for the or function.
     */
    public static final String NAME_OR = FUNCTION_NS + "or";

    /**
     * Standard identifier for the and function.
     */
    public static final String NAME_AND = FUNCTION_NS + "and";

    // internal identifiers for each of the supported functions
    private static final int ID_OR = 0;
    private static final int ID_AND = 1;

    /**
     * Creates a new <code>LogicalFunction</code> object.
     * 
     * @param functionName the standard XACML name of the function to be handled by this object,
     *            including the full namespace
     * 
     * @throws IllegalArgumentException if the functionName is unknown
     */
    public LogicalFunction(String functionName) {
        super(functionName, getId(functionName), BooleanAttribute.identifier, false, -1,
                BooleanAttribute.identifier, false);
    }

    /**
     * Private helper that looks up the private id based on the function name.
     */
    private static int getId(String functionName) {
        if (functionName.equals(NAME_OR))
            return ID_OR;
        else if (functionName.equals(NAME_AND))
            return ID_AND;
        else
            throw new IllegalArgumentException("unknown logical function: " + functionName);
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.add(NAME_OR);
        set.add(NAME_AND);

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

        // Evaluate the arguments one by one. As soon as we can
        // return a result, do so. Return Indeterminate if any argument
        // evaluated is indeterminate.
        Iterator it = inputs.iterator();
        while (it.hasNext()) {
            Evaluatable eval = (Evaluatable) (it.next());

            // Evaluate the argument
            EvaluationResult result = eval.evaluate(context);
            if (result.indeterminate())
                return result;

            AttributeValue value = result.getAttributeValue();
            boolean argBooleanValue = ((BooleanAttribute) value).getValue();

            switch (getFunctionId()) {
            case ID_OR:
                if (argBooleanValue)
                    return EvaluationResult.getTrueInstance();
                break;
            case ID_AND:
                if (!argBooleanValue)
                    return EvaluationResult.getFalseInstance();
                break;
            }
        }

        if (getFunctionId() == ID_OR)
            return EvaluationResult.getFalseInstance();
        else
            return EvaluationResult.getTrueInstance();
    }
}
