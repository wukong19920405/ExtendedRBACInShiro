package com.wukong.rbac;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.wukong.attribute.AttributeDesignator;
import com.wukong.attribute.AttributeSelector;
import com.wukong.attribute.BaseAttributeFactory;
import com.wukong.attribute.StandardAttributeFactory;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.exception.ParsingException;
import com.wukong.exception.UnknownIdentifierException;
import com.wukong.function.BaseFunctionFactory;
import com.wukong.function.Evaluatable;
import com.wukong.function.Expression;
import com.wukong.function.Function;
import com.wukong.function.FunctionTypeException;
import com.wukong.function.StandardFunctionFactory;

public class Apply implements Evaluatable{

	private Function function;
	
	private List xprs;
	
	public Apply(Function function,List xprs){
		function.checkInputs(xprs);
		this.function=function;
		this.xprs=Collections.unmodifiableList(new ArrayList(xprs));
	}
	
	public static Apply getInstance(Node root) throws ParsingException{
		Function function=null;
		List xprs=new ArrayList();
		BaseFunctionFactory functionFactory=StandardFunctionFactory.getFunctionFactory();
		BaseAttributeFactory attributeFactory=StandardAttributeFactory.getFactory();
		String funcName;
		try{
			funcName=root.getAttributes().getNamedItem("FunctionId").getNodeValue();
		}catch (Exception e){
			throw new ParsingException("Error parsing functionid in"+"functionType",e);
		}
		try{
			function=functionFactory.createFunction(funcName);
		}catch(UnknownIdentifierException uie){
			throw new ParsingException("Unknown FunctionId", uie);
		}catch(FunctionTypeException fte){
			throw new ParsingException("Unknown FunctionType", fte);
		}
		NodeList children=root.getChildNodes();
		for(int i=0;i<children.getLength();++i){
			
			Expression xpr=null;
			Node node=children.item(i);
			String name=DomHelper.getLocalName(node);
			if(name.equals("Apply"))
				xpr=Apply.getInstance(node);
			else if(name.equals("AttributeValue")){
				try{
					xpr=attributeFactory.createValue(node);
				}catch(UnknownIdentifierException uie){
					throw new ParsingException("Unknown DataType", uie);
				}
			}else if(name.equals("AttributeDesignator")){
				xpr=AttributeDesignator.getInstance(node);
			}else if(name.equals("AttributeSelector")){
				xpr=AttributeSelector.getInstance(node);
			}
			if(xpr!=null)
				xprs.add(xpr);
		}
		return new Apply(function,xprs);
	}
	
	public Function getFunction(){
		return function;
	}
	public List getExpression(){
		return xprs;
	}
	public EvaluationResult evaluate(EvaluationCtx context){
		return function.evaluate(xprs, context);
	}

	@Override
	public URI getType() {
		
		return function.getReturnType();
	}

	@Override
	public boolean returnsBag() {
		
		return function.returnsBag();
	}

	@Override
	public void encode(StringBuilder builder) {
		builder.append("<Apply FunctionId=\"").append(function.getIdentifier()).append("\">\n");

        Iterator it = xprs.iterator();
        while (it.hasNext()) {
            Expression xpr = (Expression) (it.next());
            xpr.encode(builder);
        }

        builder.append("</Apply>\n");
		
	}

	@Override
	public List getChildren() {
		return xprs;
	}
}
