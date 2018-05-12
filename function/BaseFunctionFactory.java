package com.wukong.function;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.wukong.exception.UnknownIdentifierException;




/*TODO 和属性工厂一样还需要重构*/
public class BaseFunctionFactory {

	private HashMap functionMap = null;
	
	public BaseFunctionFactory() {
        functionMap=new HashMap();
    }
	
	public BaseFunctionFactory(Set supportedFunctions ){
		functionMap=new HashMap();
		Iterator it=supportedFunctions.iterator();
		while(it.hasNext()){
			Function function=(Function)it.next();
			functionMap.put(function.getIdentifier().toString(), function);
		}
	}
	
	public void addFunction(Function function) throws IllegalArgumentException {
        String id = function.getIdentifier().toString();

        // make sure this doesn't already exist
        if (functionMap.containsKey(id))
            throw new IllegalArgumentException("function already exists");

        // finally, add to this factory
        functionMap.put(id, function);
    }
	
	public Function createFunction(URI identity) throws UnknownIdentifierException,
	 	FunctionTypeException {
		System.out.println("BaseFunctionFactory");
		 return createFunction(identity.toString());
		 }
	
	public Function createFunction(String identity) throws UnknownIdentifierException,
	 	FunctionTypeException {
		 Object entry = functionMap.get(identity);

		 if (entry != null) {
			 if (entry instanceof Function) {
				 return (Function) entry;
			 } else {
				 // this is actually a proxy, which means the other create
				 // method should have been called
				 throw new FunctionTypeException("function is abstract");
			 }
		 } else {
			 // we couldn't find a match
			 throw new UnknownIdentifierException("functions of type " + identity + " are not "
					 + "supported by this factory");
		 }
	 }
}
