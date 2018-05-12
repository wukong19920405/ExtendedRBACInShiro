package com.wukong.rbac;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BaseAttributeFactory;
import com.wukong.attribute.StandardAttributeFactory;
import com.wukong.context.Attribute;
import com.wukong.context.Attributes;
import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;

public class ContextImp implements Context{

	private Map<String,Set<Attributes>> policies;
	
	public ContextImp(Map<String,Set<Attributes>> policies){
		this.policies=policies;
	}
	
	@Override
	public void Change(String attrId, String value) throws UnknownIdentifierException, ParsingException {
		boolean flag=false;
		for(Set<Attributes> v:policies.values()){
		BaseAttributeFactory attributeFactory=StandardAttributeFactory.getFactory();
		Iterator it=v.iterator();
		while(it.hasNext()){
			Attributes attrs=(Attributes) it.next();
			List<Attribute> listAttr=attrs.getAttributes();
			for(int i=0;i<listAttr.size();++i){
				Attribute attr=listAttr.get(i);
				if(attr.getId().toString().equals(attrId)){
					AttributeValue newValue=attributeFactory.createValue(value,attr.getValue().getType().toString());
					attr.setValue(newValue);
					flag=true;
				}
			}
		}
	}
		if(flag==false)
			throw new ParsingException("no attributeID"+attrId);
	}

	@Override
	public Set<Attributes> getRoleAttributes(String role) {
		if(policies.containsKey(role))
			return policies.get(role);
		return null;
	}

}
