package com.wukong.rbac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.exception.ParsingException;

public class Condition {

	private List<Apply> applys;
	
	public Condition(List<Apply> applys){
		if(applys!=null)
			this.applys=Collections.unmodifiableList(new ArrayList(applys));
		else
			this.applys=Collections.EMPTY_LIST;
	}
	
	public static Condition getInstance(Node root) throws ParsingException{
		List<Apply> applys=null;
		NodeList children=root.getChildNodes();
		for(int i=0;i<children.getLength();++i){
			Apply apply=null;
			Node node=children.item(i);
			String name=DomHelper.getLocalName(node);
			if(name.equals("Apply"))
				apply=Apply.getInstance(node);
			if(apply!=null)
				applys.add(apply);
		}
		return new Condition(applys);
	}
	
	public EvaluationResult evaluate(EvaluationCtx context){
		EvaluationResult result=null;
		if(applys==null)
			return EvaluationResult.getTrueInstance();
		for(Apply apply:applys){
			result=apply.evaluate(context);
			if(result.indeterminate())
				return result;
		}
		return result;
	}
}
