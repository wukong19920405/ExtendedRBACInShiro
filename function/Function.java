package com.wukong.function;

import java.net.URI;
import java.util.List;

import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;

public interface Function extends Expression{
	
	public EvaluationResult evaluate(List<Evaluatable> inputs,EvaluationCtx context);
	
	public URI getIdentifier();
	
	public URI getReturnType();
	
	public void checkInputs(List inputs) throws IllegalArgumentException;
	
	//public boolean returnsBag();
	
	public String encode();
	
	public void encode(StringBuilder sb);

}
