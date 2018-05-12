package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.TimeAttribute;


public class TimeAttributeProxy implements AttributeProxy{

	 public AttributeValue getInstance(Node root) throws Exception {
	        return TimeAttribute.getInstance(root);
	    }

	    public AttributeValue getInstance(String value) throws Exception {
	        return TimeAttribute.getInstance(value);
	    }
}
