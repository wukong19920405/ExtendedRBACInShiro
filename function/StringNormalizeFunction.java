package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.StringAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;

public class StringNormalizeFunction extends FunctionBase{

	/**
     * Standard identifier for the string-normalize-space function.
     */
    public static final String NAME_STRING_NORMALIZE_SPACE = FUNCTION_NS + "string-normalize-space";

    /**
     * Standard identifier for the string-normalize-to-lower-case function.
     */
    public static final String NAME_STRING_NORMALIZE_TO_LOWER_CASE = FUNCTION_NS
            + "string-normalize-to-lower-case";

    // private identifiers for the supported functions
    private static final int ID_STRING_NORMALIZE_SPACE = 0;
    private static final int ID_STRING_NORMALIZE_TO_LOWER_CASE = 1;

    /**
     * Creates a new <code>StringNormalizeFunction</code> object.
     * 
     * @param functionName the standard XACML name of the function to be handled by this object,
     *            including the full namespace
     * 
     * @throws IllegalArgumentException if the function is unknown
     */
    public StringNormalizeFunction(String functionName) {
        super(functionName, getId(functionName), StringAttribute.identifier, false, 1,
                StringAttribute.identifier, false);
    }

    /**
     * Private helper that returns the internal identifier used for the given standard function.
     */
    private static int getId(String functionName) {
        if (functionName.equals(NAME_STRING_NORMALIZE_SPACE))
            return ID_STRING_NORMALIZE_SPACE;
        else if (functionName.equals(NAME_STRING_NORMALIZE_TO_LOWER_CASE))
            return ID_STRING_NORMALIZE_TO_LOWER_CASE;
        else
            throw new IllegalArgumentException("unknown normalize function " + functionName);
    }

    /**
     * Returns a <code>Set</code> containing all the function identifiers supported by this class.
     * 
     * @return a <code>Set</code> of <code>String</code>s
     */
    public static Set getSupportedIdentifiers() {
        Set set = new HashSet();

        set.add(NAME_STRING_NORMALIZE_SPACE);
        set.add(NAME_STRING_NORMALIZE_TO_LOWER_CASE);

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

        // Now that we have real values, perform the numeric conversion
        // operation in the manner appropriate for this function.
        switch (getFunctionId()) {
        case ID_STRING_NORMALIZE_SPACE: {
            String str = ((StringAttribute) argValues[0]).getValue();

            // Trim whitespace from start and end of string
            int startIndex = 0;
            int endIndex = str.length() - 1;
            while ((startIndex <= endIndex) && Character.isWhitespace(str.charAt(startIndex)))
                startIndex++;
            while ((startIndex <= endIndex) && Character.isWhitespace(str.charAt(endIndex)))
                endIndex--;
            String strResult = str.substring(startIndex, endIndex + 1);

            result = new EvaluationResult(new StringAttribute(strResult));
            break;
        }
        case ID_STRING_NORMALIZE_TO_LOWER_CASE: {
            String str = ((StringAttribute) argValues[0]).getValue();

            // Convert string to lower case
            String strResult = str.toLowerCase();

            result = new EvaluationResult(new StringAttribute(strResult));
            break;
        }
        }

        return result;
    }
}
