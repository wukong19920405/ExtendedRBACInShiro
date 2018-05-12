package com.wukong.attribute;

import java.net.URI;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.exception.ParsingException;
import com.wukong.function.Evaluatable;

public class AttributeSelector implements Evaluatable{
	
	private URI type;
	
	private String contextPath;
	
	private boolean mustBePresent;

	@Override
	public URI getType() {
		return type;
	}

	@Override
	public boolean returnsBag() {
		return true;
	}

	public AttributeSelector(URI type,String contextPath,boolean mustBePresent){
		this.type=type;
		this.contextPath=contextPath;
		this.mustBePresent=mustBePresent;
	}
	
	public static AttributeSelector getInstance(Node root) throws ParsingException{
		URI type=null;
		String contextPath=null;
		boolean mustBePresent=false;
		NamedNodeMap attrs=root.getAttributes();
		try{
			type=new URI(attrs.getNamedItem("DataType").getNodeValue());
		}catch(Exception e){
			throw new ParsingException("Error parsing required DataType "
                    + "attribute in AttributeSelector", e);
		}
		try{
			contextPath=attrs.getNamedItem("Path").getNodeValue();
		}catch(Exception e){
			throw new ParsingException("Error parsing required "
                    + "RequestContextPath attribute in " + "AttributeSelector", e);
		}
		
		try{
			if(attrs.getNamedItem("mustBePresent").getNodeValue().equals("true"))
				mustBePresent=true;
		}catch(Exception e){
			throw new ParsingException("Error parsing optional attributes "
                    + "in AttributeSelector", e);
		}
		
		return new AttributeSelector(type,contextPath,mustBePresent);
	}
	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EvaluationResult evaluate(EvaluationCtx context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

}
