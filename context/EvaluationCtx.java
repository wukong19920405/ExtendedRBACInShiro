package com.wukong.context;

import java.net.URI;

import com.wukong.attribute.DateAttribute;
import com.wukong.attribute.DateTimeAttribute;
import com.wukong.attribute.TimeAttribute;

public interface EvaluationCtx {

	public TimeAttribute getCurrentTime();
	
	public DateAttribute getCurrentDate();
	
	public DateTimeAttribute getCurrentDateTime();
	
	public EvaluationResult getAttribute(URI type,URI id,URI category); //没有添加issuer
	
	//TODO...这里还需要添加一个getAttribute接口 和XPath有关
}
