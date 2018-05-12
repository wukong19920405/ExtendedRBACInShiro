package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DoubleAttribute;


public class DoubleAttributeProxy implements AttributeProxy{

	public AttributeValue getInstance(Node root) throws Exception {
        return DoubleAttribute.getInstance(root);
    }

    public AttributeValue getInstance(String value) throws Exception {
        return DoubleAttribute.getInstance(value);
    }
}
