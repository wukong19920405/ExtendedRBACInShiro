package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DayTimeDurationAttribute;


public class DayTimeDurationAttributeProxy implements AttributeProxy{

	public AttributeValue getInstance(Node root) throws Exception {
        return DayTimeDurationAttribute.getInstance(root);
    }

    public AttributeValue getInstance(String value) throws Exception {
        return DayTimeDurationAttribute.getInstance(value);
    }

}
