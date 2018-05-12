package com.wukong.context;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BooleanAttribute;
import com.wukong.rbac.MatchResult;

public class EvaluationResult {

	private boolean wasInd;
	private AttributeValue value;
	private MatchResult matchResult;
	private Status status;
	
	private static EvaluationResult falsebooleanresult;
	private static EvaluationResult truebooleanresult;
	
	public EvaluationResult(AttributeValue value){
		this.value=value;
		wasInd=false;
		this.status=null;
	}
	
	public EvaluationResult(Status status){
		wasInd=true;
		this.value=null;
		this.status=status;
	}
	
	public void setMatchResult(MatchResult match){
		this.matchResult=match;
	}
	
	public static EvaluationResult getInstance(boolean value){
		if(value)
			return getTrueInstance();
		else 
			return getFalseInstance();
	}
	
	public static EvaluationResult getTrueInstance(){
		if(truebooleanresult==null)
			truebooleanresult=new EvaluationResult(BooleanAttribute.getTrueInstance());
		return truebooleanresult;
	}
	
	public static EvaluationResult getFalseInstance(){
		if(falsebooleanresult==null)
			falsebooleanresult=new EvaluationResult(BooleanAttribute.getFalseInstance());
		return falsebooleanresult;
	}
	
	public boolean indeterminate(){
		return wasInd;
	}
	
	public AttributeValue getAttributeValue(){
		return value;
	}
	
	public MatchResult getMatchResult(){
		return matchResult;
	}
	public Status getStatus(){
		return status;
	}
	
}
