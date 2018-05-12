package com.wukong.attribute;

import com.wukong.exception.*;

import java.net.URI;

import org.w3c.dom.Node;
public class DoubleAttribute extends AttributeValue{

	public static final String identifier= "http://www.w3.org/2001/XMLSchema#double";
	
	private static URI identifierURI;
	
	private static RuntimeException earlyException;
	
	static{
		try{
			identifierURI=new URI(identifier);
		}catch(Exception e){
			earlyException= new IllegalArgumentException();
			earlyException.initCause(e);
		}
	}
	
	private double value;
	
	public DoubleAttribute(double value){
		super(identifierURI);
		if(earlyException!=null)
			throw earlyException;
		
		this.value=value;
	}
	
	public static DoubleAttribute getInstance(Node root){
		return getInstance(root.getFirstChild().getNodeValue());
	}
	
	public static DoubleAttribute getInstance(String value){
		if(value.endsWith("INF")){
			int pos=value.lastIndexOf("INF");
			value=value.substring(0, pos)+"Infinity";
		}
		return new DoubleAttribute(Double.parseDouble(value));
	}
	
	public double getValue(){
		return value;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof DoubleAttribute))
			return false;
		DoubleAttribute other=(DoubleAttribute) o;
		
		if(Double.isNaN(value)){
			if(Double.isNaN(other.value))
				return true;
			return false;
		}
		
		return value==other.value;
	}
	
	public String encode(){
		return String.valueOf(value);
	}

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
}
