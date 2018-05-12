package com.wukong.rbac;

import java.util.Set;

import com.wukong.context.Attributes;
import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;

public interface Context {

	public void Change(String attrId,String value) throws UnknownIdentifierException, ParsingException;
	
	public Set<Attributes> getRoleAttributes(String role);
}
