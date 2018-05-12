package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BooleanAttribute;

public class BooleanAttributeProxy implements AttributeProxy{

	public AttributeValue getInstance(String value) throws Exception{
		return BooleanAttribute.getInstance(value);
	} 
	
	public AttributeValue getInstance(Node root) throws Exception{
		return BooleanAttribute.getInstance(root);
	}
}
