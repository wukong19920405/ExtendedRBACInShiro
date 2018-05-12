package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AnyURIAttribute;
import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;


public class AnyURIAttributeProxy implements AttributeProxy{

	public AttributeValue getInstance(Node root) throws Exception {
        return AnyURIAttribute.getInstance(root);
    }

    public AttributeValue getInstance(String value) throws Exception {
        return AnyURIAttribute.getInstance(value);
    }
}
