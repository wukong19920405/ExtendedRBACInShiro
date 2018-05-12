package com.wukong.context;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BagAttribute;
import com.wukong.attribute.DateAttribute;
import com.wukong.attribute.DateTimeAttribute;
import com.wukong.attribute.TimeAttribute;

public class EvaluationCtxImp implements EvaluationCtx{

	private Set<Attributes> attrs;
	
	public EvaluationCtxImp(Set<Attributes> attributes){
		attrs=attributes;
	}
	@Override
	public TimeAttribute getCurrentTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateAttribute getCurrentDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateTimeAttribute getCurrentDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EvaluationResult getAttribute(URI type, URI id, URI category) {
		List<AttributeValue> list= new ArrayList<AttributeValue>();
		Iterator<Attributes> it=attrs.iterator();
		while(it.hasNext()){
			Attributes attrs=it.next();
			if(!attrs.getCategory().toString().equals(category.toString())){
				continue;
			}
			for(Attribute attr:attrs.getAttributes()){
				if(attr.getId().toString().equals(id.toString()))
					list.add(attr.getValue());
			}
		}
		if(list.size()==0){
			return new EvaluationResult(BagAttribute.createEmptyBag(type));
			}
		return new EvaluationResult(new BagAttribute(type,list));
	}
}
