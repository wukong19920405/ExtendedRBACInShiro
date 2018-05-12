package com.wukong.rbac;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wukong.attribute.BooleanAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.exception.ParsingException;

public class Policy {

	private Target target;
	
	private Condition condition;
	
	public Policy(Target target,Condition condition){
		this.target=target;
		this.condition=condition;
	}
	
	public static Policy getInstance(Node root) throws ParsingException{
		Target target=null;
		Condition condition=null;
		NodeList children=root.getChildNodes();
		for(int i=0;i<children.getLength();++i){
			Node node=children.item(i);
			String name=DomHelper.getLocalName(node);
		if(name.equals("Target")){
			target=Target.getInstance(node);
		}else if(name.equals("Conditioin")){
			condition=Condition.getInstance(node);
		}
		}
		return new Policy(target,condition);
	}
	
	public boolean evaluate(EvaluationCtx context) throws Exception{
		if(target!=null){
		MatchResult match=target.match(context);
		if(match.getResult()==MatchResult.NO_MATCH||(match.getResult()==MatchResult.INDETERMINATE))//TODO  先不考虑属性缺失引发的indeterminate
			return false;
		}
		if(condition!=null){
		EvaluationResult result=condition.evaluate(context);
		if(result.indeterminate())
			return false;
		BooleanAttribute bool=(BooleanAttribute)result.getAttributeValue();
		if(bool.getValue()==false)
			return false;
		}
		return true;
	}
}
