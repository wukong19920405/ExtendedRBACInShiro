package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DateTimeAttribute;


public class DateTimeAttributeProxy implements AttributeProxy{

	public AttributeValue getInstance(Node root) throws Exception {
        return DateTimeAttribute.getInstance(root);
    }

    public AttributeValue getInstance(String value) throws Exception {
        return DateTimeAttribute.getInstance(value);
    }

}
