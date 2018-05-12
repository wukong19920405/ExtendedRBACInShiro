package com.wukong.attribute.proxy;

import org.w3c.dom.Node;

import com.wukong.attribute.AttributeProxy;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.DNSNameAttribute;
import com.wukong.exception.ParsingException;


public class DNSNameAttributeProxy implements AttributeProxy{

	public AttributeValue getInstance(Node root) throws ParsingException {
        return DNSNameAttribute.getInstance(root);
    }

    public AttributeValue getInstance(String value) throws ParsingException {
        return DNSNameAttribute.getInstance(value);
    }
}
