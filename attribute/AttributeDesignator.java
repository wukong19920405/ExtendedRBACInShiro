package com.wukong.attribute;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.context.Status;
import com.wukong.context.StatusDetail;
import com.wukong.exception.ParsingException;
import com.wukong.function.Function;

public class AttributeDesignator extends AbstractDesignator{
	
	private boolean mustBePresent;
	
	private URI category;
	
	private URI id;
	
	private URI type;
	
	public AttributeDesignator(boolean mustBePresent, URI category, URI id,URI type){
	
		this.mustBePresent=mustBePresent;
		this.category=category;
		this.id=id;
		this.type=type;
		
	}
	
	
	
	public static AttributeDesignator getInstance(Node root) throws ParsingException{
		
		boolean mustBePresent=false;
		URI category=null;
		URI type=null;
		URI id=null;
		
		NamedNodeMap attr=root.getAttributes();
		
		try{
			if(attr.getNamedItem("MustBePresent").getNodeValue().equals("true"))
				mustBePresent=true;
		}catch(Exception e){
			throw new ParsingException("Required mustBePresent must not be empty");
		}
		
		try{
			category=new URI(attr.getNamedItem("Category").getNodeValue());
		}catch(Exception e){
			throw new ParsingException("Required category must not be empty");
		}
		
		try{
			id=new URI(attr.getNamedItem("AttributeId").getNodeValue());
		}catch(Exception e){
			throw new ParsingException("Required AttributeId must not be empty");
		}
		try{
			type=new URI(attr.getNamedItem("DataType").getNodeValue());
		}catch(Exception e){
			throw new ParsingException("Required DataType must not be empty");
		}
		
		return new AttributeDesignator(mustBePresent,category,id,type);
	}
	

	@Override
	public EvaluationResult evaluate(EvaluationCtx context) {
		EvaluationResult result=null;
		result=context.getAttribute(type, id, category);
		if(result!=null){
			if(result.indeterminate())
				return result;
			BagAttribute bag=(BagAttribute) result.getAttributeValue();
			if(bag.isEmpty()){
				if(mustBePresent){
					ArrayList<String> code=new ArrayList<String>();
					code.add(Status.STATUS_MISSING_ATTRIBUTE);
					ArrayList<MissingAttributeDetail> missingattributes=new ArrayList<MissingAttributeDetail>();
					MissingAttributeDetail missingattribute=new MissingAttributeDetail(category,type,id);
					missingattributes.add(missingattribute);
					StatusDetail detail=new StatusDetail(missingattributes);
					String message="Couldn't find attribute"+id.toString();
					return new EvaluationResult(new Status(code,detail,message));
				}
			}
		}
		else{
			// TODO
		}

		return result;
	}

	@Override
	public List getChildren() {
		
		return Collections.EMPTY_LIST;
	}

	@Override
	public URI getType() {
		return type;
	}

	@Override
	public boolean returnsBag() {
		
		return true;
	}

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public URI getId() {
		
		return id;
	}



	@Override
	public URI getCategory() {
		
		return category;
	}

	public boolean mustBePresent(){
		return mustBePresent;
	}
	
	public void encode(){
		// TODO
	}
	
}
