package com.wukong.rbac;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wukong.attribute.AttributeDesignator;
import com.wukong.attribute.AttributeSelector;
import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BagAttribute;
import com.wukong.attribute.BaseAttributeFactory;
import com.wukong.attribute.BooleanAttribute;
import com.wukong.attribute.StandardAttributeFactory;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;
import com.wukong.function.BaseFunctionFactory;
import com.wukong.function.Evaluatable;
import com.wukong.function.Function;
import com.wukong.function.FunctionTypeException;
import com.wukong.function.StandardFunctionFactory;

public class Match{
	
	private Function function;
	
	private AttributeValue attrValue;
	
	private Evaluatable eval;

	public Match(Function function,AttributeValue attrValue,Evaluatable eval){
		
		this.function=function;
		this.attrValue=attrValue;
		this.eval=eval;
		
	}
	
	
	public static Match getInstance(Node root) throws ParsingException{
		Function function=null;
		AttributeValue attrValue=null;
		Evaluatable eval=null;
		BaseFunctionFactory functionFactory= (BaseFunctionFactory)StandardFunctionFactory.getFunctionFactory();
		BaseAttributeFactory attributeFactory=(BaseAttributeFactory)StandardAttributeFactory.getFactory();
		String funcName=root.getAttributes().getNamedItem("MatchId").getNodeValue();
		try{
			URI funcId=new URI(funcName);
			function=functionFactory.createFunction(funcId);
		}catch(URISyntaxException use){
			throw new ParsingException("error parsing match",use);
		}catch(UnknownIdentifierException uie){
			throw new ParsingException("Unknown MatchId", uie);
		}catch(FunctionTypeException fte){
			throw new ParsingException("Unknown funcionId", fte);
		}
		
		NodeList children=root.getChildNodes();
		for(int i=0;i<children.getLength();++i){
			Node node=children.item(i);
			String name=DomHelper.getLocalName(node);
			if(name.equals("AttributeDesignator")){
				eval=AttributeDesignator.getInstance(node);
			}else if(name.equals("AttributeSelector")){
				eval=AttributeSelector.getInstance(node);
			}else if(name.equals("AttributeValue")){
				try{
					System.out.println("AttributeValue");
					attrValue=attributeFactory.createValue(node);
				}catch(UnknownIdentifierException uie){
					throw new ParsingException("Unknown Attribute Type", uie);
				}
			}
		}
		List<Evaluatable> inputs=new ArrayList<Evaluatable>();
		inputs.add(attrValue);
		inputs.add(eval);
		function.checkInputs(inputs);
		return new Match(function,attrValue,eval);
	}
	
	public Function getFunction(){
		return function;
	}
	
	public AttributeValue getValue(){
		return attrValue;
	}
	
	public Evaluatable getEval(){
		return eval;
	}
	
	
	public MatchResult match(EvaluationCtx context) throws Exception{
		EvaluationResult result=eval.evaluate(context);
		if(result.indeterminate())
			return new MatchResult(MatchResult.INDETERMINATE,result.getStatus());
		BagAttribute value=(BagAttribute) result.getAttributeValue();
		if(value.size()!=1)
			throw new Exception("now, we don't support mutiple matched attributeValue");
		Iterator it=value.iterator();
		ArrayList<Evaluatable> inputs=new ArrayList<Evaluatable>();
		inputs.add(attrValue);
		inputs.add((AttributeValue)it.next());
		MatchResult match=evaluateMatch(inputs,context);
		return match;
	}
	MatchResult evaluateMatch(ArrayList<Evaluatable> inputs,EvaluationCtx context){
		EvaluationResult result=function.evaluate(inputs, context);
		if(result.indeterminate())
			return new MatchResult(MatchResult.INDETERMINATE,result.getStatus());
		BooleanAttribute bool=(BooleanAttribute)result.getAttributeValue();
		if(bool.getValue())
			return new MatchResult(MatchResult.MATCH);
		else
			return new MatchResult(MatchResult.NO_MATCH);
	}
}
