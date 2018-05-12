package com.wukong.rbac;

import com.wukong.attribute.BaseAttributeFactory;
import com.wukong.function.BaseFunctionFactory;

public class RBAC {

	private BaseAttributeFactory attrfactory;
	
	private BaseFunctionFactory funcfactory;
	
	private static RBAC rbac;
	
	private static Object lock=new Object();
	
	private RBAC(BaseAttributeFactory attrfactory,BaseFunctionFactory funcfactory){
		this.attrfactory=attrfactory;
		this.funcfactory=funcfactory;
	}
	
	private RBAC(){
		this.attrfactory=null;
		this.funcfactory=null;
	}
	
	
	public static RBAC getInstance(BaseAttributeFactory attrfactory,BaseFunctionFactory funcfactory){
		if(rbac==null){
			synchronized(lock){
				rbac=new RBAC(attrfactory,funcfactory);
			}
		}
		return rbac;
	}
	
	public static RBAC getInstance(){
		if(rbac==null){
			synchronized(lock){
				rbac=new RBAC();
			}
		}
		return rbac;
	}
	
	public BaseAttributeFactory getAttributeFactory(){
		
		return attrfactory;
	}
	
	public BaseFunctionFactory getFunctionFactory(){
		return funcfactory;
	}
	
	
}
