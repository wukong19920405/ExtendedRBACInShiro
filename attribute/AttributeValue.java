package com.wukong.attribute;

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;
import com.wukong.function.Evaluatable;

public abstract class AttributeValue implements Evaluatable,Serializable {
	
	private static final long serialVersionUID=1;
	URI type;
	
	protected AttributeValue(){}
	
	protected AttributeValue(URI type){
		this.type=type;
	}
	
	public URI getType(){
		return type;
	}
	public final boolean returnsBag(){
		return isBag();
	}
	
	public boolean isBag(){
		return false;
	}
	
	public abstract String encode();
	
	public Object clone()    
    {    
        Object o=null;    
       try    
        {    
            o=super.clone();    
        }    
       catch(CloneNotSupportedException e)    
        {    
            System.out.println(e.toString());    
        }    
       return o;    
    }
	public EvaluationResult evaluate(EvaluationCtx context) {
        return new EvaluationResult(this);
    }
	public List getChildren() {
        return Collections.EMPTY_LIST;
    }
	
	
}
