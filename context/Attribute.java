package com.wukong.context;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BaseAttributeFactory;
import com.wukong.attribute.StandardAttributeFactory;
import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;
import com.wukong.rbac.DomHelper;

public class Attribute implements Serializable{

	private static final long serialVersionUID=2;
	private URI id;
	
	private AttributeValue value;
	
	public Attribute(URI id,AttributeValue attrValue){
		this.id=id;
		this.value=attrValue;
	}
	
	public static Attribute getInstance(Node root) throws DOMException, ParsingException{
		URI id=null;
		AttributeValue value=null;
		BaseAttributeFactory attributeFactory=StandardAttributeFactory.getFactory();
		try{
			id=new URI(root.getAttributes().getNamedItem("AttributeId").getNodeValue());
		}catch(URISyntaxException use){
			throw new ParsingException("wrong with parsing attribute",use);
		}
		NodeList children=root.getChildNodes();
		for(int i=0;i<children.getLength();++i){
			Node node=children.item(i);
			String name=DomHelper.getLocalName(node);
			try{
				if(name.equals("AttributeValue")){
					value=attributeFactory.createValue(node);
				}
			}catch(UnknownIdentifierException uie){
				throw new  ParsingException("wrong with parsing attribute datatype",uie);
			}
		}
		if(id==null||value==null)
			throw new ParsingException("AttributeId or AttributeValue cann't be null"+DomHelper.getLocalName(root));
			
		return new Attribute(id,value);	
	}
	public URI getId(){
		return id;
	}
	
	public AttributeValue getValue(){
		return value;
	}
	
	public void setValue(AttributeValue value){
		this.value=value;
	}
}
