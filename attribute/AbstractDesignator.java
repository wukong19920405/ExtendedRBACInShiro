package com.wukong.attribute;

import java.net.URI;

import com.wukong.function.Evaluatable;

public abstract  class AbstractDesignator implements Evaluatable{

	public abstract URI getId();
	
	public abstract URI getCategory();
}
