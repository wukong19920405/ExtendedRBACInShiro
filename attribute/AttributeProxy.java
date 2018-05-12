package com.wukong.attribute;

import org.w3c.dom.Node;



public interface AttributeProxy {
	
	public AttributeValue getInstance(Node root) throws Exception;

    /**
     * Tries to create a new <code>AttributeValue</code> based on the given String data.
     *
     * @param value the text form of some attribute data
     *
     * @param params additional parameters that need to creates a value
     * 
     * @return an <code>AttributeValue</code> representing the given data
     *
     * @throws Exception if the data couldn't be used (the exception is typically wrapping some
     *             other exception)
     */
   public AttributeValue getInstance(String vlue) throws Exception;

}
