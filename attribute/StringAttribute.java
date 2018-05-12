package com.wukong.attribute;

import org.w3c.dom.Node;

import com.wukong.exception.ParsingException;

import java.net.URI;

public class StringAttribute extends AttributeValue{

	public static final String identifier="http://www.w3.org/2001/XMLSchema#string";
	
	private static URI identifierURI;
	
	private static RuntimeException earlyException;
	
	static{
		try{
			identifierURI= new URI(identifier);
		}catch(Exception e){
			earlyException=new IllegalArgumentException();
			earlyException.initCause(e);
		}
	}
	
	private String value;
	
	public StringAttribute(String value){
		super(identifierURI);
		if(earlyException!=null)
			throw earlyException;
		if(value==null)
			this.value="";
		else
			this.value=value;
	}
	
	public static StringAttribute getInstance(Node root){
		Node node=root.getFirstChild();
		
		if(node==null)
			return new StringAttribute("");
		short type=node.getNodeType();
		if ((type == Node.TEXT_NODE) || (type == Node.CDATA_SECTION_NODE)
                || (type == Node.COMMENT_NODE)) {
            return getInstance(node.getNodeValue());
        }
		
		return null;
	}
	
	public static StringAttribute getInstance(String value){
		return new StringAttribute(value);
	}
	
	public boolean equals(Object o){
		if(!(o instanceof StringAttribute))
			return false;
		StringAttribute other=(StringAttribute) o;
		
		if(other.value.equals(value))
			return true;
		return false;
	}
	
	public String encode(){
		return value;
		
	}
	
	public String toString(){
		return "StringAttribute :\""+value+"\"";
	}
	
	public String getValue(){
		return value;
	}

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
}
