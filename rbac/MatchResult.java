package com.wukong.rbac;

import com.wukong.context.Status;

public class MatchResult {
	
	public static final int MATCH=0;
	
	public static final int NO_MATCH=1;
	
	public static final int INDETERMINATE = 2;
	
	private int result;
	
	private Status status;
	
	public MatchResult(int result){
		this(result,null);
	}
	
	public MatchResult(int result, Status status) throws IllegalArgumentException{
		if(result!=MATCH&&result!=NO_MATCH&&result!=INDETERMINATE)
			throw new IllegalArgumentException("Input result is not a valid result");
		this.result=result;
		this.status=status;
	}
	
	public int getResult(){
		return result;
	}
	
	public Status getStatus(){
		return status;
	}

}
