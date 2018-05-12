package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.StringAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class StringFunction extends FunctionBase{

	/**
	 * Standard identifier for the string-concatenate function.
	 */
	public static final String NAME_STRING_CONCATENATE = FUNCTION_NS_2 + "string-concatenate";

	// private identifiers for the supported functions
	private static final int ID_STRING_CONCATENATE = 0;

	/**
	 * Creates a new <code>StringFunction</code> object.
	 * 
	 * @param functionName the standard XACML name of the function to be handled by this object,
	 *            including the full namespace
	 * 
	 * @throws IllegalArgumentException if the function is unknown
	 */
	public StringFunction(String functionName) {
		super(functionName, ID_STRING_CONCATENATE, StringAttribute.identifier, false, -1, 2,
				StringAttribute.identifier, false);
	}

	/**
	 * Returns a <code>Set</code> containing all the function identifiers supported by this class.
	 * 
	 * @return a <code>Set</code> of <code>String</code>s
	 */
	public static Set getSupportedIdentifiers() {
		Set set = new HashSet();

		set.add(NAME_STRING_CONCATENATE);

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
	public EvaluationResult evaluate(List<Evaluatable> inputs, EvaluationCtx context) {
		// Evaluate the arguments
		AttributeValue[] argValues = new AttributeValue[inputs.size()];
		EvaluationResult result = evalArgs(inputs, context, argValues);
		if (result != null)
			return result;

		switch (getFunctionId()) {
		case ID_STRING_CONCATENATE:
			String str = ((StringAttribute) argValues[0]).getValue();
			StringBuffer buffer = new StringBuffer(str);
			for (int i = 1; i < argValues.length; i++) {
				buffer.append(((StringAttribute) (argValues[i])).getValue());
			}
			result = new EvaluationResult(new StringAttribute(buffer.toString()));
			break;
		}

		return result;
	}
}
