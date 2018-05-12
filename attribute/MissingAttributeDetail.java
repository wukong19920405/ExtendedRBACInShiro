package com.wukong.attribute;

import java.net.URI;

public class MissingAttributeDetail {
	
	private URI category;
	
	private URI type;
	
	private URI id;
	
	public MissingAttributeDetail(URI category,URI type,URI id){
		
		this.category=category;
		this.type=type;
		this.id=id;
	}
	
	public URI getType(){
		return type;
	}
	
	public URI getCategory(){
		return category;
	}
	public URI getId(){
		return id;
	}

}
