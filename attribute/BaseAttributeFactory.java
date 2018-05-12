package com.wukong.attribute;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;

import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;





public class BaseAttributeFactory {

	private HashMap attributeMap;
	
	public BaseAttributeFactory() {
        attributeMap = new HashMap();
    }
	
	public BaseAttributeFactory(Map attributes) {
        attributeMap = new HashMap();

        Iterator it = attributes.keySet().iterator();
        while (it.hasNext()) {
            try {
                String id = (it.next()).toString();
                AttributeProxy proxy = (AttributeProxy) (attributes.get(id));
                attributeMap.put(id, proxy);
            } catch (ClassCastException cce) {
                throw new IllegalArgumentException("an element of the map "
                        + "was not an instance of " + "AttributeProxy");
            }
        }
    }
	
	public void addDatatype(String id, AttributeProxy proxy) {
        // make sure this doesn't already exist
        if (attributeMap.containsKey(id))
            throw new IllegalArgumentException("datatype already exists");

        attributeMap.put(id, proxy);
    }
	
	 public Set getSupportedDatatypes() {
	        return Collections.unmodifiableSet(attributeMap.keySet());//?????TODO
	    }
	 
	 public AttributeValue createValue(Node root) throws UnknownIdentifierException,ParsingException {
		 Node node = root.getAttributes().getNamedItem("DataType");
		 return createValue(root, node.getNodeValue());
}

/**
* Creates a value based on the given DOM root node and data type.
* 
* @param root the DOM root of an attribute value
* @param dataType the type of the attribute
* 
* @return a new <code>AttributeValue</code>
* 
* @throws UnknownIdentifierException if the data type isn't known to the factory
* @throws ParsingException if the node is invalid or can't be parsed by the appropriate proxy
*/
public AttributeValue createValue(Node root, URI dataType) throws UnknownIdentifierException,
     ParsingException {
 return createValue(root, dataType.toString());
}

/**
* Creates a value based on the given DOM root node and data type.
* 
* @param root the DOM root of an attribute value
* @param type the type of the attribute
* 
* @return a new <code>AttributeValue</code>
* 
* @throws UnknownIdentifierException if the type isn't known to the factory
* @throws ParsingException if the node is invalid or can't be parsed by the appropriate proxy
*/
public AttributeValue createValue(Node root, String type) throws UnknownIdentifierException,
     ParsingException {

 AttributeValue attributeValue=null;
 AttributeProxy proxy = (AttributeProxy) (attributeMap.get(type));

 if (proxy != null) {
     try {
         attributeValue =  proxy.getInstance(root);
     } catch (Exception e) {
         throw new ParsingException("couldn't create " + type
                 + " attribute based on DOM node");
     }
 } else {
     throw new UnknownIdentifierException("Attributes of type " + type
             + " aren't supported.");
 }

 if (attributeValue == null) {
     throw new ParsingException("Could not create " + type + " attribute based on DOM node");
 }

 return attributeValue;
}

public AttributeValue createValue(String value,String type) throws UnknownIdentifierException,
ParsingException {
	AttributeValue attributeValue=null;
	AttributeProxy proxy = (AttributeProxy) (attributeMap.get(type));
	if(proxy!=null){
		try{
			attributeValue=proxy.getInstance(value);
		}catch(Exception e){
			 throw new ParsingException("couldn't create " + type
	                 + " attribute based on DOM node");
		}
	}else{
		 throw new UnknownIdentifierException("Attributes of type " + type
	             + " aren't supported.");
	}
	
	if(attributeValue == null) {
	     throw new ParsingException("Could not create " + type + " attribute based on String value");
	 }
	return attributeValue;
}
}
