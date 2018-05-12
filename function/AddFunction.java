package com.wukong.function;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DoubleAttribute;
import com.wukong.attribute.IntegerAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class AddFunction extends FunctionBase{

	/**
	 * Standard identifier for the integer-add function.
	 */
	public static final String NAME_INTEGER_ADD = FUNCTION_NS + "integer-add";

	/**
	 * Standard identifier for the double-add function.
	 */
	public static final String NAME_DOUBLE_ADD = FUNCTION_NS + "double-add";

	// inernal identifiers for each of the supported functions
	private static final int ID_INTEGER_ADD = 0;
	private static final int ID_DOUBLE_ADD = 1;

	/**
	 * Creates a new <code>AddFunction</code> object.
	 * 
	 * @param functionName the standard XACML name of the function to be handled by this object,
	 *            including the full namespace
	 * 
	 * @throws IllegalArgumentException if the function is unknown
	 */
	public AddFunction(String functionName) {
		super(functionName, getId(functionName), getArgumentType(functionName), false, -1, 2,
				getArgumentType(functionName), false);
	}

	/**
	 * Private helper that returns the internal identifier used for the given standard function.
	 */
	private static int getId(String functionName) {
		if (functionName.equals(NAME_INTEGER_ADD))
			return ID_INTEGER_ADD;
		else if (functionName.equals(NAME_DOUBLE_ADD))
			return ID_DOUBLE_ADD;
		else
			throw new IllegalArgumentException("unknown add function " + functionName);
	}

	/**
	 * Private helper that returns the type used for the given standard function. Note that this
	 * doesn't check on the return value since the method always is called after getId, so we assume
	 * that the function is present.
	 */
	private static String getArgumentType(String functionName) {
		if (functionName.equals(NAME_INTEGER_ADD))
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

		set.add(NAME_INTEGER_ADD);
		set.add(NAME_DOUBLE_ADD);

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

		// Now that we have real values, perform the add operation
		switch (getFunctionId()) {
		case ID_INTEGER_ADD: {
			long sum = 0;
			for (int index = 0; index < argValues.length; index++) {
				long arg = ((IntegerAttribute) argValues[index]).getValue();
				sum += arg;
			}

			result = new EvaluationResult(new IntegerAttribute(sum));
			break;
		}
		case ID_DOUBLE_ADD: {
			double sum = 0;
			for (int index = 0; index < argValues.length; index++) {
				double arg = ((DoubleAttribute) argValues[index]).getValue();
				sum = sum + arg;
			}

			result = new EvaluationResult(new DoubleAttribute(sum));
			break;
		}
		}

		return result;
	}
}
