package com.wukong.function;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BagAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class GeneralSetFunction extends SetFunction{

	private static final int ID_BASE_INTERSECTION = 0;
	private static final int ID_BASE_UNION = 1;

	// mapping of function name to its associated id and parameter type
	private static HashMap<String, Integer> idMap;
	private static HashMap<String, String> typeMap;

	/**
	 * Static initializer that sets up the parameter info for all the supported functions.
	 */
	static {
		idMap = new HashMap<String, Integer>();
		typeMap = new HashMap<String, String>();

		idMap.put(NAME_BASE_INTERSECTION, Integer.valueOf(ID_BASE_INTERSECTION));
		idMap.put(NAME_BASE_UNION, Integer.valueOf(ID_BASE_UNION));

		for (int i = 0; i < baseTypes.length; i++) {
			String baseName = FUNCTION_NS + simpleTypes[i];
			String baseType = baseTypes[i];

			idMap.put(baseName + NAME_BASE_INTERSECTION, Integer.valueOf(ID_BASE_INTERSECTION));
			idMap.put(baseName + NAME_BASE_UNION, Integer.valueOf(ID_BASE_UNION));

			typeMap.put(baseName + NAME_BASE_INTERSECTION, baseType);
			typeMap.put(baseName + NAME_BASE_UNION, baseType);
		}

		for (int i = 0; i < baseTypes2.length; i++) {
			String baseName = FUNCTION_NS_2 + simpleTypes2[i];
			String baseType = baseTypes2[i];

			idMap.put(baseName + NAME_BASE_INTERSECTION, Integer.valueOf(ID_BASE_INTERSECTION));
			idMap.put(baseName + NAME_BASE_UNION, Integer.valueOf(ID_BASE_UNION));

			typeMap.put(baseName + NAME_BASE_INTERSECTION, baseType);
			typeMap.put(baseName + NAME_BASE_UNION, baseType);
		}
	};

	/**
	 * Constructor that is used to create one of the general-purpose standard set functions. The
	 * name supplied must be one of the standard XACML functions supported by this class, including
	 * the full namespace, otherwise an exception is thrown. Look in <code>SetFunction</code> for
	 * details about the supported names.
	 * 
	 * @param functionName the name of the function to create
	 * 
	 * @throws IllegalArgumentException if the function is unknown
	 */
	public GeneralSetFunction(String functionName) {
		super(functionName, getId(functionName), getArgumentType(functionName),
				getArgumentType(functionName), true);
	}

	/**
	 * Constructor that is used to create instances of general-purpose set functions for new
	 * (non-standard) datatypes. This is equivalent to using the <code>getInstance</code> methods in
	 * <code>SetFunction</code> and is generally only used by the run-time configuration code.
	 * 
	 * @param functionName the name of the new function
	 * @param datatype the full identifier for the supported datatype
	 * @param functionType which kind of Set function, based on the <code>NAME_BASE_*</code> fields
	 */
	public GeneralSetFunction(String functionName, String datatype, String functionType) {
		super(functionName, getId(functionType), datatype, datatype, true);
	}

	/**
	 * Private helper that returns the internal identifier used for the given standard function.
	 */
	private static int getId(String functionName) {
		Integer id = (Integer) (idMap.get(functionName));

		if (id == null)
			throw new IllegalArgumentException("unknown set function " + functionName);

		return id.intValue();
	}

	/**
	 * Private helper that returns the argument type for the given standard function. Note that this
	 * doesn't check on the return value since the method always is called after getId, so we assume
	 * that the function is present.
	 */
	private static String getArgumentType(String functionName) {
		return (String) (typeMap.get(functionName));
	}

	/**
	 * Returns a <code>Set</code> containing all the function identifiers supported by this class.
	 * 
	 * @return a <code>Set</code> of <code>String</code>s
	 */
	public static Set getSupportedIdentifiers() {
		return Collections.unmodifiableSet(idMap.keySet());
	}

	/**
	 * Evaluates the function, using the specified parameters.
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
		EvaluationResult evalResult = evalArgs(inputs, context, argValues);
		if (evalResult != null)
			return evalResult;

		// setup the two bags we'll be using
		BagAttribute[] bags = new BagAttribute[2];
		bags[0] = (BagAttribute) (argValues[0]);
		bags[1] = (BagAttribute) (argValues[1]);

		AttributeValue result = null;
		Set<AttributeValue> set = new HashSet<AttributeValue>();

		switch (getFunctionId()) {

		// *-intersection takes two bags of the same type and returns
		// a bag of that type
		case ID_BASE_INTERSECTION:
			// create a bag with the common elements of both inputs, removing
			// all duplicate values

			Iterator it = bags[0].iterator();

			// find all the things in bags[0] that are also in bags[1]
			while (it.hasNext()) {
				AttributeValue value = (AttributeValue) (it.next());
				if (bags[1].contains(value)) {
					// sets won't allow duplicates, so this addition is ok
					set.add(value);
				}
			}

			result = new BagAttribute(bags[0].getType(), Arrays.asList(set.toArray(new AttributeValue[set.size()])));

			break;

		// *-union takes two bags of the same type and returns a bag of
		// that type
		case ID_BASE_UNION:
			// create a bag with all the elements from both inputs, removing
			// all duplicate values

			Iterator it0 = bags[0].iterator();
			while (it0.hasNext()) {
				// first off, add all elements from the first bag...the set
				// will ignore all duplicates
				set.add((AttributeValue)it0.next());
			}

			Iterator it1 = bags[1].iterator();
			while (it1.hasNext()) {
				// now add all the elements from the second bag...again, all
				// duplicates will be ignored by the set
				set.add((AttributeValue)it1.next());
			}

			result = new BagAttribute(bags[0].getType(),  Arrays.asList(set.toArray(new AttributeValue[set.size()])));

			break;
		}

		return new EvaluationResult(result);
	}
}
