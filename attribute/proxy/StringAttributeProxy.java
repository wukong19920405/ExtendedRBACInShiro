package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.StringAttribute;


public class StringAttributeProxy implements AttributeProxy{

	public AttributeValue getInstance(Node root) {
        return StringAttribute.getInstance(root);
    }

    public AttributeValue getInstance(String value) {
        return StringAttribute.getInstance(value);
    }
}
