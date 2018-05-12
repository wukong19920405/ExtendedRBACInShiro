package com.wukong.context;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wukong.exception.ParsingException;
import com.wukong.rbac.DomHelper;

public class Attributes implements Serializable{
	
	private static final long serialVersionUID=3;

	private URI category;
	
	private List<Attribute> attrs;
	
	public Attributes(URI category,List<Attribute> attrs){
		this.category=category;
		this.attrs=attrs;
	}
	
	public static Attributes getInstance(Node root) throws ParsingException{
		List<Attribute> attrs=new ArrayList<Attribute>();
		URI category=null;
		String cate=null;
		cate=root.getAttributes().getNamedItem("Category").getNodeValue();
		if(cate==null)
			throw new ParsingException("Category cann't be null");
		try{
		category=new URI(cate);
		}catch(URISyntaxException use){
			throw new ParsingException("wrong with parsing attributes",use);
		}
		NodeList children=root.getChildNodes();
		for(int i=0;i<children.getLength();++i){
			Node node=children.item(i);
			if(DomHelper.getLocalName(node).equals("Attribute")){
				Attribute attr=Attribute.getInstance(node);
				attrs.add(attr);
				}
		}
		return new Attributes(category,attrs);
	}
	
	public URI getCategory(){
		return category;
	}
	public List<Attribute> getAttributes(){
		return attrs;
	}
	
}
