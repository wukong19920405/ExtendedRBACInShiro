package com.wukong.attribute.proxy;

import org.w3c.dom.Node;


import com.wukong.attribute.*;


public class IntegerAttributeProxy implements AttributeProxy{
	
	public AttributeValue getInstance(Node root) throws Exception {
        return IntegerAttribute.getInstance(root);
    }

    public AttributeValue getInstance(String value) throws Exception {
        return IntegerAttribute.getInstance(value);
    }
}
