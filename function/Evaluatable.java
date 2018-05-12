package com.wukong.function;

import java.util.List;

import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;

public interface Evaluatable extends Expression{

	public EvaluationResult evaluate(EvaluationCtx context);
	
	public List getChildren();
}
